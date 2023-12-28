package dev.mvc.sect;

import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

// Controller가 사용하는 이름
@Component("dev.mvc.sect.SectProc")
public class SectProc implements SectProcInter {
  @Autowired // CateDAOInter interface 구현한 객체를 만들어 자동으로 할당해라.
  private SectDAOInter sectDAO;
  
  @Override
  public int create(SectVO sectVO) {
    int cnt = this.sectDAO.create(sectVO);

    return cnt;
  }

  @Override
  public ArrayList<SectVO> list_all() {
    ArrayList<SectVO> list = this.sectDAO.list_all();
    
    return list;
  }

  @Override
  public SectVO read(int sectno) {
    SectVO  sectVO  = this.sectDAO.read(sectno);
    
    return sectVO;
  }

  @Override
  public int update(SectVO sectVO) {
    int cnt = this.sectDAO.update(sectVO);
    
    return cnt;
  }

  @Override
  public int delete(int sectno) {
    int cnt = this.sectDAO.delete(sectno);
    
    return cnt;
  }

  @Override
  public int update_seqno_forward(int sectno) {
    int cnt = this.sectDAO.update_seqno_forward(sectno);
    return cnt;
  }

  @Override
  public int update_seqno_backward(int sectno) {
    int cnt = this.sectDAO.update_seqno_backward(sectno);
    return cnt;
  }

  @Override
  public int update_visible_y(int sectno) {
    int cnt = this.sectDAO.update_visible_y(sectno);
    return cnt;
  }

  @Override
  public int update_visible_n(int sectno) {
    int cnt = this.sectDAO.update_visible_n(sectno);
    return cnt;
  }

  @Override
  public ArrayList<SectVO> list_all_y() {
    ArrayList<SectVO> list = this.sectDAO.list_all_y();
    return list;
  }
  }