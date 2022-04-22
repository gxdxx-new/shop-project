package com.gxdxx.shop.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class BoardDetailDto {

    private Long id;

    private String boardTitle;

    private String boardContent;

    private String createdBy;

    private LocalDateTime registerTime;

}