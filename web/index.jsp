<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Ist mir egal</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css"
          integrity="sha384-1q8mTJOASx8j1Au+a5WDVnPi2lkFfwwEAa8hDDdjZlpLegxhjVME1fgjWPGmkzs7" crossorigin="anonymous">
</head>
<body>
<h1>Ist mir egal - Text to speech</h1>
<div align="center">
    <form action="${pageContext.request.contextPath}/IstMirEgal/" method="post">
        <input type="text" name="text">
        <br/><br/>
        <input type="submit" value="Submit">
    </form>
</div>
</body>
</html>
