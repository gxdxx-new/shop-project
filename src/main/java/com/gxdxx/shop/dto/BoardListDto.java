package com.gxdxx.shop.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class BoardListDto {

    private Long id;

    private String boardTitle;

    private long hits;

    private String createdBy;

    private LocalDateTime registerTime;

    @QueryProjection
    public BoardListDto(Long id, String boardTitle, long hits, String createdBy, LocalDateTime registerTime) {
        this.id = id;
        this.boardTitle = boardTitle;
        this.hits = hits;
        this.createdBy = createdBy;
        this.registerTime = registerTime;
    }

}