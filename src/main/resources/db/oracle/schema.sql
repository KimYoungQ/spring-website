create table board_info_table(
	board_info_idx number constraint BOARD_INFO_PK primary key,
	board_info_name varchar2(500) not null
);

create table member_table(
	member_idx number constraint MEMBER_PK primary key,
	name varchar2(50) not null,
	id varchar2(100) not null,
	password varchar2(100) not null,
	role varchar2(50) not null
);

create table content_table(
	content_idx number constraint CONTENT_PK primary key,
	content_subject varchar2(500) not null,
	content_text long not null,
	content_file varchar2(500),
	content_writer_idx number not null
	                   constraint CONTENT_FK1 references member_table(member_idx),
	content_board_idx number not null
	                   constraint CONTENT_FK2 references board_info_table(board_info_idx),
	content_date date not null
);