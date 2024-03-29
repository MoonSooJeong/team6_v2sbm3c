package dev.mvc.information;

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
import dev.mvc.sect.SectProcInter;
import dev.mvc.sect.SectVO;
import dev.mvc.tool.Tool;
import dev.mvc.tool.Upload;

@Controller
public class InformationCont {
  @Autowired
  @Qualifier("dev.mvc.admin.AdminProc") // @Component("dev.mvc.admin.AdminProc")
  private AdminProcInter adminProc;
  
  @Autowired
  @Qualifier("dev.mvc.sect.SectProc")  // @Component("dev.mvc.sect.sectProc")
  private SectProcInter sectProc;
  
  @Autowired
  @Qualifier("dev.mvc.information.InformationProc") // @Component("dev.mvc.contents.ContentsProc")
  private InformationProcInter informationProc;
  
  public InformationCont () {
    System.out.println("-> InformationCont created.");
  }
  
  /**
   * POST 요청시 JSP 페이지에서 JSTL 호출 기능 지원, 새로고침 방지, EL에서 param으로 접근
   * POST → url → GET → 데이터 전송
   * @return
   */
  @RequestMapping(value="/information/msg.do", method=RequestMethod.GET)
  public ModelAndView msg(String url){
    ModelAndView mav = new ModelAndView();

    mav.setViewName(url); // forward
    
    return mav; // forward
  }
  
  // 등록 폼, contents 테이블은 FK로 sectno를 사용함.
  // http://localhost:9093/information/create.do  X
  // http://localhost:9093/information/create.do?sectno=1  // sectno 변수값을 보내는 목적
  // http://localhost:9093/information/create.do?sectno=2
  // http://localhost:9093/information/create.do?sectno=3
  @RequestMapping(value="/information/create.do", method = RequestMethod.GET)
  public ModelAndView create(int sectno) {

    ModelAndView mav = new ModelAndView();

    SectVO sectVO = this.sectProc.read(sectno); // create.jsp에 카테고리 정보를 출력하기위한 목적
    mav.addObject("sectVO", sectVO);
//    request.setAttribute("sectVO", sectVO);
    
    mav.setViewName("/information/create"); // /webapp/WEB-INF/views/contents/create.jsp
    
    return mav;
  }
  
