package com.springwebsite.board;

import com.springwebsite.member.Member;
import com.springwebsite.member.MemberDao;
import com.springwebsite.setting.Paging;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardDao boardDao;
    private final MemberDao memberDao;

    @Value("${page.listCount}")
    private int PAGE_LISTCOUNT;

    @Value("${page.paginationCount}")
    private int PAGE_PAGINATIONCOUNT;

    public String getBoardInfoName(int board_info_idx) {
        return boardDao.getBoardInfoName(board_info_idx);
    }

    public void addContentInfo(Content content, String memberId) throws IOException {

        Content addContent = addContentWriterIndex(content, memberId);
        addContent = addContentFile(content);

        boardDao.addContentInfo(addContent);

    }

    private Content addContentFile(Content content) {
        return content;
    }

    private Content addContentWriterIndex(Content content, String memberId) {
        Member member = memberDao.findById(memberId);
        int member_idx = member.getMember_idx();
        content.setContent_writer_idx(member_idx);
        return content;
    }

    public Content getContentInfo(int content_idx) {
        return boardDao.getContentInfo(content_idx);
    }

    public void modifyContentInfo(Content content, String memberId) throws IOException {
        Content addContent = addContentWriterIndex(content, memberId);
        addContent = addContentFile(content);
        boardDao.modifyContentInfo(content);
    }

    public List<Content> getContentList(int board_info_idx, int page) {

        int start = (page -1) * PAGE_LISTCOUNT;
        RowBounds rowBounds = new RowBounds(start, PAGE_LISTCOUNT);
        return boardDao.getContentList(board_info_idx, rowBounds);
    }

    public void deleteContentInfo(int content_idx) {
        boardDao.deleteContentInfo(content_idx);
    }

    public Paging getContentCount(int content_board_idx, int currentPage) {

        int contentCount = boardDao.getContentCount(content_board_idx);
        Paging paging = new Paging(contentCount, currentPage, PAGE_LISTCOUNT, PAGE_PAGINATIONCOUNT);

        return paging;
    }

    public int getContextWriterIndexByContentIndex(int content_idx) {
        return boardDao.getContextWriterIndexByContentIndex(content_idx);
    }
}
