/**********************************/
/* Table Name: 숙박시설 */
/**********************************/
DROP TABLE ACCOMMODATION;
CREATE TABLE ACCOMMODATION(
		ACCOMMODATIONNO               		NUMBER(10)		 NOT NULL		 PRIMARY KEY,
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

CREATE SEQUENCE ACCOMMODATION_SEQ
  START WITH 1              -- 시작 번호
  INCREMENT BY 1            -- 증가값
  MAXVALUE 99999            -- 최대값: 99999 --> NUMBER(5) 대응
  CACHE 2                   -- 2번은 메모리에서만 계산
  NOCYCLE;                  -- 다시 1부터 생성되는 것을 방지
COMMIT;