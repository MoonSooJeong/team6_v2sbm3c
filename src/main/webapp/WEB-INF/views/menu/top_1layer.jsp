<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
 
<div class='container_main'>
  <div class='top_img'>
    <div class="top_menu_label">나를 찾는 여행 version 4.0</div>      
    <nav class="top_menu">
      <a href="/" class="menu_link">힐링 리조트</a><span class="top_menu_sep"> </span>
      
        <c:forEach var="sectVO" items="${list_top}">
          <c:set var="sectno" value="${sectVO.sectno }" />
          <c:set var="name" value="${sectVO.name }" />
          <li class="nav-item">
            <c:choose>
              <!-- sectno가 4인 경우 -->
              <c:when test="${sectno == 4}">
                <a class="nav-link top_menu_link" href="information/list_by_sectno.do?sectno=3&now_page=1">${name}</a>
              </c:when>
              <!-- sectno가 4가 아닌 다른 경우 -->
              <c:otherwise>
                <a class="nav-link top_menu_link" href="/contents/list_by_sectno.do?sectno=${sectno}&now_page=1">${name}</a> 
              </c:otherwise>
            </c:choose>
          </li>
        </c:forEach>
      
      <a href="/member/create.do" class="menu_link">회원 가입</a><span class="top_menu_sep"> </span>
      <a href="/member/list.do" class="menu_link">회원 목록</a><span class="top_menu_sep"> </span>

      <c:choose>
        <c:when test="${sessionScope.id == null}">
          <a href="/member/login.do" class="menu_link">로그인</a><span class="top_menu_sep"> </span>
        </c:when>
        <c:otherwise>
          <a href='/member/logout.do' class="menu_link">${sessionScope.id } 로그아웃</a><span class="top_menu_sep"> </span>
          <a href='/member/passwd_update.do' class="menu_link">비밀번호 변경</a><span class="top_menu_sep"> </span>
        </c:otherwise>
      </c:choose>

      <c:choose>
        <c:when test="${sessionScope.admin_id == null }">
          <a href="/admin/login.do" class="menu_link">관리자 로그인</a>
        </c:when>
        <c:otherwise>
          <a href="/sect/list_all.do" class="menu_link">카테고리 전체 목록</a><span class="top_menu_sep"> </span>
          <a href="/contents/list_all.do" class="menu_link">전체 글 목록</a><span class="top_menu_sep"> </span>
          <a href="/contents/list_all_gallery.do" class="menu_link">Gallery</a><span class="top_menu_sep"> </span>
          <a href="/admin/logout.do" class="menu_link">관리자 ${sessionScope.admin_id } 로그아웃</a>
        </c:otherwise>
      </c:choose>
      
    </nav>
  </div>
  <div class='content_body'> <!--  내용 시작 -->  