package dev.mvc.sect;

import java.util.ArrayList;

public interface SectDAOInter {
  /**
   * 등록, 추상 메소드 -> Spring Boot이 구현함.
   * @param sectVO 객체
   * @return 등록된 레코드 갯수
   */
  public int create(SectVO sectVO);
  
  /**
   * 전체 목록
   * @return
   */
  public ArrayList<SectVO> list_all();
  
  /**
   * 조회
   * @param sectno
   * @return
   */
  public SectVO read(int sectno);
  
  /**
   * 수정   
   * @param sectVO
   * @return 수정된 레코드 갯수
   */
  public int update(SectVO sectVO);

  /**
   * 삭제
   * @param sectno 삭제할 레코드 PK 번호
   * @return 삭제된 레코드 갯수
   */
  public int delete(int sectno);
  
  /**
   * 우선 순위 높임, 10 등 -> 1 등   
   * @param sectno
   * @return 수정된 레코드 갯수
   */
  public int update_seqno_forward(int sectno);

  /**
   * 우선 순위 낮춤, 1 등 -> 10 등   
   * @param sectno
   * @return 수정된 레코드 갯수
   */
  public int update_seqno_backward(int sectno);
  
  /**
   * 카테고리 공개 설정
   * @param sectno
   * @return
   */
  public int update_visible_y(int sectno);
  
  /**
   * 카테고리 비공개 설정
   * @param sectno
   * @return
   */
  public int update_visible_n(int sectno);
  
  /**
   * 비회원/회원 SELECT LIST
   * @return
   */
  public ArrayList<SectVO> list_all_y();
  
}
