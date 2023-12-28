<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
 
  
  <div style='width: 100%;'> <%-- 갤러리 Layout 시작 --%>
    <c:forEach var="contentsVO" items="${list_recom }" varStatus="status">
      <c:set var="title" value="${contentsVO.title.trim() }" />
      <c:set var="content" value="${contentsVO.content.trim() }" />
      <c:set var="cateno" value="${contentsVO.cateno }" />
      <c:set var="contentsno" value="${contentsVO.contentsno }" />
      <c:set var="thumb1" value="${contentsVO.thumb1 }" />
      <c:set var="size1" value="${contentsVO.size1 }" />
        
      <!-- 4기준 하나의 이미지 widrh, 24% * 4 = 96% -->
      <!-- 5기준 하나의 이미지 widrh, 19.2% * 5 = 96% -->
      <!-- 5기준 하나의 이미지 widrh, 16% * 6 = 96% -->
      <div onclick="location.href='/contents/read.do?contentsno=${contentsno }&word=${param.word }&now_page=${param.now_page == null ? 1 : param.now_page }'" class='hover'  
             style='width: 13%; height: 168px; float: left; margin: 0.5%; padding: 0.1%; background-color: #EEEFFF; text-align: left;'>
        
        <c:choose> 
          <c:when test="${thumb1.endsWith('jpg') || thumb1.endsWith('png') || thumb1.endsWith('gif')}"> <%-- 이미지인지 검사 --%>
            <%-- registry.addResourceHandler("/contents/storage/**").addResourceLocations("file:///" +  Contents.getUploadDir()); --%>
            <img src="/contents/storage/${thumb1 }" style="width: 100%; height: 140px;">
          </c:when>
          <c:otherwise> <!-- 이미지가 없는 경우 기본 이미지 출력: /static/contents/images/none1.png -->
            <IMG src="/contents/images/none1.png" style="width: 100%; height: 140px;">
          </c:otherwise>
        </c:choose>
        
        <strong>
          <span style="font-size: 0.8em;">
            <c:choose>
              <c:when test="${title.length() > 20 }"> <%-- 20 이상이면 20자만 출력, 공백:  6자 --%>
                ${title.substring(0, 20)}...
              </c:when>
              <c:when test="${title.length() <= 20 }">
                ${title}
              </c:when>
            </c:choose>
          </span>
        </strong>
        <br>
       
      </div>
     
    </c:forEach>
  </div>