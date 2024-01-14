<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <title>숙소 등록</title>
  <link rel="shortcut icon" href="/images/shortcut.png" />
  <link href="/css/style.css" rel="Stylesheet" type="text/css">
</head>
<body>
  <c:import url="/menu/top.do" />

  <div class='title_line'>숙소 등록</div>
  
  <!-- 숙소 정보를 입력하는 폼 -->
  <form name='frm' method='post' action='/accommodation/create.do'>
    <div>
      <label>숙소 이름</label>
      <input type="text" name="name" required="required" autofocus="autofocus" 
             class="form-control form-control-sm" style="width: 50%">
    </div>
    <div>
      <label>연락처</label>
      <input type="text" name="contact" required="required"
             class="form-control form-control-sm" style="width: 50%">
    </div>
    <div>
      <label>주소</label>
      <input type="text" name="address" required="required"
             class="form-control form-control-sm" style="width: 50%">
    </div>
    <div>
      <label>설명</label>
      <textarea name="description" rows="4" 
                class="form-control form-control-sm" style="width: 50%"></textarea>
    </div>
    <div>
      <label>평점</label>
      <input type="number" step="0.1" name="rating"
             class="form-control form-control-sm" style="width: 50%">
    </div>

    <div class="content_body_bottom">
      <button type="submit" class="btn btn-secondary btn-sm">등록</button>
      <button type="button" onclick="location.href='./list_all.do'" class="btn btn-secondary btn-sm">목록</button> 
    </div>
  </form>

  <jsp:include page="../menu/bottom.jsp" flush='false' /> 
</body>
</html>
