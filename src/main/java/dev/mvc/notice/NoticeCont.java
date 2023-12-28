package dev.mvc.notice;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import dev.mvc.admin.AdminProcInter;
import dev.mvc.tool.Tool;
import dev.mvc.tool.Upload;

@Controller
public class NoticeCont {
  @Autowired
  @Qualifier("dev.mvc.admin.AdminProc")
  private AdminProcInter adminProc;
  
  @Autowired
  @Qualifier("dev.mvc.notice.NoticeProc")
  private NoticeProcInter noticeProc;
  
  public NoticeCont() {
    System.out.println("-> NoticeCont created.");
  }
  
  /**
   * POST 요청시 JSP 페이지에서 JSTL 호출 기능 지원, 새로고침 방지, EL에서 param으로 접근
   * POST → url → GET → 데이터 전송
   * @return
   */
  @RequestMapping(value="/notice/msg.do", method=RequestMethod.GET)
  public ModelAndView msg(String url) {
    ModelAndView mav = new ModelAndView();
    mav.setViewName(url); // forward
    return mav; // forward
  }
  
  @RequestMapping(value="/notice/create.do", method = RequestMethod.GET)
  public ModelAndView create() {
    ModelAndView mav = new ModelAndView();
    mav.setViewName("/notice/create"); // /webapp/WEB-INF/views/notice/create.jsp
    return mav;
  }
  
  /**
   * 등록 처리 http://localhost:9093/notice/create.do
   * POST 요청 처리
   */
  @RequestMapping(value = "/notice/create.do", method = RequestMethod.POST)
  public ModelAndView create(HttpServletRequest request, HttpSession session, NoticeVO noticeVO) {
    ModelAndView mav = new ModelAndView();
    
    if (adminProc.isAdmin(session)) { // 관리자로 로그인한 경우
      // 파일 전송 코드 시작
      String file1 = "";
      String file1saved = "";
      String thumb1 = "";
      String upDir = Notice.getUploadDir(); // 파일을 업로드할 폴더 준비
      System.out.println("-> upDir: " + upDir);
      
      MultipartFile mf = noticeVO.getFile1MF();
      file1 = mf.getOriginalFilename();
      System.out.println("-> 원본 파일명 산출 file1: " + file1);
      
      if (Tool.checkUploadFile(file1)) { // 업로드 가능한 파일인지 검사
        long size1 = mf.getSize();  // 파일 크기
        
        if (size1 > 0) { // 파일 크기 체크
          file1saved = Upload.saveFileSpring(mf, upDir); 
          if (Tool.isImage(file1saved)) { // 이미지인지 검사
            thumb1 = Tool.preview(upDir, file1saved, 200, 150); 
          }
        }    
        
        noticeVO.setFile1(file1);
        noticeVO.setFile1saved(file1saved);
        noticeVO.setThumb1(thumb1);
        noticeVO.setSize1(size1);
        
        int adminno = (int)session.getAttribute("adminno");
        noticeVO.setAdminno(adminno);
        int cnt = this.noticeProc.create(noticeVO); 

        if (cnt == 1) {
            mav.addObject("code", "create_success");
        } else {
            mav.addObject("code", "create_fail");
        }
        mav.addObject("cnt", cnt); 
        mav.addObject("url", "/notice/msg");
        mav.setViewName("redirect:/notice/msg.do");        
      } else {
        mav.addObject("url", "/admin/login_need");
        mav.setViewName("redirect:/notice/msg.do"); 
      }
    } else {
      mav.addObject("url", "/admin/login_need");
      mav.setViewName("redirect:/notice/msg.do"); 
    }
    
    return mav; // forward
  }
  
  /**
   * 전체 목록, 
   * http://localhost:9093/notice/list_all.do
   * @return
   */
  @RequestMapping(value="/notice/list_all.do", method = RequestMethod.GET)
  public ModelAndView list_all(HttpSession session) {
    ModelAndView mav = new ModelAndView();

      ArrayList<NoticeVO> list = this.noticeProc.list_all(); // 모든 공지사항 목록 조회

      // 리스트의 각 공지사항에 대해 특수 문자 처리
      for (NoticeVO noticeVO : list) {
        String title = Tool.convertChar(noticeVO.getTitle()); // 제목의 특수 문자 처리
        String content = Tool.convertChar(noticeVO.getContent()); // 내용의 특수 문자 처리

        noticeVO.setTitle(title);
        noticeVO.setContent(content);
      }

      mav.addObject("list", list); // 조회된 목록을 모델에 추가
      mav.setViewName("/notice/list_all"); // 공지사항 목록을 보여줄 JSP 뷰 설정

    return mav;
  }
  
