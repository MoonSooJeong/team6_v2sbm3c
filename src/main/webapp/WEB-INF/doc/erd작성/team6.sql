DROP TABLE SEO CASCADE CONSTRAINTS;
DROP TABLE MOON CASCADE CONSTRAINTS;
DROP TABLE CHATBOT CASCADE CONSTRAINTS;
DROP TABLE RESERVATION CASCADE CONSTRAINTS;
DROP TABLE ROOM CASCADE CONSTRAINTS;
DROP TABLE ACCOMMODATION CASCADE CONSTRAINTS;
DROP TABLE CONTENTS CASCADE CONSTRAINTS;
DROP TABLE NOTICE CASCADE CONSTRAINTS;
DROP TABLE COMMENT CASCADE CONSTRAINTS;
DROP TABLE INFORMATION CASCADE CONSTRAINTS;
DROP TABLE ADMIN CASCADE CONSTRAINTS;
DROP TABLE SECTION CASCADE CONSTRAINTS;
DROP TABLE MEMBER CASCADE CONSTRAINTS;

/**********************************/
/* Table Name: 회원 */
/**********************************/
CREATE TABLE MEMBER(
		MEMBERNO                      		NUMBER(10)		 NOT NULL		 PRIMARY KEY,
		ID                            		VARCHAR2(30)		 NOT NULL,
		PASSWD                        		VARCHAR2(60)		 NOT NULL,
		MNAME                         		VARCHAR2(30)		 NOT NULL,
		TEL                           		VARCHAR2(14)		 NOT NULL,
		ZIPCODE                       		VARCHAR2(5)		 NULL ,
		ADDRESS1                      		VARCHAR2(80)		 NULL ,
		ADDRESS2                      		VARCHAR2(50)		 NULL ,
		MDATE                         		DATE		 NOT NULL,
		GRADE                         		NUMBER(2)		 NOT NULL,
  CONSTRAINT SYS_C007505 UNIQUE (ID)
);

COMMENT ON TABLE MEMBER is '회원';
COMMENT ON COLUMN MEMBER.MEMBERNO is '회원 번호';
COMMENT ON COLUMN MEMBER.ID is '아이디';
COMMENT ON COLUMN MEMBER.PASSWD is '패스워드';
COMMENT ON COLUMN MEMBER.MNAME is '성명';
COMMENT ON COLUMN MEMBER.TEL is '전화번호';
COMMENT ON COLUMN MEMBER.ZIPCODE is '우편번호';
COMMENT ON COLUMN MEMBER.ADDRESS1 is '주소1';
COMMENT ON COLUMN MEMBER.ADDRESS2 is '주소2';
COMMENT ON COLUMN MEMBER.MDATE is '가입일';
COMMENT ON COLUMN MEMBER.GRADE is '등급';


/**********************************/
/* Table Name: 섹션 */
/**********************************/
CREATE TABLE SECTION(
		SECTIONNO                     		NUMBER(10)		 NOT NULL		 PRIMARY KEY,
		NAME                          		VARCHAR2(30)		 NOT NULL,
		RDATE                         		DATE		 NOT NULL,
		SEQNO                         		NUMBER(5)		 NOT NULL,
		VISIBLE                       		CHAR(1)		 NOT NULL
);

COMMENT ON TABLE SECTION is '섹션';
COMMENT ON COLUMN SECTION.SECTIONNO is '섹션 번호';
COMMENT ON COLUMN SECTION.NAME is '섹션 이름';
COMMENT ON COLUMN SECTION.RDATE is '등록일';
COMMENT ON COLUMN SECTION.SEQNO is '출력 순서';
COMMENT ON COLUMN SECTION.VISIBLE is '출력 모드';

DROP SEQUENCE SECTION_SEQ;

CREATE SEQUENCE SECTION_SEQ
  START WITH 1         -- 시작 번호
  INCREMENT BY 1       -- 증가값
  MAXVALUE 9999999999  -- 최대값: 9999999999 --> NUMBER(10) 대응
  CACHE 2              -- 2번은 메모리에서만 계산
  NOCYCLE;             -- 다시 1부터 생성되는 것을 방지

