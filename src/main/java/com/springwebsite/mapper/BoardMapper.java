package com.springwebsite.mapper;

import com.springwebsite.board.Content;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface BoardMapper {

    @Select("select board_info_name " +
            "from board_info_table " +
            "where board_info_idx = #{board_info_idx}")
    String getBoardInfoName(int board_info_idx);

    @Select("select a2.name as content_writer_name, " +
            "       to_char(a1.content_date, 'yyyy-mm-dd hh24:mi:ss') as content_date, " +
            "       a1.content_subject, a1.content_text, a1.content_file, a1.content_writer_idx " +
            "from content_table a1, member_table a2 " +
            "where a1.content_writer_idx = a2.member_idx " +
            "      and content_idx = #{content_idx}")
    Content getContentInfo(int content_idx);

    @Update("update content_table " +
            "set content_subject = #{content_subject}, content_text = #{content_text}, " +
            "content_file = #{content_file, jdbcType=VARCHAR} " +
            "where content_idx = #{content_idx}")
    void modifyContentInfo(Content content);

    @SelectKey(statement = "select content_seq.nextval from dual", keyProperty = "content_idx", before = true, resultType = int.class)
    @Insert("insert into content_table(content_idx, content_subject, content_text, " +
            "       content_file, content_writer_idx, content_board_idx, content_date) " +
            "values (#{content_idx}, #{content_subject}, #{content_text}, " +
            "       #{content_file, jdbcType=VARCHAR}, #{content_writer_idx}, #{content_board_idx}, to_date(sysdate,'yyyy-mm-dd hh24:mi:ss'))")
    void addContentInfo(Content content);

    @Delete("delete from content_table")
    void deleteAll();

    @Select("select content_idx from content_table where content_date = #{content_date}")
    int findContentIdxbyContentDate(String content_date);

    @Select("select a1.content_idx, a1.content_subject, a2.name as content_writer_name, " +
            "       to_char(a1.content_date, 'YYYY-MM-DD hh24:mi:ss') as content_date " +
            "from content_table a1, member_table a2 " +
            "where a1.content_writer_idx = a2.member_idx " +
            "       and a1.content_board_idx = #{board_info_idx} " +
            "order by a1.content_idx desc")
    List<Content> getContentList(int board_info_idx);

    @Delete("delete from content_table where content_idx = #{content_idx}")
    void deleteContentInfo(int content_idx);
}
