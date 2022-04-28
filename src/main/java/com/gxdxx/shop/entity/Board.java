package com.gxdxx.shop.entity;

import com.gxdxx.shop.dto.BoardFormDto;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="board")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    public static Board createBoard(Member member, String boardTitle, String boardContent) {
        Board board = new Board();
        board.boardTitle = boardTitle;
        board.boardContent = boardContent;
        board.hits = 0;
        board.commentCount = 0;
        board.member = member;
        return board;
    }

    public void updateBoard(String boardTitle, String boardContent) {
        this.boardTitle = boardTitle;
        this.boardContent = boardContent;
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