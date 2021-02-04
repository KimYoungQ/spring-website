package com.springwebsite.mapper;

import com.springwebsite.board.Content;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;

@Mapper
public interface BoardMapper {

    @Select("select board_info_name " +
            "from board_info_table " +
            "where board_info_idx = #{board_info_idx}")
    String getBoardInfoName(int board_info_idx);

    @Select("select a2.name as content_writer_name, " +
            "       to_char(a1.content_date, 'YYYY-MM-DD') as content_date, " +
            "       a1.content_subject, a1.content_text, a1.content_file, a1.content_writer_idx " +
            "from content_table a1, member_table a2 " +
            "where a1.content_writer_idx = a2.member_idx " +
            "      and content_idx = #{content_idx}")
    Content getContentInfo(int content_idx);

//------------------------------------------  게시판 주제 별 게시글 등록 --------------------------------------------------//
    @SelectKey(statement = "select content_seq.nextval from dual", keyProperty = "content_idx", before = true, resultType = int.class)
    @Insert("insert into content_table(content_idx, content_subject, content_text, " +
            "       content_file, content_writer_idx, content_board_idx, content_date, content_generalBoard_idx) " +
            "values (#{content_idx}, #{content_subject}, #{content_text}, " +
            "       #{content_file, jdbcType=VARCHAR}, #{content_writer_idx}, #{content_board_idx}, sysdate, generalboard_seq.nextval)")
    void addGeneralContentInfo(Content content);

    @SelectKey(statement = "select content_seq.nextval from dual", keyProperty = "content_idx", before = true, resultType = int.class)
    @Insert("insert into content_table(content_idx, content_subject, content_text, " +
            "       content_file, content_writer_idx, content_board_idx, content_date, content_generalBoard_idx) " +
            "values (#{content_idx}, #{content_subject}, #{content_text}, " +
            "       #{content_file, jdbcType=VARCHAR}, #{content_writer_idx}, #{content_board_idx}, sysdate, jobboard_seq.nextval)")
    void addJobContentInfo(Content content);

    @SelectKey(statement = "select content_seq.nextval from dual", keyProperty = "content_idx", before = true, resultType = int.class)
    @Insert("insert into content_table(content_idx, content_subject, content_text, " +
            "       content_file, content_writer_idx, content_board_idx, content_date, content_generalBoard_idx) " +
            "values (#{content_idx}, #{content_subject}, #{content_text}, " +
            "       #{content_file, jdbcType=VARCHAR}, #{content_writer_idx}, #{content_board_idx}, sysdate, musicboard_seq.nextval)")
    void addMusicContentInfo(Content content);

    @SelectKey(statement = "select content_seq.nextval from dual", keyProperty = "content_idx", before = true, resultType = int.class)
    @Insert("insert into content_table(content_idx, content_subject, content_text, " +
            "       content_file, content_writer_idx, content_board_idx, content_date, content_generalBoard_idx) " +
            "values (#{content_idx}, #{content_subject}, #{content_text}, " +
            "       #{content_file, jdbcType=VARCHAR}, #{content_writer_idx}, #{content_board_idx}, sysdate, sportboard_seq.nextval)")
    void addsportContentInfo(Content content);
}
