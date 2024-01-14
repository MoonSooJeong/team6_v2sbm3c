package dev.mvc.reply;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("dev.mvc.reply.ReplyProc")
public class ReplyProc implements ReplyProcInter {
  @Autowired
  private ReplyDAOInter replyDAO;
  
  @Override
  public int create(ReplyVO replyVO) {
    int cnt = this.replyDAO.create(replyVO);
    return cnt;
  }
  
  @Override
  public ReplyVO read(int replyno) {
    ReplyVO replyVO = this.replyDAO.read(replyno);
    return replyVO;
  }
  
  @Override
  public List<ReplyVO> list(int contentsno) {
    List<ReplyVO> replyList = this.replyDAO.list(contentsno);
    return replyList;
  }
  
  @Override
  public List<ReplyVO> getReplyList(int contentsno) {
    List<ReplyVO> replyList = this.replyDAO.list(contentsno);
    return replyList;
  }
  
  @Override
  public List<ReplyVO> list_by_contentsno(int contentsno) {
    return replyDAO.list_by_contentsno(contentsno);
  }
  
  @Override
  public int delete(int replyno) {
    return replyDAO.delete(replyno);
  }
}