  /**
   * 조회
   * http://localhost:9093/notice/read.do?contentsno=17
   * @return
   */
  @RequestMapping(value="/notice/read.do", method = RequestMethod.GET)
  public ModelAndView read(int noticeno) { // int cateno = (int)request.getParameter("cateno");
    ModelAndView mav = new ModelAndView();
    mav.setViewName("/notice/read"); // /WEB-INF/views/contents/read.jsp
    
    NoticeVO noticeVO = this.noticeProc.read(noticeno);
    
    String title = noticeVO.getTitle();
    String content = noticeVO.getContent();
    
    title = Tool.convertChar(title);  // 특수 문자 처리
    content = Tool.convertChar(content); 

    noticeVO.setTitle(title);
    noticeVO.setContent(content);  
    
    long size1 = noticeVO.getSize1();
    String size1_label = Tool.unit(size1);
    noticeVO.setSize1_label(size1_label);
    
    mav.addObject("noticeVO", noticeVO);
    
    return mav;
  }
  
  /**
   * 수정 폼
   * http://localhost:9091/contents/update_text.do?contentsno=1
   * 
   * @return
   */
  @RequestMapping(value = "/notice/update_text.do", method = RequestMethod.GET)
  public ModelAndView update_text(HttpSession session, int noticeno) {
    ModelAndView mav = new ModelAndView();
    
    if (adminProc.isAdmin(session)) { // 관리자로 로그인한경우
      NoticeVO noticeVO = this.noticeProc.read(noticeno);
      mav.addObject("noticeVO", noticeVO);
      
      mav.setViewName("/notice/update_text"); // /WEB-INF/views/contents/update_text.jsp
      // String content = "장소:\n인원:\n준비물:\n비용:\n기타:\n";
      // mav.addObject("content", content);

    } else {
      mav.addObject("url", "/admin/login_need"); // /WEB-INF/views/admin/login_need.jsp
      mav.setViewName("redirect:/notice/msg.do"); 
    }

    return mav; // forward
  }
  
  /**
   * 수정 처리
   * http://localhost:9091/notice/update_text.do?noticeno=1
   * 
   * @return
   */
  @RequestMapping(value = "/notice/update_text.do", method = RequestMethod.POST)
  public ModelAndView update_text(HttpSession session, NoticeVO noticeVO) {
    ModelAndView mav = new ModelAndView();
    
    // System.out.println("-> word: " + contentsVO.getWord());
    
    if (this.adminProc.isAdmin(session)) { // 관리자 로그인 확인
      // 공지사항 업데이트
      this.noticeProc.update_text(noticeVO);
      mav.setViewName("redirect:/notice/read.do?noticeno=" + noticeVO.getNoticeno() + "&now_page=" + noticeVO.getNow_page());
  } else { // 정상적인 로그인이 아닌 경우 로그인 유도
      mav.addObject("url", "/admin/login_need"); // /WEB-INF/views/admin/login_need.jsp
      mav.setViewName("redirect:/notice/msg.do"); 
  }
 
  return mav; // forward or redirect
  }

  /**
   * 파일 수정 폼
   * http://localhost:9093/notice/update_file.do?noticeno=1
   * 
   * @return
   */
  @RequestMapping(value = "/notice/update_file.do", method = RequestMethod.GET)
  public ModelAndView update_file(HttpSession session, int noticeno) {
    ModelAndView mav = new ModelAndView();
    
    if (adminProc.isAdmin(session)) { // 관리자로 로그인한경우
      NoticeVO noticeVO = this.noticeProc.read(noticeno);
      mav.addObject("noticeVO", noticeVO);
      
      
      mav.setViewName("/notice/update_file"); // /WEB-INF/views/contents/update_file.jsp
      
    } else {
      mav.addObject("url", "/admin/login_need"); // /WEB-INF/views/admin/login_need.jsp
      mav.setViewName("redirect:/notice/msg.do"); 
    }


    return mav; // forward
  }
  
