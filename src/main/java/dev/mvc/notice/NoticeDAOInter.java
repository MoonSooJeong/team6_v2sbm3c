package dev.mvc.notice;

import java.util.ArrayList;



public interface NoticeDAOInter {
  /**
   * 등록, 추상 메소드
   * @param noticeVO
   * @return
   */
  public int create(NoticeVO noticeVO);

  /**
   * 모든 공지사항의 등록된 글목록
   * @return
   */
  public ArrayList<NoticeVO> list_all();
  
  /**
   * 조회
   * @param noticeno
   * @return
   */
  public NoticeVO read(int noticeno);
  
  // 전체 레코드 수를 반환하는 메서드
  int search_count();
  
  /**
   * 글 정보 수정
   * @param noticeVO
   * @return 처리된 레코드 갯수
   */
  public int update_text(NoticeVO noticeVO);

  /**
   * 파일 정보 수정
   * @param noticeVO
   * @return 처리된 레코드 갯수
   */
  public int update_file(NoticeVO noticeVO);
 
  /**
   * 삭제
   * @param noticeno
   * @return 삭제된 레코드 갯수
   */
  public int delete(int noticeno);
  

}

