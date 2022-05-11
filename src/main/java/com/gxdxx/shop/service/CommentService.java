package com.gxdxx.shop.service;

import com.gxdxx.shop.dto.CommentFormDto;
import com.gxdxx.shop.entity.Board;
import com.gxdxx.shop.entity.Comment;
import com.gxdxx.shop.entity.Member;
import com.gxdxx.shop.repository.BoardRepository;
import com.gxdxx.shop.repository.CommentRepository;
import com.gxdxx.shop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {

    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;

    public Long saveComment(String email, Long boardId, CommentFormDto commentFormDto) throws Exception {

        Member member = memberRepository.findByEmail(email);
        Board board = boardRepository.findById(boardId).orElseThrow(EntityNotFoundException::new);

        Comment comment = Comment.createComment(member, board, commentFormDto.getCommentContent());
        commentRepository.save(comment);

        return board.getId();
    }

    @Transactional(readOnly = true)
    public List<CommentFormDto> getComments(Long boardId) {

        Board board = boardRepository.findById(boardId).orElseThrow(EntityNotFoundException::new);

        List<CommentFormDto> commentFormDtos = new ArrayList<>();

        for (Comment comment : board.getComments()) {
            commentFormDtos.add(
                    CommentFormDto.builder()
                            .commentId(comment.getId())
                            .commentContent(comment.getCommentContent())
                            .status(comment.getStatus())
                            .createdBy(comment.getCreatedBy())
                            .registerTime(comment.getRegisterTime())
                            .build()
            );
        }

        return commentFormDtos;
    }

    @Transactional(readOnly = true)
    public int getCommentsCount(Long boardId) {

        Board board = boardRepository.findById(boardId).orElseThrow(EntityNotFoundException::new);

        return board.getComments().size();
    }

    @Transactional(readOnly = true)
    public boolean validateComment(Long commentId, String email) {
        Member currentMember = memberRepository.findByEmail(email);
        Comment comment = commentRepository.findById(commentId).orElseThrow(EntityNotFoundException::new);
        Member savedMember = comment.getMember();

        if (!StringUtils.equals(currentMember.getEmail(), savedMember.getEmail())) {
            return false;
        }

        return true;
    }

    public void updateCommentView(Long commentId) throws Exception {

        //댓글 수정
        Comment comment = commentRepository.findById(commentId).orElseThrow(EntityNotFoundException::new);
        comment.changeStatus();

    }

    public void updateComment(CommentFormDto commentFormDto) throws Exception {

        Comment comment = commentRepository.findById(commentFormDto.getId()).orElseThrow(EntityNotFoundException::new);
        comment.updateComment(commentFormDto.getCommentContent());

    }

    public void deleteComment(Long boardId, Long commentId) {
        Board board = boardRepository.findById(boardId).orElseThrow(EntityNotFoundException::new);
        Comment comment = commentRepository.findById(commentId).orElseThrow(EntityNotFoundException::new);

        // 게시글에서도 삭제하고, 댓글에서도 삭제
        for (Comment findComment : board.getComments()) {
            if (findComment.getId() == comment.getId()) {
                board.getComments().remove(findComment);
                board.removeCommentCount();
                break;
            }
        }

        commentRepository.delete(comment);
    }

}