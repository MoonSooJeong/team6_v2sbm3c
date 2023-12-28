<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="user-scalable=yes, initial-scale=1.0, minimum-scale=1.0, maximum-scale=10.0, width=device-width" /> 
<title>http://localhost:9092/sect/list_all.do</title>
<link rel="shortcut icon" href="/images/star.png" /> <%-- /static 기준 --%>
<link href="/css/style.css" rel="Stylesheet" type="text/css"> <!-- /static 기준 -->


  
</head>
<body>
<c:import url="/menu/top.do" />

  <div class='title_line'>섹션</div>
  
  <aside class="aside_right">
    <a href="./create.do?sectno=${sectVO.sectno }">등록</a>
    <span class='menu_divide' >│</span>
    <a href="javascript:location.reload();">새로고침</a>
  </aside>
  <div class="menu_line"></div> 
  
  <form name='frm' method='post' action='/sect/create.do'>
    <div style="text-align: center;">
      <label>섹션 이름</label>
      <input type="text" name="name" value="" required="required" autofocus="autofocus" 
                 class="" style="width: 50%">
      <button type="submit" class="btn btn-secondary btn-sm" style="height: 28px; margin-bottom: 5px;">등록</button>
      <button type="button" onclick="location.href='./list_all.do'" class="btn btn-secondary btn-sm" 
                  style="height: 28px; margin-bottom: 5px;">목록</button> 
    </div>
  </form>
  
  <table class="table table-hover">
    <colgroup>
        <col style='width: 10%;'/>
        <col style='width: 40%;'/>
        <col style='width: 10%;'/>    
        <col style='width: 20%;'/>
        <col style='width: 20%;'/>
      </colgroup>
      <thead>
        <tr>
          <th class="th_bs">순서</th>
          <th class="th_bs">섹션 이름</th>
          <th class="th_bs">자료수</th>
          <th class="th_bs">등록일</th>
          <th class="th_bs">기타</th>
        </tr>
      </thead>
      <tbody>
        <c:forEach var="sectVO" items="${list }" varStatus="info">
          <c:set var="sectno" value="${sectVO.sectno }" />
          <c:set var="seqno" value="${sectVO.seqno }" />
    
          <tr>
            <td class="td_bs">${seqno }</td>
            <td style="text-align: center;"><a href="../contents/list_by_sectno.do?sectno=${sectno }" style="display: block;">${sectVO.name }</a></td>
            <td class="td_bs"> ${searchCount}</td>
            <td class="td_bs">${sectVO.rdate.substring(0, 10) }</td>
            <td class="td_bs">
              <a href="../tokyo/create.do?sectno=${sectno }" title="등록"><img src="/sect/images/create.png" class="icon"></a>
              <c:choose>
                <c:when test="${sectVO.visible == 'Y'}">
                  <a href="./update_visible_n.do?sectno=${sectno }" title="섹션 공개 설정"><img src="/sect/images/show.png" class="icon"></a>
                </c:when>
                <c:otherwise>
                  <a href="./update_visible_y.do?sectno=${sectno }" title="섹션 비공개 설정"><img src="/sect/images/hide.png" class="icon"></a>
                </c:otherwise>
              </c:choose>    
              
              <a href="./update_seqno_forward.do?sectno=${sectno }" title="우선 순위 높임"><img src="/sect/images/decrease.png" class="icon"></a>
              <a href="./update_seqno_backward.do?sectno=${sectno }" title="우선 순위 낮춤"><img src="/sect/images/increase.png" class="icon"></a>
              <a href="./update.do?sectno=${sectno }" title="수정"><img src="/sect/images/update.png" class="icon"></a>
              <a href="./delete.do?sectno=${sectno }" title="삭제"><img src="/sect/images/delete.png" class="icon"></a>
            </td>
          </tr>
        </c:forEach>
      </tbody>
      
  </table>
 
<jsp:include page="../menu/bottom.jsp" flush='false' /> 
</body>
</html>