  /**
   * 등록 처리 http://localhost:9093/contents/create.do
   * 
   * @return
   */
  @RequestMapping(value = "/information/create.do", method = RequestMethod.POST)
  public ModelAndView create(HttpServletRequest request, HttpSession session, InformationVO informationVO) {
    ModelAndView mav = new ModelAndView();
    
    if (adminProc.isAdmin(session)) { // 관리자로 로그인한경우
      // ------------------------------------------------------------------------------
      // 파일 전송 코드 시작
      // ------------------------------------------------------------------------------
      String file1 = "";          // 원본 파일명 image
      String file1saved = "";   // 저장된 파일명, image
      String thumb1 = "";     // preview image

      String upDir =  Information.getUploadDir(); // 파일을 업로드할 폴더 준비
      System.out.println("-> upDir: " + upDir);
      
      // 전송 파일이 없어도 file1MF 객체가 생성됨.
      // <input type='file' class="form-control" name='file1MF' id='file1MF' 
      //           value='' placeholder="파일 선택">
      MultipartFile mf = informationVO.getFile1MF();
      
      file1 = mf.getOriginalFilename(); // 원본 파일명 산출, 01.jpg
      System.out.println("-> 원본 파일명 산출 file1: " + file1);
      
      if (Tool.checkUploadFile(file1) == true) { // 업로드 가능한 파일인지 검사
        long size1 = mf.getSize();  // 파일 크기
        
        if (size1 > 0) { // 파일 크기 체크
          // 파일 저장 후 업로드된 파일명이 리턴됨, spring.jsp, spring_1.jpg...
          file1saved = Upload.saveFileSpring(mf, upDir); 
          
          if (Tool.isImage(file1saved)) { // 이미지인지 검사
            // thumb 이미지 생성후 파일명 리턴됨, width: 200, height: 150
            thumb1 = Tool.preview(upDir, file1saved, 200, 150); 
          }
          
        }    
        
        informationVO.setFile1(file1);   // 순수 원본 파일명
        informationVO.setFile1saved(file1saved); // 저장된 파일명(파일명 중복 처리)
        informationVO.setThumb1(thumb1);      // 원본이미지 축소판
        informationVO.setSize1(size1);  // 파일 크기
        // ------------------------------------------------------------------------------
        // 파일 전송 코드 종료
        // ------------------------------------------------------------------------------
        
        // Call By Reference: 메모리 공유, Hashcode 전달
        int adminno = (int)session.getAttribute("adminno"); // adminno FK
        informationVO.setAdminno(adminno);
        int cnt = this.informationProc.create(informationVO); 
        
        // ------------------------------------------------------------------------------
        // PK의 return
        // ------------------------------------------------------------------------------
        // System.out.println("--> informationno: " + informationVO.getInformationno());
        // mav.addObject("informationno", informationVO.getInformationno()); // redirect parameter 적용
        // ------------------------------------------------------------------------------
        
        if (cnt == 1) {
            mav.addObject("code", "create_success");
            // sectProc.increaseCnt(informationVO.getSectno()); // 글수 증가
        } else {
            mav.addObject("code", "create_fail");
        }
        mav.addObject("cnt", cnt); // request.setAttribute("cnt", cnt)
        
        // System.out.println("--> sectno: " + informationVO.getsectno());
        // redirect시에 hidden tag로 보낸것들이 전달이 안됨으로 request에 다시 저장
        mav.addObject("sectno", informationVO.getSectno()); // redirect parameter 적용
        
        mav.addObject("url", "/information/msg"); // msg.jsp, redirect parameter 적용
        mav.setViewName("redirect:/information/msg.do"); // Post -> Get - param...        
      } else {
        mav.addObject("cnt", "0"); // 업로드 할 수 없는 파일
        mav.addObject("code", "check_upload_file_fail"); // 업로드 할 수 없는 파일
        mav.addObject("url", "/information/msg"); // msg.jsp, redirect parameter 적용
        mav.setViewName("redirect:/information/msg.do"); // Post -> Get - param...        
      }
    } else {
      mav.addObject("url", "/admin/login_need"); // /WEB-INF/views/admin/login_need.jsp
      mav.setViewName("redirect:/information/msg.do"); 
    }
    
    return mav; // forward
  }

