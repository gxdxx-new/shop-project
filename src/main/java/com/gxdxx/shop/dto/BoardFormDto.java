package com.gxdxx.shop.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@Getter
@Setter
public class BoardFormDto {

    private Long id;

    @NotBlank(message = "글제목은 필수 입력 값입니다.")
    private String boardTitle;

    @NotBlank(message = "글내용은 필수 입력 값입니다.")
    private String boardContent;

    @Builder
    public BoardFormDto(Long id, String boardTitle, String boardContent) {
        this.id = id;
        this.boardTitle = boardTitle;
        this.boardContent = boardContent;
    }

}