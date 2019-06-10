package com.wch.code.generate.custom;

import com.wch.code.generate.dto.GeneratorResultDTO;
import com.wch.code.generate.dto.GeneratorTableDTO;
import org.mybatis.generator.api.*;
import org.mybatis.generator.codegen.RootClassInfo;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.Context;
import org.mybatis.generator.config.MergeConstants;
import org.mybatis.generator.config.TableConfiguration;
import org.mybatis.generator.exception.InvalidConfigurationException;
import org.mybatis.generator.exception.ShellException;
import org.mybatis.generator.internal.DefaultShellCallback;
import org.mybatis.generator.internal.NullProgressCallback;
import org.mybatis.generator.internal.ObjectFactory;
import org.mybatis.generator.internal.XmlFileMergerJaxp;

import java.io.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.mybatis.generator.internal.util.ClassloaderUtility.getCustomClassloader;
import static org.mybatis.generator.internal.util.messages.Messages.getString;

/**
 * 不够完善，请见谅
 *
 * 自定义生成主类入口  by wch
 *
 * date ：2019.06.07 2:21 AM
 */
public class GenerationStarter  {

    private Configuration configuration;

    private ShellCallback shellCallback;

    private List<GeneratedJavaFile> generatedJavaFiles;

    private List<GeneratedXmlFile> generatedXmlFiles;

    private List<String> warnings;

    private Set<String> projects;

    private List<GeneratorTableDTO> tableFlag;


    public GenerationStarter(Configuration configuration){
        this.configuration = configuration;
    }

    public GenerationStarter(Configuration configuration, ShellCallback shellCallback,
                            List<String> warnings) throws InvalidConfigurationException {
        super();
        if (configuration == null) {
            throw new IllegalArgumentException(getString("RuntimeError.2")); //$NON-NLS-1$
        } else {
            this.configuration = configuration;
        }

        if (shellCallback == null) {
            this.shellCallback = new DefaultShellCallback(false);
        } else {
            this.shellCallback = shellCallback;
        }

        if (warnings == null) {
            this.warnings = new ArrayList<String>();
        } else {
            this.warnings = warnings;
        }
        generatedJavaFiles = new ArrayList<GeneratedJavaFile>();
        generatedXmlFiles = new ArrayList<GeneratedXmlFile>();
        projects = new HashSet<String>();

        this.configuration.validate();
    }


    public List<GeneratorResultDTO> generate(ProgressCallback callback, Set<String> contextIds,
                                             Set<String> fullyQualifiedTableNames, boolean writeFiles) throws SQLException,
            IOException, InterruptedException {
        List<GeneratorResultDTO> list=new ArrayList<>();
        if (callback == null) {
            callback = new NullProgressCallback();
        }

        generatedJavaFiles.clear();
        generatedXmlFiles.clear();
        ObjectFactory.reset();
        RootClassInfo.reset();

        // calculate the contexts to run
        List<Context> contextsToRun;
        if (contextIds == null || contextIds.size() == 0) {
            contextsToRun = configuration.getContexts();
        } else {
            contextsToRun = new ArrayList<Context>();
            for (Context context : configuration.getContexts()) {
                if (contextIds.contains(context.getId())) {
                    contextsToRun.add(context);
                }
            }
        }

        // setup custom classloader if required
        if (configuration.getClassPathEntries().size() > 0) {
            ClassLoader classLoader = getCustomClassloader(configuration.getClassPathEntries());
            ObjectFactory.addExternalClassLoader(classLoader);
        }

        // now run the introspections...
        int totalSteps = 0;
        for (Context context : contextsToRun) {
            totalSteps += context.getIntrospectionSteps();
        }
        callback.introspectionStarted(totalSteps);

        for (Context context : contextsToRun) {
            context.introspectTables(callback, warnings,
                    fullyQualifiedTableNames);
        }

        // now run the generates
        totalSteps = 0;
        for (Context context : contextsToRun) {
            totalSteps += context.getGenerationSteps();
        }
        callback.generationStarted(totalSteps);

        for (Context context : contextsToRun) {
            context.generateFiles(callback, generatedJavaFiles,
                    generatedXmlFiles, warnings);
            if(tableFlag == null){
                tableFlag=new ArrayList<>();
            }

            for (TableConfiguration table : context.getTableConfigurations()) {
                GeneratorTableDTO generatorTableDTO=new GeneratorTableDTO();
                generatorTableDTO.setPoFileString(table.getDomainObjectName());
                generatorTableDTO.setXmlFileString(table.getMapperName());
                generatorTableDTO.setInterfaceFileString(table.getMapperName());
                generatorTableDTO.setTableString(table.getTableName());
                tableFlag.add(generatorTableDTO);
            }

        }

        // now save the files
        if (writeFiles) {
            for (GeneratorTableDTO generatorTableDTO : tableFlag) {
                GeneratorResultDTO generatorResultDTO=new GeneratorResultDTO();
                for (GeneratedXmlFile gxf : generatedXmlFiles) {
                    if(gxf.getFileName().contains(generatorTableDTO.getXmlFileString())){
                        generatorResultDTO.setXmlString(gxf.toString());
                        generatorResultDTO.setXmlFileName(gxf.getFileName());
                    }
                }

                for (GeneratedJavaFile gjf : generatedJavaFiles) {
                    String shortNameWithoutTypeArguments = gjf.getCompilationUnit().getType().getShortNameWithoutTypeArguments();
                    if(generatorTableDTO.getPoFileString().equals(shortNameWithoutTypeArguments)){
                        generatorResultDTO.setPoString(gjf.toString());
                        generatorResultDTO.setPoFileName(shortNameWithoutTypeArguments+".java");
                    }
                    if(generatorTableDTO.getInterfaceFileString().equals(shortNameWithoutTypeArguments)){
                        generatorResultDTO.setInterfaceString(gjf.toString());
                        generatorResultDTO.setInterfaceFileName(shortNameWithoutTypeArguments+".java");
                    }

                }
                generatorResultDTO.setFileName(generatorTableDTO.getTableString());

                list.add(generatorResultDTO);
            }



        }

        return list;
    }

