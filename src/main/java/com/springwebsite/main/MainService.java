package com.springwebsite.main;

import com.springwebsite.board.BoardDao;
import com.springwebsite.board.Content;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MainService {

    private final BoardDao boardDao;

    public List<Content> getContentList(int board_info_idx){
        RowBounds rowBounds = new RowBounds(0,5);
        return boardDao.getContentList(board_info_idx, rowBounds);
    }
}
