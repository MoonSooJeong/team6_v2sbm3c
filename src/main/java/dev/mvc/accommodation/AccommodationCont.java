package dev.mvc.accommodation;

import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import dev.mvc.admin.AdminProcInter;

@Controller
public class AccommodationCont {

  @Autowired // accommodationProcInter interface 구현한 객체를 만들어 자동으로 할당해라.
  @Qualifier("dev.mvc.accommodation.AccommodationProc")
  private AccommodationProcInter accommodationProc;
  
  @Autowired // accommodationProcInter interface 구현한 객체를 만들어 자동으로 할당해라.
  @Qualifier("dev.mvc.admin.AdminProc")
  private AdminProcInter adminProc;
  
  public AccommodationCont() {
    System.out.println("-> AccommodationCont created.");
  } 
  

  @RequestMapping(value="/accommodation/create.do", method = RequestMethod.GET)
  public ModelAndView create() {
    ModelAndView mav = new ModelAndView();
    mav.setViewName("/accommodation/create"); 
    
    return mav;
  }
  
  @RequestMapping(value="/accommodation/create.do", method = RequestMethod.POST)
  public ModelAndView create(AccommodationVO accommodationVO) { // 자동으로 accommodationVO 객체가 생성되고 폼의 값이 할당됨
    ModelAndView mav = new ModelAndView();
    
    int cnt = this.accommodationProc.create(accommodationVO);
    System.out.println("-> cnt: " + cnt);
    
    if (cnt == 1) {

      mav.setViewName("redirect:/accommodation/list_all.do"); 
    } else {
      mav.addObject("code", "create_fail");
      mav.setViewName("/accommodation/msg"); 
    }
    
    mav.addObject("cnt", cnt); 

    
    return mav;
  }
  
  /**
   * 전체 목록
   * http://localhost:9093/accommodation/list_all.do
   * @return
   */
  @RequestMapping(value="/accommodation/list_all.do", method = RequestMethod.GET)
  public ModelAndView list_all(HttpSession session) {
    ModelAndView mav = new ModelAndView();
    
    if (this.adminProc.isAdmin(session) == true) {
      mav.setViewName("/accommodation/list_all"); // /WEB-INF/views/manager/list_all.jsp
      
      ArrayList<AccommodationVO> list = this.accommodationProc.list_all();
      mav.addObject("list", list);
      
    } else {
      mav.setViewName("/admin/login_need"); // /WEB-INF/views/manager/login_need.jsp
      
    }
    
    return mav;
  }
  
  /**
   * 조회
   * http://localhost:9093/accommodation/read.do?accommodationno=1
   * @return
   */
  @RequestMapping(value="/accommodation/read.do", method = RequestMethod.GET)
  public ModelAndView read(int accommodationno) { // int accommodationno = (int)request.getParameter("accommodationno");
    ModelAndView mav = new ModelAndView();
    mav.setViewName("/accommodation/read"); // /WEB-INF/views/cate/read.jsp
    
    AccommodationVO accommodationVO = this.accommodationProc.read(accommodationno);
    mav.addObject("accommodationVO", accommodationVO);
    
    return mav;
  }

  /**
   * 수정폼
   * http://localhost:9093/accommodation/update.do?accommodationno=1
   * @return
   */
  @RequestMapping(value="/accommodation/update.do", method = RequestMethod.GET)
  public ModelAndView update(HttpSession session, int accommodationno) { // int accommodationno = (int)request.getParameter("accommodationno");
    ModelAndView mav = new ModelAndView();
    
    if (this.adminProc.isAdmin(session) == true) {
      // mav.setViewName("/accommodation/update"); // /WEB-INF/views/accommodation/update.jsp
      mav.setViewName("/accommodation/list_all_update"); // /WEB-INF/views/accommodation/list_all_update.jsp
      
      AccommodationVO accommodationVO = this.accommodationProc.read(accommodationno);
      mav.addObject("accommodationVO", accommodationVO);
      
      ArrayList<AccommodationVO> list = this.accommodationProc.list_all();
      mav.addObject("list", list);
      
    } else {
      mav.setViewName("/manager/login_need"); // /WEB-INF/views/manager/login_need.jsp
      
    }
    
    return mav;
  }
  
