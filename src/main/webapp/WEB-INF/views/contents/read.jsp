<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>



<c:set var="name" value="${sectVO.name }" />
<c:set var="sectno" value="${contentsVO.sectno }" />
<c:set var="contentsno" value="${contentsVO.contentsno }" />
<c:set var="thumb1" value="${contentsVO.thumb1 }" />
<c:set var="file1saved" value="${contentsVO.file1saved }" />
<c:set var="title" value="${contentsVO.title }" />
<c:set var="content" value="${contentsVO.content }" />
<c:set var="rdate" value="${contentsVO.rdate }" />
<c:set var="youtube" value="${contentsVO.youtube }" />
<c:set var="map" value="${contentsVO.map }" />
<c:set var="file1" value="${contentsVO.file1 }" />
<c:set var="size1_label" value="${contentsVO.size1_label }" />
<c:set var="word" value="${contentsVO.word }" />
<c:set var="content" value='${reply.content}'/>
<c:set var="rdate" value='${reply.rdate}'/>



<!-- 서버에서 로그인 여부를 JavaScript에 전달 -->
<script>
  var isLoggedIn = ${not empty sessionScope.memberno};
</script>

<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="user-scalable=yes, initial-scale=1.0, maximum-scale=3.0, width=device-width" />
  <title>Tokyo Travel</title>
  <link rel="shortcut icon" href="/images/shortcut.png" /> <!-- /static 기준 -->
  <link href="/css/style.css" rel="Stylesheet" type="text/css"> <!-- /static 기준 -->
  <style>
    .commentContainer {
      border: 1px solid #ccc;
      padding: 10px;
      margin-bottom: 10px;
    }

    .commentContent {
      margin-bottom: 10px;
    }

    .deleteCommentBtn {
      background-color: #dc3545;
      color: #fff;
      border: none;
      padding: 5px 10px;
      cursor: pointer;
    }
  </style>
  <script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>
  <script>
  
    // 댓글 등록 버튼 클릭 시 실행되는 함수
    function submitReply() {
    	  var formData = {

    			  };

      // 여기에서 로그인 여부 확인
      if (!isLoggedIn) {
        // 로그인이 안되어 있으면 메시지를 띄우고 함수 종료
        alert("댓글을 작성하려면 로그인이 필요합니다.");
        return;
      }

      $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/reply/create.do",
        data: JSON.stringify(formData),
        dataType: 'json',
        success: function (data) {
          if (data.result === 'success') {
            // 댓글 목록을 갱신
            updateCommentList(data.replyList);
            alert("댓글이 등록되었습니다.");
          } else if (data.result === 'login_required') {
            alert("댓글을 작성하려면 로그인이 필요합니다.");
          } else {
            alert("댓글 등록에 실패했습니다.");
          }
        },
        error: function (e) {
          console.log("Error:", e);
          alert("댓글 등록에 실패했습니다.");
        }
      });
    }
 // 댓글 삭제 버튼 클릭 시 실행되는 함수
    function deleteComment(replyno) {
        if (!confirm("댓글을 삭제하시겠습니까?")) {
            return;
        }

        // AJAX를 사용하여 댓글 삭제 요청
        $.ajax({
            type: "POST",
            url: "/reply/delete.do",
            data: { replyno: replyno },
            success: function (data) {
                if (data.deleteSuccess) {
                    // 댓글 목록을 갱신
                    updateCommentList(data.replyList);
                    alert("댓글이 삭제되었습니다.");

                    // 페이지 전체를 새로고침
                    location.reload(true);
                } else {
                    alert("댓글 삭제에 실패했습니다.");
                    location.reload(true);
                }
            },
            error: function (e) {
                alert("댓글 삭제에 성공했습니다.");
                location.reload(true);
            }
        });
    }

    // 댓글 목록을 갱신하는 함수
    function updateCommentList(replyList) {
        var commentListDiv = $("#commentList");
        commentListDiv.empty();

        // replyList를 이용하여 댓글 목록을 갱신하는 코드 작성
        replyList.forEach(function (reply) {
            var commentDiv = $("<div>");
            commentDiv.append("<p>" + reply.content + "</p>");
            // 추가적인 댓글 정보 표시 (작성자, 날짜 등)
            commentListDiv.append(commentDiv);
        });
    }

  </script>
