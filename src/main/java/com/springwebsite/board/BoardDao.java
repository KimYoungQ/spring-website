package com.springwebsite.board;

import com.springwebsite.mapper.BoardMapper;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class BoardDao {

    private final BoardMapper boardMapper;

    public String getBoardInfoName(int board_info_idx) {
        return boardMapper.getBoardInfoName(board_info_idx);
    }

    public void addContentInfo(Content content) {
        boardMapper.addContentInfo(content);
    }

    public Content getContentInfo(int content_idx) {
        return boardMapper.getContentInfo(content_idx);
    }

    public void modifyContentInfo(Content content) {
        boardMapper.modifyContentInfo(content);
    }

    public int findContentIdxbyContentDate(String content_date) {
        return boardMapper.findContentIdxbyContentDate(content_date);
    }

    public List<Content> getContentList(int board_info_idx, RowBounds rowBounds) {
        return boardMapper.getContentList(board_info_idx, rowBounds);
    }

    public void deleteContentInfo(int content_idx) {
        boardMapper.deleteContentInfo(content_idx);
    }

    public int getContentCount(int content_board_idx) {
        return boardMapper.getContentCount(content_board_idx);
    }
}
