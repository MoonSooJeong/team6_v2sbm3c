package dev.mvc.reply;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import dev.mvc.admin.AdminProcInter;
import dev.mvc.contents.ContentsVO;
import dev.mvc.member.MemberProcInter;
import dev.mvc.sect.SectVO;

@Controller
public class ReplyCont {
  @Autowired
  @Qualifier("dev.mvc.member.MemberProc") // @Component("dev.mvc.admin.AdminProc")
  private MemberProcInter memberProc;
  
  @Autowired
  @Qualifier("dev.mvc.reply.ReplyProc")
  private ReplyProcInter replyProc;
  
  private ReplyCont() {
    System.out.println("-> ReplyCont created.");
  }
  
  @ResponseBody
  @RequestMapping(value = "/reply/create.do", method = RequestMethod.POST)
  public ModelAndView create(HttpServletRequest request, HttpSession session, ReplyVO replyVO) {
    ModelAndView mav = new ModelAndView();

    String nowPage = request.getParameter("now_page");
    String sectno = request.getParameter("sectno");

    
    if (this.memberProc.isMember(session)) {
      int cnt = this.replyProc.create(replyVO);
      System.out.println("-> cnt: " + cnt);

      if (cnt == 1) {
        // 댓글 등록에 성공하면 해당 콘텐츠의 댓글 목록을 가져옴
        List<ReplyVO> replyList = this.replyProc.getReplyList(replyVO.getContentsno());
        mav.addObject("replyList", replyList);


        // Redirect to the contents/read.do page with necessary parameters
        mav.setViewName("redirect:/contents/read.do?contentsno=" + replyVO.getContentsno());
      } else {
        
      }
    } else {
      mav.addObject("result", "login_required");
    }

    return mav;
  }
  
  @ResponseBody
  @RequestMapping(value = "/reply/delete.do", method = RequestMethod.POST)
  public ModelAndView delete(@RequestParam int replyno, HttpServletRequest request) {
      ModelAndView mav = new ModelAndView();

      int cnt = this.replyProc.delete(replyno);

      if (cnt == 1) {
          // Assume the contentsno, now_page, and sectno are available as parameters
          String contentsno = request.getParameter("contentsno");
          String nowPage = request.getParameter("now_page");
          String sectno = request.getParameter("sectno");

          // Redirect to the contents/read.do page with necessary parameters
          mav.setViewName("redirect:/contents/read.do?contentsno=" + contentsno);

          // Add a flag to indicate successful deletion
          mav.addObject("deleteSuccess", true);
      } else {
          // Handle failure scenario, you might want to redirect to an error page or handle it differently
          mav.setViewName("redirect:/error-page");
          mav.addObject("deleteSuccess", false);
      }

      return mav;
  }
  
  @RequestMapping(value = "/reply/list.do", method = RequestMethod.GET)
  public ModelAndView list(@RequestParam int contentsno) {
      ModelAndView mav = new ModelAndView("jsonView");

      List<ReplyVO> replyList = this.replyProc.list_by_contentsno(contentsno);
      mav.addObject("replyList", replyList);

      return mav;
  }
  
}