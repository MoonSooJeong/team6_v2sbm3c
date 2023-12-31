/**********************************/
/* Table Name: 후기 글 */
/**********************************/
DROP TABLE contents CASCADE CONSTRAINTS; -- 자식 무시하고 삭제 가능
CREATE TABLE contents(
        contentsno                            NUMBER(10)         NOT NULL         PRIMARY KEY,
        memberno                              NUMBER(10)     NOT NULL , -- FK
        sectno                                NUMBER(10)         NOT NULL , -- FK
        title                                 VARCHAR2(100)         NOT NULL,
        content                               CLOB                  NOT NULL,
        recom                                 NUMBER(7)         DEFAULT 0         NOT NULL,
        cnt                                   NUMBER(7)         DEFAULT 0         NOT NULL,
        replycnt                              NUMBER(7)         DEFAULT 0         NOT NULL,
        passwd                                VARCHAR2(15)         NOT NULL,
        word                                  VARCHAR2(100)         NULL ,
        rdate                                 DATE               NOT NULL,
        file1                                   VARCHAR(50)          NULL,  -- 원본 파일명 image
        file1saved                            VARCHAR(50)          NULL,  -- 저장된 파일명, image
        thumb1                              VARCHAR(50)          NULL,   -- preview image
        size1                                 NUMBER(10)      DEFAULT 0 NULL,  -- 파일 사이즈
        map                                   VARCHAR2(1000)            NULL,
        youtube                               VARCHAR2(1000)            NULL,
        FOREIGN KEY (memberno) REFERENCES member (memberno),
        FOREIGN KEY (sectno) REFERENCES sect (sectno)
);

COMMENT ON TABLE contents is '컨텐츠';
COMMENT ON COLUMN contents.contentsno is '컨텐츠 번호';
COMMENT ON COLUMN contents.memberno is '회원 번호';
COMMENT ON COLUMN contents.sectno is '섹션 번호';
COMMENT ON COLUMN contents.title is '제목';
COMMENT ON COLUMN contents.content is '내용';
COMMENT ON COLUMN contents.recom is '추천수';
COMMENT ON COLUMN contents.cnt is '조회수';
COMMENT ON COLUMN contents.replycnt is '댓글수';
COMMENT ON COLUMN contents.passwd is '패스워드';
COMMENT ON COLUMN contents.word is '검색어';
COMMENT ON COLUMN contents.rdate is '등록일';
COMMENT ON COLUMN contents.file1 is '메인 이미지';
COMMENT ON COLUMN contents.file1saved is '실제 저장된 파일명';
COMMENT ON COLUMN contents.thumb1 is '메인 이미지 Preview';
COMMENT ON COLUMN contents.size1 is '메인 이미지 크기';
COMMENT ON COLUMN contents.map is '지도';
COMMENT ON COLUMN contents.youtube is 'Youtube 영상';

DROP SEQUENCE contents_seq;

CREATE SEQUENCE contents_seq
  START WITH 1                -- 시작 번호
  INCREMENT BY 1            -- 증가값
  MAXVALUE 9999999999  -- 최대값: 9999999999 --> NUMBER(10) 대응
  CACHE 2                        -- 2번은 메모리에서만 계산
  NOCYCLE;                      -- 다시 1부터 생성되는 것을 방지

-- 등록 화면 유형 1: 커뮤니티(공지사항, 게시판, 자료실, 갤러리,  Q/A...)글 등록
INSERT INTO contents(contentsno, memberno, sectno, title, content, recom, cnt, replycnt, passwd, 
                     word, rdate, file1, file1saved, thumb1, size1)
VALUES(contents_seq.nextval, 2, 1, '여행 후기', '여행', 0, 0, 0, '123',
       '자연', sysdate, '', '', '', 1000);
  
  
COMMIT;