</head>
 
<body>
<c:import url="/menu/top.do" />
  <DIV class='title_line'><A href="./list_by_sectno.do?sectno=${sectVO.sectno }" class='title_link'>${sectVO.name }</A></DIV>

  <aside class="aside_right">
    <%-- 관리자로 로그인해야 메뉴가 출력됨 --%>
    <c:if test="${sessionScope.member_id != null }">
      <%--
      http://localhost:9091/contents/create.do?sectno=1
      http://localhost:9091/contents/create.do?sectno=2
      http://localhost:9091/contents/create.do?sectno=3
      --%>
<c:if test="${sessionScope.member_id != null }">
  <!-- Menu options for logged-in users -->
  <a href="./create.do?sectno=${sectno }">등록</a>
  <span class='menu_divide' >│</span>
  <a href="./update_text.do?contentsno=${contentsno}&now_page=${param.now_page}&word=${param.word }">글 수정</a>
  <span class='menu_divide' >│</span>
  <a href="./update_file.do?contentsno=${contentsno}&now_page=${param.now_page}">파일 수정</a>  
  <span class='menu_divide' >│</span>
  <a href="./map.do?sectno=${sectno }&contentsno=${contentsno}">지도</a>
  <span class='menu_divide' >│</span>
  <a href="./youtube.do?sectno=${sectno }&contentsno=${contentsno}">Youtube</a>
  <span class='menu_divide' >│</span>
  <a href="./delete.do?contentsno=${contentsno}&now_page=${param.now_page}&sectno=${sectno}">삭제</a>  
  <span class='menu_divide' >│</span>
</c:if>

    </c:if>

    <a href="javascript:location.reload();">새로고침</a>
    <span class='menu_divide' >│</span>    
    <a href="./list_by_sectno.do?sectno=${sectno }&now_page=${param.now_page}&word=${param.word }">목록형</a>    
    <span class='menu_divide' >│</span>
    <a href="./list_by_sectno_grid.do?sectno=${sectno }&now_page=${param.now_page}&word=${param.word }">갤러리형</a>
  </aside> 
  
  <div style="text-align: right; clear: both;">  
    <form name='frm' id='frm' method='get' action='./list_by_sectno.do'>
      <input type='hidden' name='sectno' value='${param.sectno }'>  <%-- 게시판의 구분 --%>
      
      <c:choose>
        <c:when test="${param.word != '' }"> <%-- 검색하는 경우는 검색어를 출력 --%>
          <input type='text' name='word' id='word' value='${param.word }'>
        </c:when>
        <c:otherwise> <%-- 검색하지 않는 경우 --%>
          <input type='text' name='word' id='word' value=''>
        </c:otherwise>
      </c:choose>
      <button type='submit' class='btn btn-secondary btn-sm' style="padding: 2px 8px 3px 8px; margin: 0px 0px 2px 0px;">검색</button>
      <c:if test="${param.word.length() > 0 }"> <%-- 검색 상태하면 '검색 취소' 버튼을 출력 --%>
        <button type='button' class='btn btn-secondary btn-sm' style="padding: 2px 8px 3px 8px; margin: 0px 0px 2px 0px;"
                    onclick="location.href='./list_by_sectno.do?sectno=${param.sectno}&word='">검색 취소</button>  
      </c:if>    
    </form>
  </div>
  
  <DIV class='menu_line'></DIV>

  <fieldset class="fieldset_basic">
    <ul>
      <li class="li_none">
        <DIV style="width: 100%; word-break: break-all;">
          <c:choose>
            <c:when test="${thumb1.endsWith('jpg') || thumb1.endsWith('png') || thumb1.endsWith('gif')}">
              <%-- /static/contents/storage/ --%>
              <img src="/contents/storage/${file1saved }" style='width: 50%; float: left; margin-top: 0.5%; margin-right: 1%;'> 
            </c:when>
            <c:otherwise> <!-- 기본 이미지 출력 -->
              <img src="/contents/images/none1.png" style='width: 50%; float: left; margin-top: 0.5%; margin-right: 1%;'> 
            </c:otherwise>
          </c:choose>

          <span style="font-size: 1.5em; font-weight: bold;">${contentsVO.title }</span>
          <span style="font-size: 1em;"> ${contentsVO.rdate }</span><br>
          ${contentsVO.content}
        </DIV>
      </li>
      
      <c:if test="${youtube.trim().length() > 0 }">
        <li class="li_none" style="clear: both; padding-top: 5px; padding-bottom: 5px;">
          <DIV style="text-align: center;">
            ${youtube }
          </DIV>
        </li>
      </c:if>
      
      <c:if test="${map.trim().length() > 0 }">
        <li class="li_none" style="clear: both; padding-top: 5px; padding-bottom: 5px;">
          <DIV style='text-align: center; width:640px; height: 360px; margin: 0px auto;'>
            ${map }
          </DIV>
        </li>
      </c:if>
      
      <li class="li_none" style="clear: both;">
        <DIV style='text-decoration: none;'>
          <br>
          검색어(키워드): ${word }
        </DIV>
      </li>

      <li class="li_none">
        <div>
          <c:if test="${file1.trim().length() > 0 }">
            첨부 파일: <a href='/download?dir=/contents/storage&filename=${file1saved}&downname=${file1}'>${file1}</a> (${size1_label}) 
            <a href='/download?dir=/contents/storage&filename=${file1saved}&downname=${file1}'><img src="/contents/images/download.png"></a>
          </c:if>
        </div>
      </li>   
    </ul>
  </fieldset>
  
  <h3>Comments:</h3>
  <div>

