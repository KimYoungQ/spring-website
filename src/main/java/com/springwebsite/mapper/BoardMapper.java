package com.springwebsite.mapper;

import com.springwebsite.board.Content;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

@Mapper
public interface BoardMapper {

    @Select("select board_info_name " +
            "from board_info_table " +
            "where board_info_idx = #{board_info_idx}")
    String getBoardInfoName(int board_info_idx);

    @Select("select a2.name as content_writer_name, " +
            "       DATE_FORMAT(a1.content_date, '%Y-%m-%d %H:%i:%s %p') as content_date, " +
            "       a1.content_subject, a1.content_text, a1.content_writer_idx " +
            "from content_table a1, member_table a2 " +
            "where a1.content_writer_idx = a2.member_idx " +
            "      and content_idx = #{content_idx}")
    Content getContentInfo(int content_idx);

    @Update("update content_table " +
            "set content_subject = #{content_subject}, content_text = #{content_text} " +
            "where content_idx = #{content_idx}")
    void modifyContentInfo(Content content);

    @SelectKey(statement = "select NEXTVAL(content_seq) from dual", keyProperty = "content_idx", before = true, resultType = int.class)
    @Insert("insert into content_table(content_idx, content_subject, content_text, " +
            "       content_writer_idx, content_board_idx, content_date) " +
            "values (#{content_idx}, #{content_subject}, #{content_text}, " +
            "       #{content_writer_idx}, #{content_board_idx}, NOW())")
    void addContentInfo(Content content);

    @Delete("delete from content_table")
    void deleteAll();

    @Select("select a1.content_idx, a1.content_subject, a2.name as content_writer_name, " +
            "       DATE_FORMAT(a1.content_date, '%Y-%m-%d %H:%i:%s') as content_date " +
            "from content_table a1, member_table a2 " +
            "where a1.content_writer_idx = a2.member_idx " +
            "       and a1.content_board_idx = #{board_info_idx} " +
            "order by a1.content_idx desc")
    List<Content> getContentList(int board_info_idx, RowBounds rowBounds);

    @Delete("delete from content_table where content_idx = #{content_idx}")
    void deleteContentInfo(int content_idx);

    @Select("select count(*) from content_table where content_board_idx = #{content_board_idx}")
    int getContentCount(int content_board_idx);

    @Select("select content_writer_idx from content_table where content_idx = #{content_idx}")
    int getContextWriterIndexByContentIndex(int content_idx);
}
