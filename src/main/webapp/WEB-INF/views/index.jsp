<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <title>http://localhost:9093/</title>
  <link rel="shortcut icon" href="/images/shortcut.png" />
  <link href="/css/style.css" rel="Stylesheet" type="text/css">

</head>
<body>
  <c:import url="/menu/top.do" />

  <div style='margin: 20px; text-align: center;'>
    도쿄 여행 정보지
  </div>   
  <jsp:include page="./menu/bottom.jsp" flush='false'/> 
</body>
</html>
