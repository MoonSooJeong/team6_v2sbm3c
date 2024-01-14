package dev.mvc.accommodation;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component("dev.mvc.accommodation.AccommodationProc")
public class AccommodationProc implements AccommodationProcInter {
  @Autowired
  private AccommodationDAOInter accommodationDAO;
  
  @Override
  public int create(AccommodationVO accommodationVO) {
    int cnt = this.accommodationDAO.create(accommodationVO);

    return cnt;
  }

  @Override
  public ArrayList<AccommodationVO> list_all() {
    ArrayList<AccommodationVO> list = this.accommodationDAO.list_all();
    
    return list;
  }

  @Override
  public AccommodationVO read(int accommodationno) {
    AccommodationVO  AccommodationVO  = this.accommodationDAO.read(accommodationno);
    
    return AccommodationVO;
  }

  @Override
  public int update(AccommodationVO accommodationVO) {
    int cnt = this.accommodationDAO.update(accommodationVO);
    
    return cnt;
  }

  @Override
  public int delete(int accommodationno) {
    int cnt = this.accommodationDAO.delete(accommodationno);
    
    return cnt;
  }
  
}
