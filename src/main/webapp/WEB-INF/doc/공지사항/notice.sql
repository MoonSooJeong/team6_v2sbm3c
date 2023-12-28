DROP TABLE NOTICE;

CREATE TABLE NOTICE(
		NOTICENO                      		NUMBER(10)		 NOT NULL		 PRIMARY KEY,
        ADMINNO                              NUMBER(10)     NOT NULL , -- FK
--        CATENO                                NUMBER(10)         NOT NULL , -- FK
		TITLE                         		VARCHAR2(100)		 NOT NULL,
		CONTENT                       		VARCHAR2(4000)		 NOT NULL,
		WORD                          		VARCHAR2(200)		 NULL ,
		RDATE                         		DATE		 NOT NULL,
		FILE1                         		VARCHAR2(50)		 NULL ,
		FILE1SAVED                    		VARCHAR2(50)		 NULL ,
		THUMB1                        		VARCHAR2(50)		 NULL ,
  FOREIGN KEY (ADMINNO) REFERENCES ADMIN (ADMINNO)
);

COMMENT ON TABLE NOTICE is '공지사항';
COMMENT ON COLUMN NOTICE.NOTICENO is '공지 번호';
COMMENT ON COLUMN NOTICE.ADMINNO is '관리자 번호';
--COMMENT ON COLUMN NOTICE.SECT is '카테고리 번호';
COMMENT ON COLUMN NOTICE.TITLE is '제목';
COMMENT ON COLUMN NOTICE.CONTENT is '내용';
COMMENT ON COLUMN NOTICE.WORD is '검색어';
COMMENT ON COLUMN NOTICE.RDATE is '등록일';
COMMENT ON COLUMN NOTICE.FILE1 is '메인 이미지';
COMMENT ON COLUMN NOTICE.FILE1SAVED is '실제 저장된 메인 이미지';
COMMENT ON COLUMN NOTICE.THUMB1 is '메인 이미지 Preview';


DROP SEQUENCE NOTICE_SEQ;
CREATE SEQUENCE NOTICE_SEQ
  START WITH 1         -- 시작 번호
  INCREMENT BY 1       -- 증가값
  MAXVALUE 9999999999  -- 최대값: 9999999999 --> NUMBER(10) 대응
  CACHE 2              -- 2번은 메모리에서만 계산
  NOCYCLE;             -- 다시 1부터 생성되는 것을 방지
  
  
COMMIT;

-- 등록 화면 유형 1: 커뮤니티(공지사항, 게시판, 자료실, 갤러리,  Q/A...)글 등록
INSERT INTO notice (noticeno, adminno, title, content, word, file1, file1saved, thumb1, rdate)
VALUES (notice_seq.nextval, 1, '공지사항 테스트', '공지사항 내용 테스트', '테스트', '', '', '', sysdate);
