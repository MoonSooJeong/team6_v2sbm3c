package dev.mvc.information;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import dev.mvc.sect.SectVO;

/**
 * Spring Boot가 자동 구현
 * @author soldesk
 *
 */
public interface InformationDAOInter {
  /**
   * 등록, 추상 메소드
   * @param informationVO
   * @return
   */
  public int create(InformationVO informationVO);

  /**
   * 모든 카테고리의 등록된 글목록
   * @return
   */
  public ArrayList<InformationVO> list_all();
  
  /**
   * 카테고리별 등록된 글 목록
   * @param sectno
   * @return
   */
  public ArrayList<InformationVO> list_by_sectno(int sectno);
  
  /**
   * 조회
   * @param informationno
   * @return
   */
  public InformationVO read(int informationno);
  
  /**
   * map 등록, 수정, 삭제
   * @param map
   * @return 수정된 레코드 갯수
   */
  public int map(HashMap<String, Object> map);

  /**
   * youtube 등록, 수정, 삭제
   * @param youtube
   * @return 수정된 레코드 갯수
   */
  public int youtube(HashMap<String, Object> map);
  
  /**
   * 카테고리별 검색 목록
   * @param map
   * @return
   */
  public ArrayList<InformationVO> list_by_sectno_search(HashMap<String, Object> hashMap);
  
  /**
   * 카테고리별 검색된 레코드 갯수
   * @param map
   * @return
   */
  public int search_count(HashMap<String, Object> hashMap);
  
  /**
   * 카테고리별 검색 목록 + 페이징
   * @param informationVO
   * @return
   */
  public ArrayList<InformationVO> list_by_sectno_search_paging(InformationVO informationVO);
  
  /**
   * 패스워드 검사
   * @param hashMap
   * @return
   */
  public int password_check(HashMap<String, Object> hashMap);
  
  /**
   * 글 정보 수정
   * @param informationVO
   * @return 처리된 레코드 갯수
   */
  public int update_text(InformationVO informationVO);

  /**
   * 파일 정보 수정
   * @param informationVO
   * @return 처리된 레코드 갯수
   */
  public int update_file(InformationVO informationVO);
 
  /**
   * 삭제
   * @param informationno
   * @return 삭제된 레코드 갯수
   */
  public int delete(int informationno);
  
  /**
   * FK sectno 값이 같은 레코드 갯수 산출
   * @param sectno
   * @return
   */
  public int count_by_sectno(int sectno);
 
  /**
   * 특정 카테고리에 속한 모든 레코드 삭제
   * @param sectno
   * @return 삭제된 레코드 갯수
   */
  public int delete_by_sectno(int sectno);
  
}
