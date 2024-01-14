/**********************************/
/* Table Name: 추천 정보 */
/**********************************/
DROP TABLE INFORMATION CASCADE CONSTRAINTS; 

CREATE TABLE INFORMATION(
      INFORMATIONNO                       NUMBER(10)       NOT NULL       PRIMARY KEY,
      ADMINNO                             NUMBER(10)       NOT NULL,
      SECTNO                           NUMBER(10)       NOT NULL,
      TITLE                               VARCHAR2(100)       NOT NULL,
      CONTENT                             VARCHAR2(4000)       NOT NULL,
      RECOM                               NUMBER(7)         DEFAULT 0         NOT NULL,
        CNT                                   NUMBER(7)         DEFAULT 0         NOT NULL,
        REPLYCNT                              NUMBER(7)         DEFAULT 0         NOT NULL,
      PASSWD                             VARCHAR2(15)         NOT NULL,
      WORD                                VARCHAR2(200)       NULL ,
      RDATE                               DATE       NOT NULL,
      FILE1                               VARCHAR2(50)       NULL ,
      FILE1SAVED                          VARCHAR2(50)       NULL ,
      THUMB1                              VARCHAR2(50)       NULL ,
      SIZE1                               NUMBER(10)       NULL ,
      MAP                                 VARCHAR2(1000)       NULL ,
      YOUTUBE                             VARCHAR2(1000)       NULL ,
  FOREIGN KEY (SECTNO) REFERENCES SECT (SECTNO),
  FOREIGN KEY (ADMINNO) REFERENCES ADMIN (ADMINNO)
);

COMMENT ON TABLE INFORMATION is '추천 정보';
COMMENT ON COLUMN INFORMATION.INFORMATIONNO is '게시글 번호';
COMMENT ON COLUMN INFORMATION.ADMINNO is '관리자 번호';
COMMENT ON COLUMN INFORMATION.SECTNO is '섹션 번호';
COMMENT ON COLUMN INFORMATION.TITLE is '제목';
COMMENT ON COLUMN INFORMATION.CONTENT is '내용';
COMMENT ON COLUMN INFORMATION.RECOM is '추천수';
COMMENT ON COLUMN INFORMATION.CNT is '조회수';
COMMENT ON COLUMN INFORMATION.REPLYCNT is '댓글수';
COMMENT ON COLUMN INFORMATION.PASSWD is '패스워드';
COMMENT ON COLUMN INFORMATION.WORD is '검색어';
COMMENT ON COLUMN INFORMATION.RDATE is '등록일';
COMMENT ON COLUMN INFORMATION.FILE1 is '메인 이미지';
COMMENT ON COLUMN INFORMATION.FILE1SAVED is '실제 저장된 메인 이미지';
COMMENT ON COLUMN INFORMATION.THUMB1 is '메인 이미지 Preview';
COMMENT ON COLUMN INFORMATION.SIZE1 is '메인 이미지 크기';
COMMENT ON COLUMN INFORMATION.MAP is '지도';
COMMENT ON COLUMN INFORMATION.YOUTUBE is 'Youtube 영상';


DROP SEQUENCE INFORMATION_SEQ;

CREATE SEQUENCE INFORMATION_SEQ
  START WITH 1                -- 시작 번호
  INCREMENT BY 1            -- 증가값
  MAXVALUE 9999999999  -- 최대값: 9999999999 --> NUMBER(10) 대응
  CACHE 2                        -- 2번은 메모리에서만 계산
  NOCYCLE;    
  
COMMIT;