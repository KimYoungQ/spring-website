package com.springwebsite.board;

import lombok.*;
import org.springframework.lang.Nullable;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Content {

    private int content_idx;

    @NotBlank(message = "제목을 입력해주세요.")
    private String content_subject;

    @NotBlank(message = "내용을 입력해주세요.")
    private String content_text;

    private String content_file;

    private int content_writer_idx;

    private int content_board_idx;

    private String content_date;

    private String content_writer_name;

    private String content_generalBoard_idx;

    private String content_jobBoard_idx;

    private String content_musicBoard_idx;

    private String content_sportBoard_idx;
}