</div>
<DIV class='title_line'></div>
<c:choose>
<c:when test="${sessionScope.member_id == null }">
<div id="loginMessage" style="display: ${isLoggedIn ? 'none' : 'block'};">
  댓글을 작성하려면 로그인이 필요합니다.
</div>
</c:when>
<c:otherwise>
<!-- 댓글 폼 -->
<div id="commentForm">
  <form id="frm_reply" method="post" action="<c:url value='/reply/create.do'/>">
    <input type="hidden" name="contentsno" value="${contentsVO.contentsno}">
    <input type="hidden" name="memberno" value="${sessionScope.memberno}">
    <input type="hidden" name="mname" value="${sessionScope.mname}">
    <textarea name="content" id="content" style="width: 100%; height: 60px; margin: 3px;" placeholder="댓글 작성, 로그인해야 등록 할 수 있습니다."></textarea>
    <button type="submit" class="btn btn-primary">등록</button>
  </form>
</div>

</c:otherwise>
</c:choose>
<!-- 콘텐츠 내용 표시 부분 이후에 댓글 목록을 표시할 영역을 추가 -->
<!-- 댓글 목록을 표시할 영역 추가 -->
<div id="commentList">
  <c:forEach var="reply" items="${replyList}">
    <!-- 각 댓글을 표시하는 코드 작성 -->
    <div class="commentContainer">
    
      <span style="font-size: 1em;">
        작성자: ${reply.mname} |<!-- 작성자 이름 표시 -->
        날짜: ${reply.rdate}
      </span>
      </span>
      <p class="commentContent">${reply.content}</p>
      
      <c:if test="${sessionScope.admin_id != null || reply.memberno eq sessionScope.memberno}">
        <button class="deleteCommentBtn" onclick="deleteComment(${reply.replyno})">삭제</button>
      </c:if>
    </div>
  </c:forEach>
</div>

</DIV>
 
<jsp:include page="../menu/bottom.jsp" flush='false' />
</body>
 
</html>