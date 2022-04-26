package com.gxdxx.shop.entity;

import com.gxdxx.shop.dto.BoardFormDto;
import com.gxdxx.shop.dto.CommentFormDto;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="board")
@Getter
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

    @OneToMany(mappedBy = "board", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Comment> comments;

    private int commentCount;

    public static Board createBoard(Member member, BoardFormDto boardFormDto) {
        Board board = new Board();
        board.boardTitle = boardFormDto.getBoardTitle();
        board.boardContent = boardFormDto.getBoardContent();
        board.hits = 0;
        board.commentCount = 0;
        board.member = member;
        return board;
    }

    public void updateBoard(BoardFormDto boardFormDto) {
        this.boardTitle = boardFormDto.getBoardTitle();
        this.boardContent = boardFormDto.getBoardContent();
    }

    public void addHitsCount() {
        this.hits++;
    }

    public void addComment(Comment comment) {
        comments.add(comment);
        this.commentCount++;
    }

    public void removeCommentCount() {
        this.commentCount--;
    }

}