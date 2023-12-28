package dev.mvc.sect;

import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import dev.mvc.admin.AdminProcInter;


@Controller
public class SectCont {
  @Autowired // SectProcInter interface 구현한 객체를 만들어 자동으로 할당해라.
  @Qualifier("dev.mvc.sect.SectProc")
  private SectProcInter sectProc;
  
  @Autowired // SectProcInter interface 구현한 객체를 만들어 자동으로 할당해라.
  @Qualifier("dev.mvc.admin.AdminProc")
  private AdminProcInter adminProc;
  
  public SectCont() {
    System.out.println("-> SectCont created.");  
  }

//  // FORM 출력, http://localhost:9092/sect/create.do
//  @RequestMapping(value="/sect/create.do", method = RequestMethod.GET)
//  @ResponseBody // 단순 문자열로 출력, jsp 파일명 조합이 발생하지 않음.
//  public String create() {
//    return "<h3>GET 방식 FORM 출력</h3>";
//  }

  // FORM 출력, http://localhost:9092/sect/create.do
  @RequestMapping(value="/sect/create.do", method = RequestMethod.GET)
  public ModelAndView create() {
    ModelAndView mav = new ModelAndView();
    mav.setViewName("/sect/create"); // /WEB-INF/views/sect/create.jsp
    
    return mav;
  }
  
  // FORM 데이터 처리, http://localhost:9092/sect/create.do
  @RequestMapping(value="/sect/create.do", method = RequestMethod.POST)
  public ModelAndView create(SectVO sectVO) { // 자동으로 sectVO 객체가 생성되고 폼의 값이 할당됨
    ModelAndView mav = new ModelAndView();
    
    int cnt = this.sectProc.create(sectVO);
    System.out.println("-> cnt: " + cnt);
    
    if (cnt == 1) {
      // mav.addObject("code", "create_success"); // 키, 값
      // mav.addObject("name", sectVO.getName()); // 카테고리 이름 jsp로 전송
      mav.setViewName("redirect:/sect/list_all.do"); // 주소 자동 이동
    } else {
      mav.addObject("code", "create_fail");
      mav.setViewName("/sect/msg"); // /WEB-INF/views/sect/msg.jsp
    }
    
    mav.addObject("cnt", cnt); // request.setAttribute("cnt", cnt);
//    mav.addObject("cnt", 0); // request.setAttribute("cnt", cnt);
    
    return mav;
  }
  
  /**
   * 전체 목록
   * http://localhost:9093/sect/list_all.do
   * @return
   */
  @RequestMapping(value="/sect/list_all.do", method = RequestMethod.GET)
  public ModelAndView list_all(HttpSession session) {
    ModelAndView mav = new ModelAndView();
    
    if (this.adminProc.isAdmin(session) == true) {
      mav.setViewName("/sect/list_all"); // /WEB-INF/views/manager/list_all.jsp
      
      ArrayList<SectVO> list = this.sectProc.list_all();
      mav.addObject("list", list);
      
    } else {
      mav.setViewName("/admin/login_need"); // /WEB-INF/views/manager/login_need.jsp
      
    }
    
    return mav;
  }
  
  /**
   * 조회
   * http://localhost:9092/sect/read.do?sectno=1
   * @return
   */
  @RequestMapping(value="/sect/read.do", method = RequestMethod.GET)
  public ModelAndView read(int sectno) { // int sectno = (int)request.getParameter("sectno");
    ModelAndView mav = new ModelAndView();
    mav.setViewName("/sect/read"); // /WEB-INF/views/cate/read.jsp
    
    SectVO sectVO = this.sectProc.read(sectno);
    mav.addObject("sectVO", sectVO);
    
    return mav;
  }

