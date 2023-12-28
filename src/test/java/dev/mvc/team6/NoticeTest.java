package dev.mvc.team6;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import dev.mvc.notice.NoticeProcInter;
import dev.mvc.notice.NoticeVO;

@SpringBootTest
public class NoticeTest {

  @Autowired
  private NoticeProcInter noticeProc;

  // 전체 공지사항 목록 테스트
  @Test
  public void testListAll() {
    assertNotNull(noticeProc, "noticeProc is not autowired.");
    assertTrue(noticeProc.list_all().size() >= 0, "List all method should return at least 0 items.");
  }

  // 특정 공지사항 조회 테스트
  @Test
  public void testRead() {
    assertNotNull(noticeProc, "noticeProc is not autowired.");
    int noticeno = 1; // 테스트할 공지사항 번호
    NoticeVO noticeVO = noticeProc.read(noticeno);
    assertNotNull(noticeVO, "Read method should return a NoticeVO object.");
  }


}
