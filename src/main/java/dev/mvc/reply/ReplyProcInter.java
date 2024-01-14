package dev.mvc.reply;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;



public interface ReplyProcInter {
  
  public int create(ReplyVO replyVO);
  
  
  public ReplyVO read(int replyno);
  
  public List<ReplyVO> list(int contentsno);
  
  public List<ReplyVO> getReplyList(int contentsno);
  
  public List<ReplyVO> list_by_contentsno(int contentsno);
  
  public int delete(int replyno);
}
