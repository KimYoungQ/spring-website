create sequence member_seq
    start with 0
    increment by 1
    minvalue 0;

create sequence content_seq
    start with 0
    increment by 1
    minvalue 0;

insert into board_info_table(board_info_idx, board_info_name) values (1, '자유게시판');
insert into board_info_table(board_info_idx, board_info_name) values (2, '구직게시판');
insert into board_info_table(board_info_idx, board_info_name) values (3, '음악게시판');
insert into board_info_table(board_info_idx, board_info_name) values (4, '스포츠게시판');