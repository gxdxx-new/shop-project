package com.gxdxx.shop.entity;

import com.gxdxx.shop.dto.CommentFormDto;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="comment")
@Getter
@Setter
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "comment_id")
    private Long id;

    @Lob
    @Column(nullable = false)
    private String commentContent;    //댓글내용

    private int status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    public void updateComment(CommentFormDto commentFormDto) {
        this.commentContent = commentFormDto.getCommentContent();
        this.status = 1;
    }

}