  /**
   * 전체 목록, 관리자만 사용 가능
   * http://localhost:9093/information/list_all.do
   * @return
   */
  @RequestMapping(value="/information/list_all.do", method = RequestMethod.GET)
  public ModelAndView list_all(HttpSession session) {
    System.out.println("-> list_all");
    ModelAndView mav = new ModelAndView();
    
    if (this.adminProc.isAdmin(session) == true) {
      mav.setViewName("/information/list_all"); // /WEB-INF/views/information/list_all.jsp
      
      ArrayList<InformationVO> list = this.informationProc.list_all();
     
      // for문을 사용하여 객체를 추출, Call By Reference 기반의 원본 객체 값 변경
      for (InformationVO informationVO : list) {
        String title = informationVO.getTitle();
        String content = informationVO.getContent();
        
        title = Tool.convertChar(title);  // 특수 문자 처리
        content = Tool.convertChar(content); 
        
        informationVO.setTitle(title);
        informationVO.setContent(content);  

      }
      
      mav.addObject("list", list);
      
    } else {
      mav.setViewName("/admin/login_need"); // /WEB-INF/views/admin/login_need.jsp
      
    }
    
    return mav;
  }
  
  
   /**
   * 목록 + 검색 + 페이징 지원
   * 검색하지 않는 경우
   * http://localhost:9093/information/list_by_sectno.do?sectno=2&word=&now_page=1
   * 검색하는 경우
   * http://localhost:9093/information/list_by_sectno.do?sectno=2&word=탐험&now_page=1
   * 
   * @param sectno
   * @param word
   * @param now_page
   * @return
   */
  @RequestMapping(value = "/information/list_by_sectno.do", method = RequestMethod.GET)
  public ModelAndView list_by_sectno(InformationVO informationVO) {
    ModelAndView mav = new ModelAndView();
  
    // 검색 목록
    ArrayList<InformationVO> list = informationProc.list_by_sectno_search_paging(informationVO);
    
    // for문을 사용하여 객체를 추출, Call By Reference 기반의 원본 객체 값 변경
    for (InformationVO vo : list) {
      String title = vo.getTitle();
      String content = vo.getContent();
      
      title = Tool.convertChar(title);  // 특수 문자 처리
      content = Tool.convertChar(content); 
      
      vo.setTitle(title);
      vo.setContent(content);  
  
    }
    
    mav.addObject("list", list);
  
    SectVO sectVO = sectProc.read(informationVO.getSectno());
    mav.addObject("sectVO", sectVO);
  
    HashMap<String, Object> hashMap = new HashMap<String, Object>();
    hashMap.put("sectno", informationVO.getSectno());
    hashMap.put("word", informationVO.getWord());
    
    int search_count = this.informationProc.search_count(hashMap);  // 검색된 레코드 갯수 ->  전체 페이지 규모 파악
    mav.addObject("search_count", search_count);
    
    /*
     * SPAN태그를 이용한 박스 모델의 지원, 1 페이지부터 시작 현재 페이지: 11 / 22 [이전] 11 12 13 14 15 16 17
     * 18 19 20 [다음]
     * @param sectno 카테고리번호
     * @param now_page 현재 페이지
     * @param word 검색어
     * @param list_file 목록 파일명
     * @return 페이징용으로 생성된 HTML/CSS tag 문자열
     */
    String paging = informationProc.pagingBox(informationVO.getSectno(), informationVO.getNow_page(), informationVO.getWord(), "list_by_sectno.do", search_count);
    mav.addObject("paging", paging);
  
    // mav.addObject("now_page", now_page);
    
    mav.setViewName("/information/list_by_sectno");  // /information/list_by_sectno.jsp
  
    return mav;
  }

  /**
  * 목록 + 검색 + 페이징 지원 + Grid
  * 검색하지 않는 경우
  * http://localhost:9093/information/list_by_sectno_grid.do?sectno=2&word=&now_page=1
  * 검색하는 경우
  * http://localhost:9093/information/list_by_sectno_grid.do?sectno=2&word=탐험&now_page=1
  * 
  * @param sectno
  * @param word
  * @param now_page
  * @return
  */
  @RequestMapping(value = "/information/list_by_sectno_grid.do", method = RequestMethod.GET)
  public ModelAndView list_by_sectno_grid(InformationVO informationVO) {
     ModelAndView mav = new ModelAndView();
   
     // 검색 목록
   ArrayList<InformationVO> list = informationProc.list_by_sectno_search_paging(informationVO);
   
   // for문을 사용하여 객체를 추출, Call By Reference 기반의 원본 객체 값 변경
   for (InformationVO vo : list) {
     String title = vo.getTitle();
     String content = vo.getContent();
     
     title = Tool.convertChar(title);  // 특수 문자 처리
       content = Tool.convertChar(content); 
       
       vo.setTitle(title);
       vo.setContent(content);  
   
     }
     
     mav.addObject("list", list);
   
     SectVO sectVO = sectProc.read(informationVO.getSectno());
     mav.addObject("sectVO", sectVO);
     
     HashMap<String, Object> hashMap = new HashMap<String, Object>();
     hashMap.put("sectno", informationVO.getSectno());
     hashMap.put("word", informationVO.getWord());
     
     int search_count = this.informationProc.search_count(hashMap);  // 검색된 레코드 갯수 ->  전체 페이지 규모 파악
     mav.addObject("search_count", search_count);
   
     /*
  * SPAN태그를 이용한 박스 모델의 지원, 1 페이지부터 시작 현재 페이지: 11 / 22 [이전] 11 12 13 14 15 16 17
  * 18 19 20 [다음]
  * @param sectno 카테고리번호
  * @param now_page 현재 페이지
  * @param word 검색어
  * @param list_file 목록 파일명
  * @return 페이징용으로 생성된 HTML/CSS tag 문자열
  */
   String paging = informationProc.pagingBox(informationVO.getSectno(), informationVO.getNow_page(), informationVO.getWord(), "list_by_sectno_grid.do", search_count);
   mav.addObject("paging", paging);
   
     // mav.addObject("now_page", now_page);
   
   mav.setViewName("/information/list_by_sectno_grid");  // /information/list_by_sectno_grid.jsp
   
     return mav;
   }
 
 
  /**
   * 조회
   * http://localhost:9093/information/read.do?informationno=17
   * @return
   */
  @RequestMapping(value="/information/read.do", method = RequestMethod.GET)
  public ModelAndView read(int informationno) { // int sectno = (int)request.getParameter("sectno");
    ModelAndView mav = new ModelAndView();
    mav.setViewName("/information/read"); // /WEB-INF/views/information/read.jsp
    
    InformationVO informationVO = this.informationProc.read(informationno);
    
    String title = informationVO.getTitle();
    String content = informationVO.getContent();
    
    title = Tool.convertChar(title);  // 특수 문자 처리
    content = Tool.convertChar(content); 
    
    informationVO.setTitle(title);
    informationVO.setContent(content);  
    
    long size1 = informationVO.getSize1();
    String size1_label = Tool.unit(size1);
    informationVO.setSize1_label(size1_label);
    
    mav.addObject("informationVO", informationVO);
    
    SectVO sectVO = this.sectProc.read(informationVO.getSectno());
    mav.addObject("sectVO", sectVO);
    
    return mav;
  }
  
