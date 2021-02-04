package com.springwebsite.board;

import com.springwebsite.member.MemberDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardDao boardDao;
    private final MemberDao memberDao;

    public String getBoardInfoName(int board_info_idx) {
        return boardDao.getBoardInfoName(board_info_idx);
    }

    public void addContentInfo(Content content, String memberId) {

        int member_idx = memberDao.findMemberIndexById(memberId);
        content.setContent_writer_idx(member_idx);

        switch (content.getContent_board_idx()) {
            case 1 :
                boardDao.addGeneralContentInfo(content);
                break;

            case 2 :
                boardDao.addJobContentInfo(content);
                break;

            case 3 :
                boardDao.addMusicContentInfo(content);
                break;

            case 4 :
                boardDao.addSportContentInfo(content);
                break;
        }
    }

    public Content getContentInfo(int content_idx) {
        return boardDao.getContentInfo(content_idx);
    }
}
