package com.springwebsite.board;

import com.springwebsite.member.MemberDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.security.Principal;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardDao boardDao;
    private final MemberDao memberDao;

    private static final String FILE_PATH = "C:/spring/springwebsite/src/main/resources/static/images/";

    public String saveUploadFile(MultipartFile upload_file) {

        String file_name = System.currentTimeMillis() + "_" + upload_file.getOriginalFilename();

        try {
            upload_file.transferTo(new File(FILE_PATH + file_name));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return file_name;
    }

    public String getBoardInfoName(int board_info_idx) {
        return boardDao.getBoardInfoName(board_info_idx);
    }

    public void addContentInfo(Content content, String memberId) {

        Content addContent = addContentWriterIndex(content, memberId);
        addContent = addContentFile(content);

        boardDao.addContentInfo(addContent);

    }

    private Content addContentFile(Content content) {
        MultipartFile upload_file = content.getUpload_file();

        if (upload_file.getSize() > 0) {
            String file_name = saveUploadFile(upload_file);
            content.setContent_file(file_name);
        }

        return content;
    }

    private Content addContentWriterIndex(Content content, String memberId) {
        int member_idx = memberDao.findMemberIndexById(memberId);
        content.setContent_writer_idx(member_idx);
        return content;
    }

    public Content getContentInfo(int content_idx) {
        return boardDao.getContentInfo(content_idx);
    }

    public void modifyContentInfo(Content content) {
        boardDao.modifyContentInfo(content);
    }

    int findContentIdxbyContentDate(String content_date) {
        return boardDao.findContentIdxbyContentDate(content_date);
    }
}
