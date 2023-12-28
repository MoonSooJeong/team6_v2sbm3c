/**********************************/
/* Table Name: 카테고리 */
/**********************************/
DROP TABLE SECT;

CREATE TABLE SECT(
		SECTNO                        		NUMBER(10)		 NOT NULL PRIMARY KEY,
		NAME                          		VARCHAR2(30)	 NOT NULL,
		CNT                           		NUMBER(7)		 DEFAULT 0 NOT NULL,
		RDATE                         		DATE		     NOT NULL,
        SEQNO                               NUMBER(5)        DEFAULT 1 NOT NULL,
        VISIBLE                             CHAR(1)          DEFAULT 'N' NOT NULL  
);

COMMENT ON TABLE SECT is '섹션';
COMMENT ON COLUMN SECT.SECTNO is '섹션 번호';
COMMENT ON COLUMN SECT.NAME is '섹션 이름';
COMMENT ON COLUMN SECT.CNT is '관련 자료수';
COMMENT ON COLUMN SECT.RDATE is '등록일';
COMMENT ON COLUMN SECT.SEQNO is '출력 순서';
COMMENT ON COLUMN SECT.VISIBLE is '출력 모드';

DROP SEQUENCE SECT_SEQ;

CREATE SEQUENCE SECT_SEQ
  START WITH 1         -- 시작 번호
  INCREMENT BY 1       -- 증가값
  MAXVALUE 9999999999  -- 최대값: 9999999999 --> NUMBER(10) 대응
  CACHE 2              -- 2번은 메모리에서만 계산
  NOCYCLE;             -- 다시 1부터 생성되는 것을 방지
  
-- CREATE
INSERT INTO sect(sectno, name, cnt, rdate) VALUES(sect_seq.nextval, '테스트1', 0, sysdate); 
INSERT INTO sect(sectno, name, cnt, rdate) VALUES(sect_seq.nextval, '테스트2', 0, sysdate); 
INSERT INTO sect(sectno, name, cnt, rdate) VALUES(sect_seq.nextval, '시부야', 0, sysdate);

-- READ: LIST
SELECT * FROM sect;
SELECT sectno, name, cnt, rdate, seqno, visible FROM sect ORDER BY sectno ASC;
    SECTNO NAME                                  CNT RDATE                    SEQNO V
---------- ------------------------------ ---------- ------------------- ---------- -
         1 이케부쿠로                              0 2023-09-20 02:03:27          1 N
         2 아키하바라                              0 2023-09-20 02:03:27          1 N
         3 시부야                                  0 2023-09-20 02:03:27          1 N

-- READ
SELECT sectno, name, cnt, rdate FROM sect WHERE sectno=1;
    SECTNO NAME                                  CNT RDATE              
---------- ------------------------------ ---------- -------------------
         1 이케부쿠로                              0 2023-09-20 02:03:27
         
-- UPDATE
UPDATE sect SET name='신주쿠', cnt=1 WHERE sectno=1;
    SECTNO NAME                                  CNT RDATE                    SEQNO V
---------- ------------------------------ ---------- ------------------- ---------- -
         1 신주쿠                                  1 2023-09-20 02:03:27          1 N
         2 아키하바라                              0 2023-09-20 02:03:27          1 N
         3 시부야                                  0 2023-09-20 02:03:27          1 N

-- DELETE
DELETE FROM sect WHERE sectno=1;
DELETE FROM sect WHERE sectno >= 10;

COMMIT;

-- COUNT
SELECT COUNT(*) as cnt FROM sect;


-- 우선 순위 높임, 10 등 -> 1 등
UPDATE sect SET seqno = seqno - 1 WHERE sectno=1;
SELECT sectno, name, cnt, rdate, seqno FROM sect ORDER BY sectno ASC;

-- 우선 순위 낮춤, 1 등 -> 10 등
UPDATE sect SET seqno = seqno + 1 WHERE sectno=1;
SELECT sectno, name, cnt, rdate, seqno FROM sect ORDER BY sectno ASC;

-- READ: LIST
SELECT sectno, name, cnt, rdate, seqno FROM sect ORDER BY seqno ASC;

COMMIT;

-- 카테고리 공개 설정
UPDATE sect SET visible='Y' WHERE sectno=1;
SELECT sectno, name, cnt, rdate, seqno, visible FROM sect ORDER BY sectno ASC;

-- 카테고리 비공개 설정
UPDATE sect SET visible='N' WHERE sectno=1;
SELECT sectno, name, cnt, rdate, seqno, visible FROM sect ORDER BY sectno ASC;

COMMIT;

-- 비회원/회원 SELECT LIST, id: list_all_y
SELECT sectno, name, cnt, rdate, seqno, visible 
FROM sect 
WHERE visible='Y'
ORDER BY seqno ASC;




         






