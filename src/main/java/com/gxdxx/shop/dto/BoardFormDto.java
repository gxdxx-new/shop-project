package com.gxdxx.shop.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class BoardFormDto {

    private Long id;

    @NotBlank(message = "글제목은 필수 입력 값입니다.")
    private String boardTitle;

    @NotBlank(message = "글내용은 필수 입력 값입니다.")
    private String boardContent;

}