  /**
   * 파일 수정 처리 http://localhost:9093/notice/update_file.do
   * 
   * @return
   */
  @RequestMapping(value = "/notice/update_file.do", method = RequestMethod.POST)
  public ModelAndView update_file(HttpSession session, NoticeVO noticeVO) {
    ModelAndView mav = new ModelAndView();
    
    if (this.adminProc.isAdmin(session)) {
      // 삭제할 파일 정보를 읽어옴, 기존에 등록된 레코드 저장용
      NoticeVO noticeVO_old = noticeProc.read(noticeVO.getNoticeno());
      
      // -------------------------------------------------------------------
      // 파일 삭제 시작
      // -------------------------------------------------------------------
      String file1saved = noticeVO_old.getFile1saved();  // 실제 저장된 파일명
      String thumb1 = noticeVO_old.getThumb1();       // 실제 저장된 preview 이미지 파일명
      long size1 = 0;
         
      String upDir =  Notice.getUploadDir(); // C:/kd/deploy/resort_v3sbm3c/contents/storage/
      
      Tool.deleteFile(upDir, file1saved);  // 실제 저장된 파일삭제
      Tool.deleteFile(upDir, thumb1);     // preview 이미지 삭제
      // -------------------------------------------------------------------
      // 파일 삭제 종료
      // -------------------------------------------------------------------
          
      // -------------------------------------------------------------------
      // 파일 전송 시작
      // -------------------------------------------------------------------
      String file1 = "";          // 원본 파일명 image

      // 전송 파일이 없어도 file1MF 객체가 생성됨.
      // <input type='file' class="form-control" name='file1MF' id='file1MF' 
      //           value='' placeholder="파일 선택">
      MultipartFile mf = noticeVO.getFile1MF();
          
      file1 = mf.getOriginalFilename(); // 원본 파일명
          
      if (size1 > 0) { // 폼에서 새롭게 올리는 파일이 있는지 파일 크기로 체크 ★
        // 파일 저장 후 업로드된 파일명이 리턴됨, spring.jsp, spring_1.jpg...
        file1saved = Upload.saveFileSpring(mf, upDir); 
        
        if (Tool.isImage(file1saved)) { // 이미지인지 검사
          // thumb 이미지 생성후 파일명 리턴됨, width: 250, height: 200
          thumb1 = Tool.preview(upDir, file1saved, 250, 200); 
        }
        
      } else { // 파일이 삭제만 되고 새로 올리지 않는 경우
        file1="";
        file1saved="";
        thumb1="";
        size1=0;
      }
          
      noticeVO.setFile1(file1);
      noticeVO.setFile1saved(file1saved);
      noticeVO.setThumb1(thumb1);
      noticeVO.setSize1(size1);
      // -------------------------------------------------------------------
      // 파일 전송 코드 종료
      // -------------------------------------------------------------------
          
      this.noticeProc.update_file(noticeVO); // Oracle 처리

      mav.addObject("noticeno", noticeVO.getNoticeno());
      mav.setViewName("redirect:/notice/read.do"); // request -> param으로 접근 전환
                
    } else {
      mav.addObject("url", "/admin/login_need"); // login_need.jsp, redirect parameter 적용
      mav.setViewName("redirect:/notice/msg.do"); // GET
    }

    // redirect하게되면 전부 데이터가 삭제됨으로 mav 객체에 다시 저장
    mav.addObject("now_page", noticeVO.getNow_page());
    
    return mav; // forward
  }   
  
  /**
   * 파일 삭제 폼
   * http://localhost:9093/notice/delete.do?noticeno=1
   * 
   * @return
   */
  @RequestMapping(value = "/notice/delete.do", method = RequestMethod.GET)
  public ModelAndView delete(HttpSession session, int noticeno) {
    ModelAndView mav = new ModelAndView();
    
    if (adminProc.isAdmin(session)) { // 관리자로 로그인한경우
      NoticeVO noticeVO = this.noticeProc.read(noticeno);
      mav.addObject("noticeVO", noticeVO);
      
      mav.setViewName("/notice/delete"); // /WEB-INF/views/contents/delete.jsp
      
    } else {
      mav.addObject("url", "/admin/login_need"); // /WEB-INF/views/admin/login_need.jsp
      mav.setViewName("redirect:/notice/msg.do"); 
    }


    return mav; // forward
  }
  
  /**
   * 삭제 처리 http://localhost:9093/notice/delete.do
   * 
   * @return
   */
  @RequestMapping(value = "/notice/delete.do", method = RequestMethod.POST)
  public ModelAndView delete(NoticeVO noticeVO) {
    ModelAndView mav = new ModelAndView();
    
    // -------------------------------------------------------------------
    // 파일 삭제 시작
    // -------------------------------------------------------------------
    // 삭제할 파일 정보를 읽어옴.
    NoticeVO contentsVO_read = noticeProc.read(noticeVO.getNoticeno());
        
    String file1saved = noticeVO.getFile1saved();
    String thumb1 = noticeVO.getThumb1();
    
    String uploadDir = Notice.getUploadDir();
    Tool.deleteFile(uploadDir, file1saved);  // 실제 저장된 파일삭제
    Tool.deleteFile(uploadDir, thumb1);     // preview 이미지 삭제
    // -------------------------------------------------------------------
    // 파일 삭제 종료
    // -------------------------------------------------------------------
        
    this.noticeProc.delete(noticeVO.getNoticeno()); // DBMS 삭제
        

 // 마지막 페이지의 마지막 레코드 삭제시의 페이지 번호 조정
    int now_page = noticeVO.getNow_page();

    // 전체 레코드 수를 기반으로 현재 페이지 번호를 조정
    int totalRecordCount = noticeProc.search_count();
    if (totalRecordCount % Notice.RECORD_PER_PAGE == 0) {
      now_page = now_page - 1; // 삭제 후 남은 레코드 수에 따라 페이지 번호 감소
      if (now_page < 1) {
        now_page = 1; // 최소 페이지 번호는 1
      }
    }

    mav.addObject("now_page", now_page);
    mav.setViewName("redirect:/notice/list_all.do");
    
    return mav;
  }   
      
}
