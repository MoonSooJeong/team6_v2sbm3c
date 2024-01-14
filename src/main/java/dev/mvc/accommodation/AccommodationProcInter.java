package dev.mvc.accommodation;

import java.util.ArrayList;

public interface AccommodationProcInter {
  
  // 숙박시설 생성
  public int create(AccommodationVO accommodationVO);
  
  // 모든 숙박시설 목록 조회
  public ArrayList<AccommodationVO> list_all();
  
  // 특정 숙박시설 읽기
  public AccommodationVO read(int accommodationNo);
  
  // 숙박시설 정보 수정
  public int update(AccommodationVO accommodationVO);
  
  // 숙박시설 삭제
  public int delete(int accommodationNo);
}
