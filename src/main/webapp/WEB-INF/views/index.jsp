<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <title>도쿄 여행 정보지</title>
  <link rel="shortcut icon" href="/images/shortcut.png" />
  <link href="/css/style.css" rel="Stylesheet" type="text/css">
<style>
  .header-container {
    display: flex;
    align-items: center;
    padding: 20px;
  }
  .header-text {
    flex: 1;
    text-align: center; /* Changed from right to center */
    display: flex;
    flex-direction: column;
    justify-content: center; /* Added for vertical centering */
  }
  .header-image {
    flex: 1;
    max-width: 450px; /* Adjust this value as needed */
  }
  .header-image img {
    width: 100%;
    height: auto;
  }
  
</style>
<script>
  // 페이지 로드 시 쿠키 확인
  window.onload = function() {
    if (!getCookie("hidePopup")) {
      document.getElementById("popup").style.display = "block";
    }
  }

  // 팝업창 숨기기
  function hidePopup() {
    document.getElementById("popup").style.display = "none";
  }

  // 쿠키 설정 및 팝업창 숨기기
  function setCookieAndHidePopup() {
    var date = new Date();
    date.setTime(date.getTime() + (24 * 60 * 60 * 1000));
    document.cookie = "hidePopup=true; expires=" + date.toUTCString() + "; path=/";
    hidePopup();
  }

  // 쿠키 가져오기
  function getCookie(name) {
    var value = "; " + document.cookie;
    var parts = value.split("; " + name + "=");
    if (parts.length == 2) return parts.pop().split(";").shift();
  }
  function deleteCookie(name) {
	  document.cookie = name + '=; expires=Thu, 01 Jan 1970 00:00:01 GMT; path=/';
	}
  //deleteCookie('hidePopup');
  
</script>
</head>
<body>
  <c:import url="/menu/top.do" />
  
  <!-- Header with Image and Text -->
  <div class="header-container">
    <div class="header-image">
      <!-- Image URL should be relative to the 'static' directory -->
      <img src="/images/tokyotower.jpg" alt="Tokyo Tower">
    </div>
    <div class="header-text">
      <h1>도쿄에서 만나요!</h1>
      <p> 신주쿠, 시부야, 이케부쿠로, 아키하바라, 아사쿠사, 롯폰기 등 <br>당신이 떠날 곳은 많습니다! 
      도쿄는 당신을 기다리고 있어요. <br>모든 순간이 새로운 이야기와 추억으로 가득 찰 여행을 떠나보세요.</p>
    </div>
  </div>
<!-- 팝업창 HTML -->
<div id="popup" style="display:none; position: fixed; left: 50%; top: 50%; transform: translate(-50%, -50%); border: 1px solid black; padding: 20px; background-color: white; z-index: 1000;">
  <p>🚫Visit Japan Web 서비스를 사칭앱 주의🚫<br>
Visit Japan Web Info라는 Visit Japan Web 서비스를 사칭한 수상한 앱이 발견되었습니다.<br>
디지털청의 Visit Japan 웹서비스는 모바일 앱 서비스를 제공하지 않습니다.
또한 신용카드 정보에 대한 입력을 요청하지 않습니다.
만일 수상한 앱을 다운로드 받은 경우 신속히 삭제하시기 바랍니다.</p>
  <button onclick="setCookieAndHidePopup()">하루 동안 보지 않기</button>
  <button onclick="hidePopup()">닫기</button>
  
</div>
  <jsp:include page="./menu/bottom.jsp" flush='false'/> 

</body>
</html>
