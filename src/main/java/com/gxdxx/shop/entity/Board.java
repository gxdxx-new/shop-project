package com.gxdxx.shop.entity;

import com.gxdxx.shop.dto.BoardFormDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name="board")
@Getter
@Setter
@ToString
public class Board extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "board_id")
    private Long id;

    @Column(nullable = false, length = 20)
    private String boardTitle;    //글제목

    @Lob
    @Column(nullable = false)
    private String boardContent; //글내용

    @Column(nullable = false)
    private long hits;  //조회수

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public static Board createBoard(Member member, BoardFormDto boardFormDto) {
        Board board = new Board();
        board.boardTitle = boardFormDto.getBoardTitle();
        board.boardContent = boardFormDto.getBoardContent();
        board.hits = 0;
        board.setMember(member);
        return board;
    }

    public void updateBoard(BoardFormDto boardFormDto) {
        this.boardTitle = boardFormDto.getBoardTitle();
        this.boardContent = boardFormDto.getBoardContent();
    }

}