  /**
   * 수정폼
   * http://localhost:9092/sect/update.do?sectno=1
   * @return
   */
  @RequestMapping(value="/sect/update.do", method = RequestMethod.GET)
  public ModelAndView update(HttpSession session, int sectno) { // int sectno = (int)request.getParameter("sectno");
    ModelAndView mav = new ModelAndView();
    
    if (this.adminProc.isAdmin(session) == true) {
      // mav.setViewName("/sect/update"); // /WEB-INF/views/sect/update.jsp
      mav.setViewName("/sect/list_all_update"); // /WEB-INF/views/sect/list_all_update.jsp
      
      SectVO sectVO = this.sectProc.read(sectno);
      mav.addObject("sectVO", sectVO);
      
      ArrayList<SectVO> list = this.sectProc.list_all();
      mav.addObject("list", list);
      
    } else {
      mav.setViewName("/manager/login_need"); // /WEB-INF/views/manager/login_need.jsp
      
    }
    
    return mav;
  }
  
  /**
   * 수정 처리, http://localhost:9092/sect/update.do
   * @param sectVO 수정할 내용
   * @return
   */
  
  @RequestMapping(value="/sect/update.do", method = RequestMethod.POST)
  public ModelAndView update(SectVO sectVO) { // 자동으로 sectVO 객체가 생성되고 폼의 값이 할당됨
    ModelAndView mav = new ModelAndView();
    
    int cnt = this.sectProc.update(sectVO); // 수정 처리
    System.out.println("-> cnt: " + cnt);
    
    if (cnt == 1) {
      // mav.addObject("code", "update_success"); // 키, 값
      // mav.addObject("name", sectVO.getName()); // 카테고리 이름 jsp로 전송
      mav.setViewName("redirect:/sect/list_all.do"); 
    } else {
      mav.addObject("code", "update_fail");
      mav.setViewName("/sect/msg"); // /WEB-INF/views/sect/msg.jsp
    }
    
    mav.addObject("cnt", cnt); // request.setAttribute("cnt", cnt);
//    mav.addObject("cnt", 0); // request.setAttribute("cnt", cnt);
    
    return mav;
  }
  
  /**
   * 삭제폼
   * http://localhost:9092/sect/delete.do?sectno=1
   * @return
   */
  @RequestMapping(value="/sect/delete.do", method = RequestMethod.GET)
  public ModelAndView delete(HttpSession session, int sectno) { // int sectno = (int)request.getParameter("sectno");
    ModelAndView mav = new ModelAndView();
    
    if (this.adminProc.isAdmin(session) == true) {
      // mav.setViewName("/sect/delete"); // /WEB-INF/views/sect/delete.jsp
      mav.setViewName("/sect/list_all_delete"); // /WEB-INF/views/sect/list_all_delete.jsp
      
      SectVO sectVO = this.sectProc.read(sectno);
      mav.addObject("sectVO", sectVO);
      
      ArrayList<SectVO> list = this.sectProc.list_all();
      mav.addObject("list", list);
      
      // 특정 섹션에 속한 레코드 갯수를 리턴

      
    } else {
      mav.setViewName("/manager/login_need"); // /WEB-INF/views/manager/login_need.jsp
   
    }
    
    return mav;
  }
  
  /**
   * 삭제 처리, http://localhost:9092/sect/delete.do?sectno=1
   * @param sectno 삭제할 레코드 번호
   * @return
   */
  