  /**
   * 맵 등록/수정/삭제 폼
   * http://localhost:9093/information/map.do?informationno=1
   * @return
   */
  @RequestMapping(value="/information/map.do", method=RequestMethod.GET )
  public ModelAndView map(int informationno) {
    ModelAndView mav = new ModelAndView();

    InformationVO informationVO = this.informationProc.read(informationno); // map 정보 읽어 오기
    mav.addObject("informationVO", informationVO); // request.setAttribute("informationVO", informationVO);

    SectVO sectVO = this.sectProc.read(informationVO.getSectno()); // 그룹 정보 읽기
    mav.addObject("sectVO", sectVO); 

    mav.setViewName("/information/map"); // /WEB-INF/views/contents/map.jsp
        
    return mav;
  }
  
  /**
   * MAP 등록/수정/삭제 처리
   * http://localhost:9093/information/map.do
   * @param informationVO
   * @return
   */
  @RequestMapping(value="/information/map.do", method = RequestMethod.POST)
  public ModelAndView map_update(int informationno, String map) {
    ModelAndView mav = new ModelAndView();
    
    HashMap<String, Object> hashMap = new HashMap<String, Object>();
    hashMap.put("informationno", informationno);
    hashMap.put("map", map);
    
    this.informationProc.map(hashMap);
    
    mav.setViewName("redirect:/information/read.do?informationno=" + informationno); 
    // /webapp/WEB-INF/views/information/read.jsp
    
    return mav;
  }

  /**
   * Youtube 등록/수정/삭제 폼
   * http://localhost:9093/information/map.do?informationno=1
   * @return
   */
  @RequestMapping(value="/information/youtube.do", method=RequestMethod.GET )
  public ModelAndView youtube(int informationno) {
    ModelAndView mav = new ModelAndView();

    InformationVO informationVO = this.informationProc.read(informationno); // map 정보 읽어 오기
    mav.addObject("informationVO", informationVO); // request.setAttribute("contentsVO", informationVO);

    SectVO sectVO = this.sectProc.read(informationVO.getSectno()); // 그룹 정보 읽기
    mav.addObject("sectVO", sectVO); 

    mav.setViewName("/information/youtube"); // /WEB-INF/views/information/youtube.jsp
        
    return mav;
  }
  
