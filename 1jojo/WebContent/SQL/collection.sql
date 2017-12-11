create sequence ua_seq
start with 1
increment by 1
nocache;
create sequence QNA_BOARD_SEQ
start with 1
increment by 1
nocache;
create sequence FORUM_BOARD_SEQ
start with 1
increment by 1
nocache;

/* 사용자계정정보 */
CREATE TABLE useraccount (
	No NUMBER NOT NULL, /* 사용자번호 */
	ID VARCHAR2(20) NOT NULL, /* 아이디 */
	PASS VARCHAR2(20) NOT NULL /* 암호 */
);

CREATE UNIQUE INDEX PK_useraccount
	ON useraccount (
		No ASC
	);

ALTER TABLE useraccount
	ADD
		CONSTRAINT PK_useraccount
		PRIMARY KEY (
			No
		);

/* QnA게시판 */
CREATE TABLE qna_board (
	board_Num NUMBER NOT NULL, /* 게시물번호 */
	No NUMBER, /* 사용자번호 */
	board_name VARCHAR2(20), /* 이름 */
	board_pass VARCHAR2(20), /* 게시물암호 */
	board_subject VARCHAR2(40), /* 제목 */
	board_content VARCHAR2(1000), /* 본문 */
	board_file VARCHAR2(100), /* 첨부파일 */
	board_re_ref NUMBER, /* 글그룹 */
	board_re_lev INTEGER, /* 글깊이 */
	board_re_seq INTEGER, /* 글순서 */
	board_readcount INTEGER, /* 조회수 */
	board_date DATE /* 게시시간 */
);

CREATE UNIQUE INDEX PK_qna_board
	ON qna_board (
		board_Num ASC
	);

ALTER TABLE qna_board
	ADD
		CONSTRAINT PK_qna_board
		PRIMARY KEY (
			board_Num
		);

/* 업체검색결과 */
CREATE TABLE search_qual (
	search_com_No VARCHAR(12) NOT NULL, /* 번호 */
	No NUMBER, /* 사용자번호 */
	com_qual VARCHAR2(1000), /* 업체지원자격 */
	com_preex VARCHAR2(1000), /* 업체우대사항 */
	com_frequency INTEGER /* 빈도수 */
);

/* 해당업체 리스트 테이블 */
CREATE TABLE search_list (
	com_link VARCHAR2(500) NOT NULL, /* 지원자격포함 기업 링크 */
	search_com_No VARCHAR(12) NOT NULL, /* 번호 */
	com_qual VARCHAR2(1000), /* 업체지원자격 */
	com_preex VARCHAR2(1000), /* 업체우대사항 */
	com_name VARCHAR2(100) /* 지원자격포함 기업명 */
);

/* 포럼게시판 */
CREATE TABLE forum_board (
	board_Num NUMBER NOT NULL, /* 게시물번호 */
	No NUMBER, /* 사용자번호 */
	board_name VARCHAR2(20), /* 이름 */
	board_pass VARCHAR2(20), /* 게시물암호 */
	board_subject VARCHAR2(40), /* 제목 */
	board_content VARCHAR2(1000), /* 본문 */
	board_file VARCHAR2(100), /* 첨부파일 */
	board_re_ref NUMBER, /* 글그룹 */
	board_re_lev INTEGER, /* 글깊이 */
	board_re_seq INTEGER, /* 글순서 */
	board_readcount INTEGER, /* 조회수 */
	board_date DATE /* 게시시간 */
);

CREATE UNIQUE INDEX PK_forum_board
	ON forum_board (
		board_Num ASC
	);

ALTER TABLE forum_board
	ADD
		CONSTRAINT PK_forum_board
		PRIMARY KEY (
			board_Num
		);

ALTER TABLE qna_board
	ADD
		CONSTRAINT FK_useraccount_TO_qna_board
		FOREIGN KEY (
			No
		)
		REFERENCES useraccount (
			No
		);

ALTER TABLE forum_board
	ADD
		CONSTRAINT FK_useraccount_TO_forum_board
		FOREIGN KEY (
			No
		)
		REFERENCES useraccount (
			No
		);