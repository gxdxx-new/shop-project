package com.gxdxx.shop.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class BoardDetailDto {

    private Long id;

    private String boardTitle;

    private String boardContent;

    private long hits;

    private String createdBy;

    private LocalDateTime registerTime;

    @Builder
    public BoardDetailDto(Long id, String boardTitle, String boardContent, long hits, String createdBy, LocalDateTime registerTime) {
        this.id = id;
        this.boardTitle = boardTitle;
        this.boardContent = boardContent;
        this.hits = hits;
        this.createdBy = createdBy;
        this.registerTime = registerTime;
    }

}