  @RequestMapping(value="/sect/delete.do", method = RequestMethod.POST)
  public ModelAndView delete_proc(int sectno) { // 자동으로 cateVO 객체가 생성되고 폼의 값이 할당됨
    ModelAndView mav = new ModelAndView();
    mav.setViewName("/sect/msg"); // /WEB-INF/views/sect/msg.jsp
    
    SectVO sectVO = this.sectProc.read(sectno); // 삭제할 레코드 정보 읽기
    
    int cnt = this.sectProc.delete(sectno); // 삭제 처리
    System.out.println("-> cnt: " + cnt);
    
    if (cnt == 1) {
      mav.addObject("code", "delete_success"); // 키, 값
      mav.addObject("name", sectVO.getName()); // 카테고리 이름 jsp로 전송
    } else {
      mav.addObject("code", "delete_fail");
    }
    
    mav.addObject("cnt", cnt); // request.setAttribute("cnt", cnt);
//    mav.addObject("cnt", 0); // request.setAttribute("cnt", cnt);
    
    return mav;
  }
  /**
   * 우선 순위 높임, 10 등 -> 1 등, http://localhost:9092/sect/update_seqno_forward.do?sectno=1
   * @param sectVO 수정할 내용
   * @return
   */
  @RequestMapping(value="/sect/update_seqno_forward.do", method = RequestMethod.GET)
  public ModelAndView update_seqno_forward(int sectno) {
    ModelAndView mav = new ModelAndView();
    
    int cnt = this.sectProc.update_seqno_forward(sectno);
    System.out.println("-> cnt: " + cnt);
    
    if (cnt == 1) {
      mav.setViewName("redirect:/sect/list_all.do"); 
      
    } else {
      mav.addObject("code", "update_fail");
      mav.setViewName("/sect/msg"); // /WEB-INF/views/sect/msg.jsp
    }
    
    mav.addObject("cnt", cnt); // request.setAttribute("cnt", cnt);
//    mav.addObject("cnt", 0); // request.setAttribute("cnt", cnt);
    
    return mav;
  }
  
  /**
   * 우선 순위 낮춤, 1 등 -> 10 등, http://localhost:9092/sect/update_seqno_backward.do?sectno=1
   * @param sectno 수정할 레코드 PK 번호
   * @return
   */
  @RequestMapping(value="/sect/update_seqno_backward.do", method = RequestMethod.GET)
  public ModelAndView update_seqno_backward(int sectno) {
    ModelAndView mav = new ModelAndView();
    
    int cnt = this.sectProc.update_seqno_backward(sectno);
    System.out.println("-> cnt: " + cnt);
    
    if (cnt == 1) {
      mav.setViewName("redirect:/sect/list_all.do"); 
      
    } else {
      mav.addObject("code", "update_fail");
      mav.setViewName("/sect/msg"); // /WEB-INF/views/sect/msg.jsp
    }
    
    mav.addObject("cnt", cnt); // request.setAttribute("cnt", cnt);
//    mav.addObject("cnt", 0); // request.setAttribute("cnt", cnt);
    
    return mav;
  }
  
  /**
   * 카테고리 공개 설정, http://localhost:9092/sect/update_visible_y.do?sectno=1
   * @param sectno 수정할 레코드 PK 번호
   * @return
   */
  @RequestMapping(value="/sect/update_visible_y.do", method = RequestMethod.GET)
  public ModelAndView update_visible_y(int sectno) { 
    ModelAndView mav = new ModelAndView();
    
    int cnt = this.sectProc.update_visible_y(sectno);
    System.out.println("-> cnt: " + cnt);
    
    if (cnt == 1) {
      mav.setViewName("redirect:/sect/list_all.do"); 
      
    } else {
      mav.addObject("code", "update_fail");
      mav.setViewName("/sect/msg"); // /WEB-INF/views/sect/msg.jsp
    }
    
    mav.addObject("cnt", cnt); // request.setAttribute("cnt", cnt);
//    mav.addObject("cnt", 0); // request.setAttribute("cnt", cnt);
    
    return mav;
  }
  
  /**
   * 카테고리 비공개 설정, http://localhost:9092/sect/update_visible_n.do?sectno=1
   * @param sectno 수정할 레코드 PK 번호
   * @return
   */
  @RequestMapping(value="/sect/update_visible_n.do", method = RequestMethod.GET)
  public ModelAndView update_visible_n(int sectno) { 
    ModelAndView mav = new ModelAndView();
    
    int cnt = this.sectProc.update_visible_n(sectno);
    System.out.println("-> cnt: " + cnt);
    
    if (cnt == 1) {
      mav.setViewName("redirect:/sect/list_all.do"); 
      
    } else {
      mav.addObject("code", "update_fail");
      mav.setViewName("/sect/msg"); // /WEB-INF/views/sect/msg.jsp
    }
    
    mav.addObject("cnt", cnt); // request.setAttribute("cnt", cnt);
//    mav.addObject("cnt", 0); // request.setAttribute("cnt", cnt);
    
    return mav;
  }  
}