  /**
   * Youtube 등록/수정/삭제 처리
   * http://localhost:9093/information/map.do
   * @param informationno 글 번호
   * @param youtube Youtube url의 소스 코드
   * @return
   */
  @RequestMapping(value="/information/youtube.do", method = RequestMethod.POST)
  public ModelAndView youtube_update(int informationno, String youtube) {
    ModelAndView mav = new ModelAndView();
    
    if (youtube.trim().length() > 0) {  // 삭제 중인지 확인, 삭제가 아니면 youtube 크기 변경
      youtube = Tool.youtubeResize(youtube, 640);  // youtube 영상의 크기를 width 기준 640 px로 변경
    }    
    
    HashMap<String, Object> hashMap = new HashMap<String, Object>();
    hashMap.put("informationno", informationno);
    hashMap.put("youtube", youtube);
    
    this.informationProc.youtube(hashMap);
    
    mav.setViewName("redirect:/information/read.do?informationno=" + informationno); 
    // /webapp/WEB-INF/views/information/read.jsp
    
    return mav;
  }
  
  /**
   * 수정 폼
   * http://localhost:9093/information/update_text.do?informationno=1
   * 
   * @return
   */
  @RequestMapping(value = "/information/update_text.do", method = RequestMethod.GET)
  public ModelAndView update_text(HttpSession session, int informationno) {
    ModelAndView mav = new ModelAndView();
    
    if (adminProc.isAdmin(session)) { // 관리자로 로그인한경우
      InformationVO informationVO = this.informationProc.read(informationno);
      mav.addObject("informationVO", informationVO);
      
      SectVO sectVO = this.sectProc.read(informationVO.getSectno());
      mav.addObject("sectVO", sectVO);
      
      mav.setViewName("/information/update_text"); // /WEB-INF/views/information/update_text.jsp
      // String content = "장소:\n인원:\n준비물:\n비용:\n기타:\n";
      // mav.addObject("content", content);

    } else {
      mav.addObject("url", "/admin/login_need"); // /WEB-INF/views/admin/login_need.jsp
      mav.setViewName("redirect:/information/msg.do"); 
    }

    return mav; // forward
  }
  
  /**
   * 수정 처리
   * http://localhost:9093/information/update_text.do?informationno=1
   * 
   * @return
   */
  @RequestMapping(value = "/information/update_text.do", method = RequestMethod.POST)
  public ModelAndView update_text(HttpSession session, InformationVO informationVO) {
    ModelAndView mav = new ModelAndView();
    
    // System.out.println("-> word: " + informationVO.getWord());
    
    if (this.adminProc.isAdmin(session)) { // 관리자 로그인 확인
      HashMap<String, Object> hashMap = new HashMap<String, Object>();
      hashMap.put("informationno", informationVO.getInformationno());
      hashMap.put("passwd", informationVO.getPasswd());
      
      if (this.informationProc.password_check(hashMap) == 1) { // 패스워드 일치
        this.informationProc.update_text(informationVO); // 글수정  
         
        // mav 객체 이용
        mav.addObject("informationno", informationVO.getInformationno());
        mav.addObject("sectno", informationVO.getSectno());
        mav.setViewName("redirect:/information/read.do"); // 페이지 자동 이동
        
      } else { // 패스워드 불일치
        mav.addObject("code", "passwd_fail");
        mav.addObject("cnt", 0);
        mav.addObject("url", "/information/msg"); // msg.jsp, redirect parameter 적용
        mav.setViewName("redirect:/information/msg.do");  // POST -> GET -> JSP 출력
      }
    } else { // 정상적인 로그인이 아닌 경우 로그인 유도
      mav.addObject("url", "/admin/login_need"); // /WEB-INF/views/admin/login_need.jsp
      mav.setViewName("redirect:/information/msg.do"); 
    }
    
    mav.addObject("now_page", informationVO.getNow_page()); // POST -> GET: 데이터 분실이 발생함으로 다시하번 데이터 저장 ★
    
    // URL에 파라미터의 전송
 
    return mav; // forward
  }

