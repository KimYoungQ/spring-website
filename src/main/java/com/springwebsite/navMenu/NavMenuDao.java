package com.springwebsite.navMenu;

import com.springwebsite.mapper.NavMenuMapper;
import com.springwebsite.board.Board;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class NavMenuDao {

    private final NavMenuMapper navMenuMapper;

    public List<Board> getNavMenuList() {
        return navMenuMapper.getNavMenuList();
    }
}