  /**
   * 수정 처리, http://localhost:9093/accommodation/update.do
   * @param accommodationVO 수정할 내용
   * @return
   */
  
  @RequestMapping(value="/accommodation/update.do", method = RequestMethod.POST)
  public ModelAndView update(AccommodationVO accommodationVO) { // 자동으로 accommodationVO 객체가 생성되고 폼의 값이 할당됨
    ModelAndView mav = new ModelAndView();
    
    int cnt = this.accommodationProc.update(accommodationVO); // 수정 처리
    System.out.println("-> cnt: " + cnt);
    
    if (cnt == 1) {
      // mav.addObject("code", "update_success"); // 키, 값
      // mav.addObject("name", accommodationVO.getName()); // 카테고리 이름 jsp로 전송
      mav.setViewName("redirect:/accommodation/list_all.do"); 
    } else {
      mav.addObject("code", "update_fail");
      mav.setViewName("/accommodation/msg"); // /WEB-INF/views/accommodation/msg.jsp
    }
    
    mav.addObject("cnt", cnt); // request.setAttribute("cnt", cnt);
//    mav.addObject("cnt", 0); // request.setAttribute("cnt", cnt);
    
    return mav;
  }
  
  /**
   * 삭제폼
   * http://localhost:9093/accommodation/delete.do?accommodationno=1
   * @return
   */
  @RequestMapping(value="/accommodation/delete.do", method = RequestMethod.GET)
  public ModelAndView delete(HttpSession session, int accommodationno) { // int accommodationno = (int)request.getParameter("accommodationno");
    ModelAndView mav = new ModelAndView();
    
    if (this.adminProc.isAdmin(session) == true) {
      // mav.setViewName("/accommodation/delete"); // /WEB-INF/views/accommodation/delete.jsp
      mav.setViewName("/accommodation/list_all_delete"); // /WEB-INF/views/accommodation/list_all_delete.jsp
      
      AccommodationVO accommodationVO = this.accommodationProc.read(accommodationno);
      mav.addObject("accommodationVO", accommodationVO);
      
      ArrayList<AccommodationVO> list = this.accommodationProc.list_all();
      mav.addObject("list", list);
      
      // 특정 섹션에 속한 레코드 갯수를 리턴

      
    } else {
      mav.setViewName("/manager/login_need"); // /WEB-INF/views/manager/login_need.jsp
   
    }
    
    return mav;
  }
  
  /**
   * 삭제 처리, http://localhost:9093/accommodation/delete.do?accommodationno=1
   * @param accommodationno 삭제할 레코드 번호
   * @return
   */
  
  @RequestMapping(value="/accommodation/delete.do", method = RequestMethod.POST)
  public ModelAndView delete_proc(int accommodationno) { // 자동으로 cateVO 객체가 생성되고 폼의 값이 할당됨
    ModelAndView mav = new ModelAndView();
    mav.setViewName("/accommodation/msg"); // /WEB-INF/views/accommodation/msg.jsp
    
    AccommodationVO accommodationVO = this.accommodationProc.read(accommodationno); // 삭제할 레코드 정보 읽기
    
    int cnt = this.accommodationProc.delete(accommodationno); // 삭제 처리
    System.out.println("-> cnt: " + cnt);
    
    if (cnt == 1) {
      mav.addObject("code", "delete_success"); // 키, 값
      mav.addObject("name", accommodationVO.getName()); // 카테고리 이름 jsp로 전송
    } else {
      mav.addObject("code", "delete_fail");
    }
    
    mav.addObject("cnt", cnt); // request.setAttribute("cnt", cnt);
//    mav.addObject("cnt", 0); // request.setAttribute("cnt", cnt);
    
    return mav;
  }
}
