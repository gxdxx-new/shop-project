package com.gxdxx.shop.dto;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Getter
@Setter
public class CommentFormDto {

    private Long id;

    @NotBlank(message = "댓글 내용은 필수 입력 값입니다.")
    @Length(max = 50, message = "댓글은 50자 이하로 입력해주세요.")
    private String commentContent;

    private String createdBy;

    private LocalDateTime registerTime;

    public CommentFormDto(Long commentId, String commentContent, String createdBy, LocalDateTime registerTime) {
        this.id = commentId;
        this.commentContent = commentContent;
        this.createdBy = createdBy;
        this.registerTime = registerTime;
    }

}