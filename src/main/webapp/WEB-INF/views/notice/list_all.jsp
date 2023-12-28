<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
 
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="user-scalable=yes, initial-scale=1.0, minimum-scale=1.0, maximum-scale=10.0, width=device-width" /> 
<title>http://localhost:9093/notice/list_all.do</title>
t>
<link rel="shortcut icon" href="/images/shortcut.png" /> <%-- /static 기준 --%>
<link href="/css/style.css" rel="Stylesheet" type="text/css"> <!-- /static 기준 -->


</head>
<body>
<c:import url="/menu/top.do" />

  <div class='title_line'>전체 공지사항</div>
  
  <aside class="aside_right">
    <%-- 관리자로 로그인해야 메뉴가 출력됨 --%>
    <c:if test="${sessionScope.admin_id != null }">
      <a href="./create.do?">등록</a>
      <span class='menu_divide' >│</span>
    </c:if>
    <a href="javascript:location.reload();">새로고침</a>
  </aside>
  <div class="menu_line"></div> 
  
  <table class="table table-hover">
    <colgroup>
      <col style="width: 10%;"></col>
      <col style="width: 80%;"></col>
      <col style="width: 10%;"></col>
    </colgroup>
    <thead>
      <tr>
        <th style='text-align: center;'>파일</th>
        <th style='text-align: center;'>제목</th>
        <th style='text-align: center;'>기타</th>
      </tr>
    </thead>
    <tbody>
        <c:forEach var="noticeVO" items="${list }" varStatus="info">
          <c:set var="noticeno" value="${noticeVO.noticeno }" />
          <c:set var="thumb1" value="${noticeVO.thumb1 }" />
    
          <tr onclick="location.href='./read.do?noticeno=${noticeno}&now_page=1'" style="cursor: pointer;">
            <td>
              <c:choose>
                <c:when test="${thumb1.endsWith('jpg') || thumb1.endsWith('png') || thumb1.endsWith('gif')}"> <%-- 이미지인지 검사 --%>
                  <%-- registry.addResourceHandler("/notice/storage/**").addResourceLocations("file:///" +  Notice.getUploadDir()); --%>
                  <img src="/notice/storage/${thumb1 }" style="width: 120px; height: 90px;">
                </c:when>
                <c:otherwise> <!-- 이미지가 없는 경우 기본 이미지 출력: /static/notice/images/none1.png -->
                  <img src="/notice/images/none1.png" style="width: 120px; height: 90px;">
                </c:otherwise>
              </c:choose>
            </td>
            <td class="td_bs_left">
              <span style="font-weight: bold;">${noticeVO.title }</span><br>
              <c:choose>
                <c:when test="${noticeVO.content.length() > 160 }">
                  ${noticeVO.content.substring(0, 160) }...
                </c:when>
                <c:otherwise>
                  ${noticeVO.content }
                </c:otherwise>
              </c:choose>
              (${noticeVO.rdate.substring(0, 16) })
            </td>
            <td class="td_bs">
              <a href="#" title="삭제"><img src="/notice/images/map.png" class="icon"></a>
              <a href="#" title="삭제"><img src="/notice/images/youtube.png" class="icon"></a>
              <a href="#" title="삭제"><img src="/notice/images/delete.png" class="icon"></a>
            </td>
          </tr>
        </c:forEach>
    </tbody>
      
  </table>
 
<jsp:include page="../menu/bottom.jsp" flush='false' /> 
</body>
</html>
