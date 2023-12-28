/**********************************/
/* Table Name: 방 */
/**********************************/
DROP TABLE ROOM;
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

COMMIT;

