<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/html">
<head lang="en">
    <meta charset="UTF-8"/>
    <title></title>
</head>
<body>

<form method="post" action="/generate">

    选择数据源：数据库库名
    </br>
    <input id="libraryName" name="libraryName" placeholder="输入库名"/>
    </br>
    输入表名：<input id="table" name="table" placeholder="输入表名"/>
    </br>
    输入mapper：<input id="mapperName" name="mapperName" placeholder="输入mapperName"/>
    </br>
    输入mapper生成目录：<input id="mapperNameTarget" name="mapperNameTarget" placeholder="输入mapperName的taget目录"/>
    </br>
    输入po：<input id="domainObjectName" name="domainObjectName" placeholder="输入domainObjectName"/>
    </br>
    输入po生成目录：<input id="domainObjectNameTarget" name="domainObjectNameTarget" placeholder="输入domainObjectName的target目录"/>
    </br>
    <input type="submit" value="自动生成">

</form>
</body>
</html>
