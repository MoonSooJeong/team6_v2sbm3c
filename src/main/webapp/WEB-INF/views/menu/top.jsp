<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/css/bootstrap.min.css" rel="stylesheet">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/js/bootstrap.bundle.min.js"></script>
<style>
  .top_menu_link:link{  /* 방문전 상태 */
    text-decoration: none; /* 밑줄 삭제 */
    color: #FFFFFF;
    font-weight: bold;
  }
  .top_menu_link:visited{  /* 방문후 상태 */
    text-decoration: none; /* 밑줄 삭제 */
    color: #FFFFFF;
    font-weight: bold;
  }
  .top_menu_link:hover{  /* A 태그에 마우스가 올라간 상태 */
    text-decoration: none; /* 밑줄 출력 */
    color: #FFFFFF;
    font-size: 1.05em;
  }
</style>
<script type="text/javascript">
  function chatbot() {
    // 챗봇을 개발한 사람의 AWS IP
    // "http://localhost:5000/chatbot/?memberno=${sessionScope.memberno }"  
    var url = 'http://15.164.197.174:5000/chatbot?memberno=${sessionScope.memberno }';
    var win = window.open(url, '챗봇', 'width=1300px, height=850px');
       
    var x = (screen.width - 1300) / 2;
    var y = (screen.height - 850) / 2;
       
    win.moveTo(x, y); // 화면 중앙으로 이동
  }
 </script>
<div class='container_main'>
  <div class='top_img'>
    <div class="top_menu_label">Tokyo Travel</div>      
  </div> <!-- <div class='top_img'></div> 종료 -->
  

  <nav class="navbar navbar-expand-md navbar-dark custom-navbar-color">
      <a class="navbar-brand" href="/"><img src='/css/images/home.png' title="시작페이지" style='display: block; padding-left: 5px;'></a>
      <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarCollapse" aria-controls="navbarCollapse" aria-expanded="false" aria-label="Toggle Navigation">
        <span class="navbar-toggler-icon"></span>
      </button>   
      <a href="/notice/list_all.do" class="nav-link top_menu_link">공지사항</a>
        <div class="collapse navbar-collapse" id="navbarCollapse">
          <ul class="navbar-nav mr-auto">
            <c:forEach var="sectVO" items="${list_top}">
              <c:set var="sectno" value="${sectVO.sectno }" />
              <c:set var="name" value="${sectVO.name }" />
              <li class="nav-item">
                <c:choose>
                  <c:when test="${sectno == 4}">
                    <a class="nav-link top_menu_link" href="/information/list_by_sectno.do?sectno=4&now_page=1">${name}</a>
                  </c:when>
                  <c:otherwise>
                    <a class="nav-link top_menu_link" href="/contents/list_by_sectno.do?sectno=${sectno}&now_page=1">${name}</a> 
                  </c:otherwise>
                </c:choose>
              </li>
            </c:forEach>
            
            <li class="nav-item"> 
              <a class="nav-link top_menu_link" href="/contents/list_all.do">전체 글 목록</a>
            </li>

            <li class="nav-item dropdown"> <%-- 회원 서브 메뉴 --%>
              <a class="nav-link top_menu_link dropdown-toggle" data-bs-toggle="dropdown" href="#">회원</a>
              <div class="dropdown-menu">
                <c:choose>
                  <c:when test="${sessionScope.id == null }">
                    <a class="dropdown-item" href="/member/create.do">회원 가입</a>
                    <a class="dropdown-item" href="#">아이디 찾기</a>
                    <a class="dropdown-item" href="#">비밀번호 찾기</a>
                  </c:when>
                  <c:otherwise>
                    <a class="dropdown-item" href="javascript: chatbot();">챗봇</a>                   
                    <a class="dropdown-item" href="javascript: recommend();">관심분야 등록하고 추천받기</a>                  
                    <a class="dropdown-item" href="/member/read.do">가입 정보</a>
                    <a class="dropdown-item" href="/member/passwd_update.do">비밀번호 변경</a>
                    <a class="dropdown-item" href="/member/read.do">회원 정보 수정</a>
                    <a class="dropdown-item" href="javascript: alert('개발 예정')">로그인 내역</a>
                    <a class="dropdown-item" href="#">회원 탈퇴</a>
                  </c:otherwise>
                </c:choose>
              </div>
            </li>
          
            <c:choose>
              <c:when test="${sessionScope.admin_id == null }">
                <li class="nav-item">
                  <a class="nav-link top_menu_link" href="/admin/login.do">관리자 로그인</a>
                </li>
              </c:when>
              <c:otherwise>
                <li class="nav-item dropdown"> 
                  <a class="nav-link top_menu_link dropdown-toggle" data-bs-toggle="dropdown" href="#">관리자</a>
                  <div class="dropdown-menu">
                    <a class="dropdown-item" href='/sect/list_all.do'>카테고리 전체 목록</a>
                    <a class="dropdown-item" href='/member/list.do'>회원 목록</a>
                    <a class="dropdown-item" href='/admin/logout.do'>관리자 ${sessionScope.admin_id } 로그아웃</a>
                  </div>
                </li>
              </c:otherwise>
            </c:choose>

            <li class="nav-item"> 
              <c:choose>
                  <c:when test="${sessionScope.id == null}">
                      <a class="nav-link top_menu_link" href="/member/login.do">로그인</a>
                  </c:when>
                  <c:otherwise>
                      <a class="nav-link top_menu_link" href='/member/logout.do'>${sessionScope.id } 로그아웃</a>
                  </c:otherwise>
              </c:choose>
            </li>     
          </ul>
      </div>    
  </nav>
    
  <div class='content_body'> <!--  내용 시작 -->