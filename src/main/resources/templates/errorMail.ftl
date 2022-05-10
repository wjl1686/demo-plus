<html>
<body>
<p>
    <b>${time?string('yyyy-MM-dd hh:mm:ss')}  错误信息：</b>
<div> 接口路径：${path}</div>
<div> 接口参数：${params}</div>
<div> 请求头信息：${headers}</div>
<br/>
<div style="color: red">${e}</div>
</p>
<hr/>
<br/>
<#list e.stackTrace as st>
${st}<br/>
</#list>
</body>
</html>