/**********************************/
/* Table Name: 관리자 */
/**********************************/
CREATE TABLE ADMIN(
		ADMINNO                       		NUMBER(5)		 NOT NULL		 PRIMARY KEY,
		ID                            		VARCHAR2(20)		 NOT NULL,
		PASSWD                        		VARCHAR2(15)		 NOT NULL,
		MNAME                         		VARCHAR2(20)		 NOT NULL,
		MDATE                         		DATE		 NOT NULL,
		GRADE                         		NUMBER(2)		 NOT NULL,
		NOTICENO                      		NUMBER(10)		 NULL ,
  CONSTRAINT SYS_C007034 UNIQUE (ID)
);

COMMENT ON TABLE ADMIN is '관리자';
COMMENT ON COLUMN ADMIN.ADMINNO is '관리자 번호';
COMMENT ON COLUMN ADMIN.ID is '아이디';
COMMENT ON COLUMN ADMIN.PASSWD is '패스워드';
COMMENT ON COLUMN ADMIN.MNAME is '성명';
COMMENT ON COLUMN ADMIN.MDATE is '가입일';
COMMENT ON COLUMN ADMIN.GRADE is '등급';
COMMENT ON COLUMN ADMIN.NOTICENO is '공지 번호';

CREATE SEQUENCE ADMIN_SEQ
  START WITH 1              -- 시작 번호
  INCREMENT BY 1            -- 증가값
  MAXVALUE 99999            -- 최대값: 99999 --> NUMBER(5) 대응
  CACHE 2                   -- 2번은 메모리에서만 계산
  NOCYCLE;                  -- 다시 1부터 생성되는 것을 방지
  
/**********************************/
/* Table Name: 추천 정보 */
/**********************************/
CREATE TABLE INFORMATION(
		INFORMATIONNO                 		NUMBER(10)		 NOT NULL		 PRIMARY KEY,
		ADMINNO                       		NUMBER(10)		 NOT NULL,
		SECTIONNO                     		NUMBER(10)		 NOT NULL,
		TITLE                         		VARCHAR2(100)		 NOT NULL,
		CONTENT                       		CLOB(4000)		 NOT NULL,
		RECOM                         		NUMBER(7)		 NOT NULL,
		CNT                           		NUMBER(7)		 NOT NULL,
		REPLYCNT                      		NUMBER(7)		 NOT NULL,
		WORD                          		VARCHAR2(200)		 NULL ,
		RDATE                         		DATE		 NOT NULL,
		FILE1                         		VARCHAR2(50)		 NULL ,
		FILE1SAVED                    		VARCHAR2(50)		 NULL ,
		THUMB1                        		VARCHAR2(50)		 NULL ,
		SIZE1                         		NUMBER(10)		 NULL ,
		MAP                           		VARCHAR2(1000)		 NULL ,
		YOUTUBE                       		VARCHAR2(1000)		 NULL ,
  FOREIGN KEY (SECTIONNO) REFERENCES SECTION (SECTIONNO),
  FOREIGN KEY (ADMINNO) REFERENCES ADMIN (ADMINNO)
);

COMMENT ON TABLE INFORMATION is '추천 정보';
COMMENT ON COLUMN INFORMATION.INFORMATIONNO is '게시글 번호';
COMMENT ON COLUMN INFORMATION.ADMINNO is '관리자 번호';
COMMENT ON COLUMN INFORMATION.SECTIONNO is '섹션 번호';
COMMENT ON COLUMN INFORMATION.TITLE is '제목';
COMMENT ON COLUMN INFORMATION.CONTENT is '내용';
COMMENT ON COLUMN INFORMATION.RECOM is '추천수';
COMMENT ON COLUMN INFORMATION.CNT is '조회수';
COMMENT ON COLUMN INFORMATION.REPLYCNT is '댓글수';
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
  
