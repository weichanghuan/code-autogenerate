package com.wch.code.generate.share;

import com.wch.code.generate.dto.GeneratorResultDTO;
import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.zip.Zip64Mode;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;

import java.io.File;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

public class ZipCompressShare {

    public static void getZipCompress(OutputStream outputStream, List<GeneratorResultDTO> list) throws Exception {
        //2：吧response的流给到ZipArchiveOutputStream并创建该对象
        ZipArchiveOutputStream zous = new ZipArchiveOutputStream(outputStream);
        zous.setUseZip64(Zip64Mode.AsNeeded);
        //遍历文件list

        for (GeneratorResultDTO dto : list) {
            //下面三行是吧excel的文件以流的形式转为byte[]
            byte[] xml = dto.getXmlString().getBytes();
            byte[] dao = dto.getInterfaceString().getBytes();
            byte[] po = dto.getPoString().getBytes();

            ArchiveEntry entryxml = new ZipArchiveEntry(dto.getFileName()+ File.separator + dto.getXmlFileName());
            zous.putArchiveEntry(entryxml);
            zous.write(xml);
            ArchiveEntry entrydao = new ZipArchiveEntry(dto.getFileName()+ File.separator + dto.getInterfaceFileName());
            zous.putArchiveEntry(entrydao);
            zous.write(dao);
            ArchiveEntry entrypo = new ZipArchiveEntry(dto.getFileName()+ File.separator + dto.getPoFileName());
            zous.putArchiveEntry(entrypo);
            zous.write(po);
            zous.closeArchiveEntry();
        }


        zous.close();
    }
}
