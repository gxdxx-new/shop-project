package com.gxdxx.shop.service;

import com.gxdxx.shop.dto.*;
import com.gxdxx.shop.entity.*;
import com.gxdxx.shop.repository.BoardRepository;
import com.gxdxx.shop.repository.CommentRepository;
import com.gxdxx.shop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityNotFoundException;

import java.util.ArrayList;
import java.util.List;

import static com.gxdxx.shop.entity.Board.createBoard;

@Service
@Transactional
@RequiredArgsConstructor
public class BoardService {

    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;

    public Long saveBoard(String email, BoardFormDto boardFormDto) throws Exception {

        Member member = memberRepository.findByEmail(email);

        //글 등록
        Board board = createBoard(member, boardFormDto.getBoardTitle(), boardFormDto.getBoardContent());
        boardRepository.save(board);

        return board.getId();
    }

    @Transactional(readOnly = true)
    public BoardFormDto getBoardForm(Long boardId) {

        Board board = boardRepository.findById(boardId).orElseThrow(EntityNotFoundException::new);
        BoardFormDto boardFormDto = BoardFormDto.builder()
                .id(board.getId())
                .boardTitle(board.getBoardTitle())
                .boardContent(board.getBoardContent())
                .build();

        return boardFormDto;
    }

    @Transactional(readOnly = true)
    public BoardDetailDto getBoardDetail(Long boardId) {

        Board board = boardRepository.findById(boardId).orElseThrow(EntityNotFoundException::new);
        BoardDetailDto boardDetailDto = BoardDetailDto.builder()
                .id(board.getId())
                .boardTitle(board.getBoardTitle())
                .boardContent(board.getBoardContent())
                .hits(board.getHits())
                .createdBy(board.getCreatedBy())
                .registerTime(board.getRegisterTime())
                .build();

        return boardDetailDto;
    }

    public void hitsCount(Long boardId) {
        Board board = boardRepository.findById(boardId).orElseThrow(EntityNotFoundException::new);

        board.addHitsCount();

    }

    @Transactional(readOnly = true)
    public boolean validateBoard(Long boardId, String email) {
        Member currentMember = memberRepository.findByEmail(email);
        Board board = boardRepository.findById(boardId).orElseThrow(EntityNotFoundException::new);
        Member savedMember = board.getMember();

        if (!StringUtils.equals(currentMember.getEmail(), savedMember.getEmail())) {
            return false;
        }

        return true;
    }

    public Long updateBoard(BoardFormDto boardFormDto) throws Exception {

        //게시글 수정
        Board board = boardRepository.findById(boardFormDto.getId()).orElseThrow(EntityNotFoundException::new);
        board.updateBoard(boardFormDto.getBoardTitle(), board.getBoardContent());

        return board.getId();
    }

    public void deleteBoard(Long boardId) {
        Board board = boardRepository.findById(boardId).orElseThrow(EntityNotFoundException::new);
        boardRepository.delete(board);
    }

    @Transactional(readOnly = true)
    public Page<BoardListDto> getBoardPage(BoardSearchDto boardSearchDto, Pageable pageable) {
        return boardRepository.getBoardPage(boardSearchDto, pageable);
    }

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