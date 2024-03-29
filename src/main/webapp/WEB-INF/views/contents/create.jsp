<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 
<!DOCTYPE html> 
<html lang="ko"> 
<head> 
<meta charset="UTF-8"> 
<meta name="viewport" content="user-scalable=yes, initial-scale=1.0, maximum-scale=3.0, width=device-width" /> 
<title>Tokyo Travel</title>
<link rel="shortcut icon" href="/images/shortcut.png" /> <%-- /static 기준 --%>
<link href="/css/style.css" rel="Stylesheet" type="text/css"> <!-- /static 기준 -->
<script>
  // 댓글 등록 버튼 클릭 시 실행되는 함수
  function submitReply() {
    var formData = {
      // 댓글 작성 시 입력한 데이터를 이곳에 추가
      // 예: "contentsno": $("#contentsno").val(),
      //     "content": $("#content").val(),
    };

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

  // 댓글 목록을 갱신하는 함수
  function updateCommentList(replyList) {
    var commentListDiv = $("#commentList");
    commentListDiv.empty();

    // replyList를 이용하여 댓글 목록을 갱신하는 코드 작성
    // 예: replyList.forEach(function(reply) { /* 댓글을 표시하는 코드 */ });
  }
</script>
</head>
 
<body>
<c:import url="/menu/top.do" />
 
  <div class='title_line'>${sectVO.name } > 글 등록</div>
  
  <aside class="aside_right">
    <a href="./create.do?sectno=${sectVO.sectno }">등록</a>
    <span class='menu_divide' >│</span>
    <a href="javascript:location.reload();">새로고침</a>
    <span class='menu_divide' >│</span>
    <a href="./list_by_sectno_search_paging.do?sectno=${sectVO.sectno }">기본 목록형</a>    
    <span class='menu_divide' >│</span>
    <a href="./list_by_sectno_grid.do?sectno=${sectVO.sectno }">갤러리형</a>
  </aside>
  
  <div style="text-align: right; clear: both;">  
    <form name='frm' id='frm' method='get' action='./list_by_sectno_search_paging.do'>
      <input type='hidden' name='sectno' value='${sectVO.sectno }'>  <%-- 게시판의 구분 --%>
      
      <c:choose>
        <c:when test="${param.word != '' }"> <%-- 검색하는 경우 --%>
          <input type='text' name='word' id='word' value='${param.word }' class='input_word'>
        </c:when>
        <c:otherwise> <%-- 검색하지 않는 경우 --%>
          <input type='text' name='word' id='word' value='' class='input_word'>
        </c:otherwise>
      </c:choose>
      <button type='submit' class='btn btn-info btn-sm'>검색</button>
      <c:if test="${param.word.length() > 0 }">
        <button type='button' class='btn btn-info btn-sm' 
                    onclick="location.href='./list_by_sectno.do?sectno=${sectVO.sectno}&word='">검색 취소</button>  
      </c:if>    
    </form>
  </div>
  
  <div class='menu_line'></div>
  
  <form name='frm' method='post' action='./create.do' enctype="multipart/form-data">
    <input type="hidden" name="sectno" value="${param.sectno }">
    
    <div>
       <label>제목</label>
       <input type='text' name='title' value='일본 여행' required="required" 
                 autofocus="autofocus" class="form-control" style='width: 100%;'>
    </div>
    <div>
       <label>내용</label>
       <textarea name='content' required="required" class="form-control" rows="12" style='width: 100%;'>일본에서 즐거운 시간 보내세요! </textarea>
    </div>
    <div>
       <label>검색어</label>
       <input type='text' name='word' value='일본, 여행, 도쿄' required="required" 
                 class="form-control" style='width: 100%;'>
    </div>   
    <div>
       <label>이미지</label>
       <input type='file' class="form-control" name='file1MF' id='file1MF' value='' placeholder="파일 선택">
    </div>   
    <div>
       <label>패스워드</label>
       <input type='password' name='passwd' value='1234' required="required" 
                 class="form-control" style='width: 50%;'>
    </div>   
    <div class="content_body_bottom">
      <button type="submit" class="btn btn-secondary btn-sm">등록</button>
      <button type="button" onclick="location.href='./list_by_sectno_search_paging.do?sectno=${param.sectno}'" class="btn btn-secondary btn-sm">목록</button>
    </div>
  
  </form>

 
<jsp:include page="../menu/bottom.jsp" flush='false' />
</body>
 
</html>