    private void writeGeneratedJavaFile(GeneratedJavaFile gjf, ProgressCallback callback)
            throws InterruptedException, IOException {
        File targetFile;
        String source;
        try {
            File directory = shellCallback.getDirectory(gjf
                    .getTargetProject(), gjf.getTargetPackage());
            targetFile = new File(directory, gjf.getFileName());
            if (targetFile.exists()) {
                if (shellCallback.isMergeSupported()) {
                    source = shellCallback.mergeJavaFile(gjf
                                    .getFormattedContent(), targetFile,
                            MergeConstants.OLD_ELEMENT_TAGS,
                            gjf.getFileEncoding());
                } else if (shellCallback.isOverwriteEnabled()) {
                    source = gjf.getFormattedContent();
                    warnings.add(getString("Warning.11", //$NON-NLS-1$
                            targetFile.getAbsolutePath()));
                } else {
                    source = gjf.getFormattedContent();
                    targetFile = getUniqueFileName(directory, gjf
                            .getFileName());
                    warnings.add(getString(
                            "Warning.2", targetFile.getAbsolutePath())); //$NON-NLS-1$
                }
            } else {
                source = gjf.getFormattedContent();
            }

            callback.checkCancel();
            callback.startTask(getString(
                    "Progress.15", targetFile.getName())); //$NON-NLS-1$
            writeFile(targetFile, source, gjf.getFileEncoding());
        } catch (ShellException e) {
            warnings.add(e.getMessage());
        }
    }

    private void writeGeneratedXmlFile(GeneratedXmlFile gxf, ProgressCallback callback)
            throws InterruptedException, IOException {
        File targetFile;
        String source;
        try {
            File directory = shellCallback.getDirectory(gxf
                    .getTargetProject(), gxf.getTargetPackage());
            targetFile = new File(directory, gxf.getFileName());
            if (targetFile.exists()) {
                if (gxf.isMergeable()) {
                    source = XmlFileMergerJaxp.getMergedSource(gxf,
                            targetFile);
                } else if (shellCallback.isOverwriteEnabled()) {
                    source = gxf.getFormattedContent();
                    warnings.add(getString("Warning.11", //$NON-NLS-1$
                            targetFile.getAbsolutePath()));
                } else {
                    source = gxf.getFormattedContent();
                    targetFile = getUniqueFileName(directory, gxf
                            .getFileName());
                    warnings.add(getString(
                            "Warning.2", targetFile.getAbsolutePath())); //$NON-NLS-1$
                }
            } else {
                source = gxf.getFormattedContent();
            }

            callback.checkCancel();
            callback.startTask(getString(
                    "Progress.15", targetFile.getName())); //$NON-NLS-1$
            writeFile(targetFile, source, "UTF-8"); //$NON-NLS-1$
        } catch (ShellException e) {
            warnings.add(e.getMessage());
        }
    }


    private void writeFile(File file, String content, String fileEncoding) throws IOException {
        FileOutputStream fos = new FileOutputStream(file, false);
        OutputStreamWriter osw;
        if (fileEncoding == null) {
            osw = new OutputStreamWriter(fos);
        } else {
            osw = new OutputStreamWriter(fos, fileEncoding);
        }

        BufferedWriter bw = new BufferedWriter(osw);
        bw.write(content);
        bw.close();
    }


    private File getUniqueFileName(File directory, String fileName) {
        File answer = null;

        // try up to 1000 times to generate a unique file name
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i < 1000; i++) {
            sb.setLength(0);
            sb.append(fileName);
            sb.append('.');
            sb.append(i);

            File testFile = new File(directory, sb.toString());
            if (!testFile.exists()) {
                answer = testFile;
                break;
            }
        }

        if (answer == null) {
            throw new RuntimeException(getString(
                    "RuntimeError.3", directory.getAbsolutePath())); //$NON-NLS-1$
        }

        return answer;
    }


    public List<GeneratedJavaFile> getGeneratedJavaFiles() {
        return generatedJavaFiles;
    }


    public List<GeneratedXmlFile> getGeneratedXmlFiles() {
        return generatedXmlFiles;
    }


    public Configuration getConfiguration() {
        return configuration;
    }

    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }

    public ShellCallback getShellCallback() {
        return shellCallback;
    }

    public void setShellCallback(ShellCallback shellCallback) {
        this.shellCallback = shellCallback;
    }

    public void setGeneratedJavaFiles(List<GeneratedJavaFile> generatedJavaFiles) {
        this.generatedJavaFiles = generatedJavaFiles;
    }

    public void setGeneratedXmlFiles(List<GeneratedXmlFile> generatedXmlFiles) {
        this.generatedXmlFiles = generatedXmlFiles;
    }

    public List<String> getWarnings() {
        return warnings;
    }

    public void setWarnings(List<String> warnings) {
        this.warnings = warnings;
    }

    public Set<String> getProjects() {
        return projects;
    }

    public void setProjects(Set<String> projects) {
        this.projects = projects;
    }


    public List<GeneratorTableDTO> getTableFlag() {
        return tableFlag;
    }

    public void setTableFlag(List<GeneratorTableDTO> tableFlag) {
        this.tableFlag = tableFlag;
    }
}
