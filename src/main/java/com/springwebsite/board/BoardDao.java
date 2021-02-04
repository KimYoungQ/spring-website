package com.springwebsite.board;

import com.springwebsite.mapper.BoardMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class BoardDao {

    private final BoardMapper boardMapper;

    public String getBoardInfoName(int board_info_idx) {
        return boardMapper.getBoardInfoName(board_info_idx);
    }

    public void addGeneralContentInfo(Content content) {
        boardMapper.addGeneralContentInfo(content);
    }

    public void addMusicContentInfo (Content content) {
        boardMapper.addMusicContentInfo(content);
    }

    public void addJobContentInfo(Content content) {
        boardMapper.addJobContentInfo(content);
    }

    public void addSportContentInfo(Content content) {
        boardMapper.addsportContentInfo(content);
    }

    public Content getContentInfo(int content_idx) {
        return boardMapper.getContentInfo(content_idx);
    }
}