  /**
   * 파일 수정 폼
   * http://localhost:9093/information/update_file.do?informationno=1
   * 
   * @return
   */
  @RequestMapping(value = "/information/update_file.do", method = RequestMethod.GET)
  public ModelAndView update_file(HttpSession session, int informationno) {
    ModelAndView mav = new ModelAndView();
    
    if (adminProc.isAdmin(session)) { // 관리자로 로그인한경우
      InformationVO informationVO = this.informationProc.read(informationno);
      mav.addObject("informationVO", informationVO);
      
      SectVO sectVO = this.sectProc.read(informationVO.getSectno());
      mav.addObject("sectVO", sectVO);
      
      mav.setViewName("/information/update_file"); // /WEB-INF/views/information/update_file.jsp
      
    } else {
      mav.addObject("url", "/admin/login_need"); // /WEB-INF/views/admin/login_need.jsp
      mav.setViewName("redirect:/information/msg.do"); 
    }


    return mav; // forward
  }
  
  /**
   * 파일 수정 처리 http://localhost:9093/information/update_file.do
   * 
   * @return
   */
  @RequestMapping(value = "/information/update_file.do", method = RequestMethod.POST)
  public ModelAndView update_file(HttpSession session, InformationVO informationVO) {
    ModelAndView mav = new ModelAndView();
    
    if (this.adminProc.isAdmin(session)) {
      // 삭제할 파일 정보를 읽어옴, 기존에 등록된 레코드 저장용
      InformationVO informationVO_old = informationProc.read(informationVO.getInformationno());
      
      // -------------------------------------------------------------------
      // 파일 삭제 시작
      // -------------------------------------------------------------------
      String file1saved = informationVO_old.getFile1saved();  // 실제 저장된 파일명
      String thumb1 = informationVO_old.getThumb1();       // 실제 저장된 preview 이미지 파일명
      long size1 = 0;
         
      String upDir =  Information.getUploadDir(); // C:/kd/deploy/resort_v3sbm3c/information/storage/
      
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
      MultipartFile mf = informationVO.getFile1MF();
          
      file1 = mf.getOriginalFilename(); // 원본 파일명
      size1 = mf.getSize();  // 파일 크기
          
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
          
      informationVO.setFile1(file1);
      informationVO.setFile1saved(file1saved);
      informationVO.setThumb1(thumb1);
      informationVO.setSize1(size1);
      // -------------------------------------------------------------------
      // 파일 전송 코드 종료
      // -------------------------------------------------------------------
          
      this.informationProc.update_file(informationVO); // Oracle 처리

      mav.addObject("informationno", informationVO.getInformationno());
      mav.addObject("sectno", informationVO.getSectno());
      mav.setViewName("redirect:/information/read.do"); // request -> param으로 접근 전환
                
    } else {
      mav.addObject("url", "/admin/login_need"); // login_need.jsp, redirect parameter 적용
      mav.setViewName("redirect:/information/msg.do"); // GET
    }

    // redirect하게되면 전부 데이터가 삭제됨으로 mav 객체에 다시 저장
    mav.addObject("now_page", informationVO.getNow_page());
    
    return mav; // forward
  }   
  
  /**
   * 파일 삭제 폼
   * http://localhost:9093/information/delete.do?informationno=1
   * 
   * @return
   */
  @RequestMapping(value = "/information/delete.do", method = RequestMethod.GET)
  public ModelAndView delete(HttpSession session, int informationno) {
    ModelAndView mav = new ModelAndView();
    
    if (adminProc.isAdmin(session)) { // 관리자로 로그인한경우
      InformationVO informationVO = this.informationProc.read(informationno);
      mav.addObject("informationVO", informationVO);
      
      SectVO sectVO = this.sectProc.read(informationVO.getSectno());
      mav.addObject("sectVO", sectVO);
      
      mav.setViewName("/information/delete"); // /WEB-INF/views/information/delete.jsp
      
    } else {
      mav.addObject("url", "/admin/login_need"); // /WEB-INF/views/admin/login_need.jsp
      mav.setViewName("redirect:/information/msg.do"); 
    }


    return mav; // forward
  }
  