/**********************************/
/* Table Name: 댓글 */
/**********************************/
CREATE TABLE COMMENT(
		COMMENTNO                     		NUMBER(10)		 NOT NULL		 PRIMARY KEY,
		INFORMATIONNO                 		NUMBER(10)		 NULL ,
		MEMBERNO                      		NUMBER(10)		 NULL ,
  FOREIGN KEY (MEMBERNO) REFERENCES MEMBER (MEMBERNO),
  FOREIGN KEY (INFORMATIONNO) REFERENCES INFORMATION (INFORMATIONNO)
);

COMMENT ON TABLE COMMENT is '댓글';
COMMENT ON COLUMN COMMENT.COMMENTNO is '댓글 번호';
COMMENT ON COLUMN COMMENT.INFORMATIONNO is '게시글 번호';
COMMENT ON COLUMN COMMENT.MEMBERNO is '회원 번호';


/**********************************/
/* Table Name: 공지사항 */
/**********************************/
CREATE TABLE NOTICE(
		NOTICENO                      		NUMBER(10)		 NOT NULL		 PRIMARY KEY,
		ADMINNO                       		NUMBER(10)		 NOT NULL,
		TITLE                         		VARCHAR2(100)		 NOT NULL,
		CONTENT                       		CLOB(4000)		 NOT NULL,
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
COMMENT ON COLUMN NOTICE.TITLE is '제목';
COMMENT ON COLUMN NOTICE.CONTENT is '내용';
COMMENT ON COLUMN NOTICE.WORD is '검색어';
COMMENT ON COLUMN NOTICE.RDATE is '등록일';
COMMENT ON COLUMN NOTICE.FILE1 is '메인 이미지';
COMMENT ON COLUMN NOTICE.FILE1SAVED is '실제 저장된 메인 이미지';
COMMENT ON COLUMN NOTICE.THUMB1 is '메인 이미지 Preview';


/**********************************/
/* Table Name: 후기 글 */
/**********************************/
CREATE TABLE CONTENTS(
		CONTENTSNO                    		NUMBER(10)		 NOT NULL		 PRIMARY KEY,
		MEMBERNO                      		NUMBER(10)		 NOT NULL,
		SECTIONNO                     		NUMBER(10)		 NOT NULL,
		TITLE                         		VARCHAR2(100)		 NOT NULL,
		CONTENT                       		CLOB(4000)		 NOT NULL,
		RECOM                         		NUMBER(7)		 NOT NULL,
		CNT                           		NUMBER(7)		 NOT NULL,
		WORD                          		VARCHAR2(200)		 NULL ,
		RDATE                         		DATE		 NOT NULL,
		FILE1                         		VARCHAR2(50)		 NULL ,
		FILE1SAVED                    		VARCHAR2(50)		 NULL ,
		THUMB1                        		VARCHAR2(50)		 NULL ,
		SIZE1                         		NUMBER(10)		 NULL ,
		MAP                           		VARCHAR2(1000)		 NULL ,
		YOUTUBE                       		VARCHAR2(1000)		 NULL ,
  FOREIGN KEY (MEMBERNO) REFERENCES MEMBER (MEMBERNO),
  FOREIGN KEY (SECTIONNO) REFERENCES SECTION (SECTIONNO)
);

COMMENT ON TABLE CONTENTS is '후기 글';
COMMENT ON COLUMN CONTENTS.CONTENTSNO is '후기 글 번호';
COMMENT ON COLUMN CONTENTS.MEMBERNO is '회원 번호';
COMMENT ON COLUMN CONTENTS.SECTIONNO is '섹션 번호';
COMMENT ON COLUMN CONTENTS.TITLE is '제목';
COMMENT ON COLUMN CONTENTS.CONTENT is '내용';
COMMENT ON COLUMN CONTENTS.RECOM is '추천수';
COMMENT ON COLUMN CONTENTS.CNT is '조회수';
COMMENT ON COLUMN CONTENTS.WORD is '검색어';
COMMENT ON COLUMN CONTENTS.RDATE is '등록일';
COMMENT ON COLUMN CONTENTS.FILE1 is '메인 이미지';
COMMENT ON COLUMN CONTENTS.FILE1SAVED is '실제 저장된 메인 이미지';
COMMENT ON COLUMN CONTENTS.THUMB1 is '메인 이미지 Preview';
COMMENT ON COLUMN CONTENTS.SIZE1 is '메인 이미지 크기';
COMMENT ON COLUMN CONTENTS.MAP is '지도';
COMMENT ON COLUMN CONTENTS.YOUTUBE is 'Youtube 영상';


/**********************************/
/* Table Name: 숙박시설 */
/**********************************/
CREATE TABLE ACCOMMODATION(
		ACCOMMODATIONNO               		INTEGER(10)		 NOT NULL		 PRIMARY KEY,
		NAME                          		VARCHAR2(100)		 NOT NULL,
		A_CONTACT                     		VARCHAR2(100)		 NOT NULL,
		ADDRESS                       		VARCHAR2(200)		 NOT NULL,
		DESCRIPTION                   		VARCHAR2(400)		 NULL ,
		RATING                        		NUMBER(2,1)		 NULL 
);

COMMENT ON TABLE ACCOMMODATION is '숙박시설';
COMMENT ON COLUMN ACCOMMODATION.ACCOMMODATIONNO is '숙박시설 번호';
COMMENT ON COLUMN ACCOMMODATION.NAME is '이름';
COMMENT ON COLUMN ACCOMMODATION.A_CONTACT is '연락처';
COMMENT ON COLUMN ACCOMMODATION.ADDRESS is '주소';
COMMENT ON COLUMN ACCOMMODATION.DESCRIPTION is '설명';
COMMENT ON COLUMN ACCOMMODATION.RATING is '평점';


/**********************************/
/* Table Name: 방 */
/**********************************/
CREATE TABLE ROOM(
		ACCOMMODATIONNO               		NUMBER(10)		 NOT NULL,
		ROOMNO                        		NUMBER(5)		 NOT NULL		 PRIMARY KEY,
		TYPE                          		VARCHAR2(20)		 NOT NULL,
		PRICE                         		NUMBER(10)		 NOT NULL,
  FOREIGN KEY (ACCOMMODATIONNO) REFERENCES ACCOMMODATION (ACCOMMODATIONNO)
);

COMMENT ON TABLE ROOM is '방';
COMMENT ON COLUMN ROOM.ACCOMMODATIONNO is '숙박시설 번호';
COMMENT ON COLUMN ROOM.ROOMNO is '방 번호';
COMMENT ON COLUMN ROOM.TYPE is '타입';
COMMENT ON COLUMN ROOM.PRICE is '가격';


/**********************************/
/* Table Name: 숙소 예약 */
/**********************************/
CREATE TABLE RESERVATION(
		ROOMNO                        		NUMBER(5)		 NOT NULL		 PRIMARY KEY,
		MEMBERNO                      		NUMBER(10)		 NOT NULL,
		MNAME                         		VARCHAR2(30)		 NOT NULL,
		CHECKIN_DATE                  		DATE		 NOT NULL,
		CHECKOUT_DATE                 		DATE		 NULL ,
  FOREIGN KEY (MEMBERNO) REFERENCES MEMBER (MEMBERNO),
  FOREIGN KEY (ROOMNO) REFERENCES ROOM (ROOMNO)
);

COMMENT ON TABLE RESERVATION is '숙소 예약';
COMMENT ON COLUMN RESERVATION.ROOMNO is '방 번호';
COMMENT ON COLUMN RESERVATION.MEMBERNO is '회원번호';
COMMENT ON COLUMN RESERVATION.MNAME is '성명';
COMMENT ON COLUMN RESERVATION.CHECKIN_DATE is '체크인 날짜';
COMMENT ON COLUMN RESERVATION.CHECKOUT_DATE is '체크아웃 날짜';


/**********************************/
/* Table Name: 챗봇[추후 추가예정] */
/**********************************/
CREATE TABLE CHATBOT(

);

COMMENT ON TABLE CHATBOT is '챗봇[추후 추가예정]';


/**********************************/
/* Table Name: 문수정 */
/**********************************/
CREATE TABLE MOON(

);

COMMENT ON TABLE MOON is '문수정';


/**********************************/
/* Table Name: 서영채 */
/**********************************/
CREATE TABLE SEO(

);

COMMENT ON TABLE SEO is '서영채';


