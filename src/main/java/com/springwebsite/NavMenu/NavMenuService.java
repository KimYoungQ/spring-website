package com.springwebsite.NavMenu;

import com.springwebsite.board.Board;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NavMenuService {

    private final NavMenuDao navMenuDao;

    public List<Board> getNavMenuList() {
        return navMenuDao.getNavMenuList();
    }
}