  /**
   * 삭제 처리 http://localhost:9093/information/delete.do
   * 
   * @return
   */
  @RequestMapping(value = "/information/delete.do", method = RequestMethod.POST)
  public ModelAndView delete(InformationVO informationVO) {
    ModelAndView mav = new ModelAndView();
    
    // -------------------------------------------------------------------
    // 파일 삭제 시작
    // -------------------------------------------------------------------
    // 삭제할 파일 정보를 읽어옴.
    InformationVO informationVO_read = informationProc.read(informationVO.getInformationno());
        
    String file1saved = informationVO.getFile1saved();
    String thumb1 = informationVO.getThumb1();
    
    String uploadDir = Information.getUploadDir();
    Tool.deleteFile(uploadDir, file1saved);  // 실제 저장된 파일삭제
    Tool.deleteFile(uploadDir, thumb1);     // preview 이미지 삭제
    // -------------------------------------------------------------------
    // 파일 삭제 종료
    // -------------------------------------------------------------------
        
    this.informationProc.delete(informationVO.getInformationno()); // DBMS 삭제
        
    // -------------------------------------------------------------------------------------
    // 마지막 페이지의 마지막 레코드 삭제시의 페이지 번호 -1 처리
    // -------------------------------------------------------------------------------------    
    // 마지막 페이지의 마지막 10번째 레코드를 삭제후
    // 하나의 페이지가 3개의 레코드로 구성되는 경우 현재 9개의 레코드가 남아 있으면
    // 페이지수를 4 -> 3으로 감소 시켜야함, 마지막 페이지의 마지막 레코드 삭제시 나머지는 0 발생
    int now_page = informationVO.getNow_page();
    
    HashMap<String, Object> hashMap = new HashMap<String, Object>();
    hashMap.put("sectno", informationVO.getSectno());
    hashMap.put("word", informationVO.getWord());
    
    if (informationProc.search_count(hashMap) % Information.RECORD_PER_PAGE == 0) {
      now_page = now_page - 1; // 삭제시 DBMS는 바로 적용되나 크롬은 새로고침등의 필요로 단계가 작동 해야함.
      if (now_page < 1) {
        now_page = 1; // 시작 페이지
      }
    }
    // -------------------------------------------------------------------------------------

    mav.addObject("sectno", informationVO.getSectno());
    mav.addObject("now_page", now_page);
    mav.setViewName("redirect:/information/list_by_sectno.do"); 
    
    return mav;
  }   
      
  // http://localhost:9093/information/delete_by_sectno.do?sectno=1
  // 파일 삭제 -> 레코드 삭제
  @RequestMapping(value = "/information/delete_by_sectno.do", method = RequestMethod.GET)
  public String delete_by_sectno(int sectno) {
    ArrayList<InformationVO> list = this.informationProc.list_by_sectno(sectno);
    
    for(InformationVO informationVO : list) {
      // -------------------------------------------------------------------
      // 파일 삭제 시작
      // -------------------------------------------------------------------
      String file1saved = informationVO.getFile1saved();
      String thumb1 = informationVO.getThumb1();
      
      String uploadDir = Information.getUploadDir();
      Tool.deleteFile(uploadDir, file1saved);  // 실제 저장된 파일삭제
      Tool.deleteFile(uploadDir, thumb1);     // preview 이미지 삭제
      // -------------------------------------------------------------------
      // 파일 삭제 종료
      // -------------------------------------------------------------------
    }
    
    int cnt = this.informationProc.delete_by_sectno(sectno);
    System.out.println("-> count: " + cnt);
    
    return "";
  
  }
  
  /**
   * Gallery 전체 이미지 출력
   * http://localhost:9093/information/list_all_gallery.do
   * @return
   */
  @RequestMapping(value="/information/list_all_gallery.do", method = RequestMethod.GET)
  public ModelAndView list_all_gallery(HttpSession session) {
    ModelAndView mav = new ModelAndView();
    
    if (this.adminProc.isAdmin(session) == true) {
      mav.setViewName("/information/list_all_gallery"); // /WEB-INF/views/information/list_all_gallery.jsp
      
      ArrayList<InformationVO> list = this.informationProc.list_all();
      mav.addObject("list", list);
      
    } else {
      mav.setViewName("/admin/login_need"); // /WEB-INF/views/admin/login_need.jsp
      
    }
    
    return mav;
  }
  
}


