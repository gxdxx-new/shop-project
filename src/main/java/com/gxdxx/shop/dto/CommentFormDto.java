package com.gxdxx.shop.dto;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class CommentFormDto {

    @NotBlank(message = "댓글 내용은 필수 입력 값입니다.")
    @Length(max = 50, message = "댓글은 50자 이하로 입력해주세요.")
    private String commentContent;

}