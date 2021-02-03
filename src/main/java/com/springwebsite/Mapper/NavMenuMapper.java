package com.springwebsite.Mapper;

import com.springwebsite.board.Board;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface NavMenuMapper {

    @Select("select * from board_info_table " +
            "order by board_info_idx")
    List<Board> getNavMenuList();
}
