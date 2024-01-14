<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="user-scalable=yes, initial-scale=1.0, minimum-scale=1.0, maximum-scale=10.0, width=device-width" /> 
  <title>댓글 작성</title>
  <link rel="shortcut icon" href="/images/star.png" /> <%-- /static 기준 --%>
  <link href="/css/style.css" rel="Stylesheet" type="text/css"> <!-- /static 기준 -->

<script>
  function validateForm() {
    var content = document.getElementById("content").value.trim();
    if (content === "") {
      alert("댓글 내용을 입력해주세요.");
      return false;
    }
    return true;
  }

  function createComment(contentsno, memberno) {
    // 댓글 내용을 가져오기
    var content = document.getElementById("content").value.trim();

    // Ajax 등을 이용하여 서버에 댓글을 전송하는 코드 추가

    // 필요한 경우 페이지 새로고침 또는 댓글 목록 갱신 등의 동작 추가

    // 예시: 페이지 새로고침
    location.reload();
  }
</script>
</head>
<body>
<!-- 댓글 폼 -->
<div id="commentForm">
  <form id="frm_reply" method="post" action="<c:url value='/reply/create.do'/>">
    <input type="hidden" name="contentsno" value="${contentsVO.contentsno}">
    <input type="hidden" name="memberno" value="${sessionScope.memberno}">
    <textarea name="content" id="content" style="width: 100%; height: 60px;" placeholder="댓글 작성, 로그인해야 등록 할 수 있습니다."></textarea>
    <input type="password" name="passwd" id="passwd" placeholder="비밀번호">
    <button type="submit" class="btn btn-secondary btn-sm">등록</button>
  </form>
</div>
</body>
</html>
