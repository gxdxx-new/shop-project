package com.gxdxx.shop.service;

import com.gxdxx.shop.dto.*;
import com.gxdxx.shop.entity.*;
import com.gxdxx.shop.exception.BoardAjaxNotFoundException;
import com.gxdxx.shop.exception.BoardNotFoundException;
import com.gxdxx.shop.repository.BoardRepository;
import com.gxdxx.shop.repository.CommentRepository;
import com.gxdxx.shop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

import static com.gxdxx.shop.entity.Board.createBoard;

@Service
@Transactional
@RequiredArgsConstructor
public class BoardService {

    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;

    public Long saveBoard(String email, BoardFormDto boardFormDto) {

        Member member = memberRepository.findByEmail(email);

        //글 등록
        Board board = createBoard(member, boardFormDto.getBoardTitle(), boardFormDto.getBoardContent());
        boardRepository.save(board);

        return board.getId();
    }

    @Transactional(readOnly = true)
    public BoardFormDto getBoardForm(Long boardId) {

        Board board = boardRepository.findById(boardId).orElseThrow(BoardNotFoundException::new);
        BoardFormDto boardFormDto = BoardFormDto.builder()
                .id(board.getId())
                .boardTitle(board.getBoardTitle())
                .boardContent(board.getBoardContent())
                .build();

        return boardFormDto;
    }

    @Transactional(readOnly = true)
    public BoardDetailDto getBoardDetail(Long boardId) {

        Board board = boardRepository.findById(boardId).orElseThrow(BoardNotFoundException::new);
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
        Board board = boardRepository.findById(boardId).orElseThrow(BoardNotFoundException::new);
        board.addHitsCount();
    }

    @Transactional(readOnly = true)
    public boolean validateBoard(Long boardId, String email) {
        Member currentMember = memberRepository.findByEmail(email);
        Board board = boardRepository.findById(boardId).orElseThrow(BoardNotFoundException::new);
        Member savedMember = board.getMember();

        if (!StringUtils.equals(currentMember.getEmail(), savedMember.getEmail())) {
            return false;
        }

        return true;
    }

    public Long updateBoard(BoardFormDto boardFormDto) {

        //게시글 수정
        Board board = boardRepository.findById(boardFormDto.getId()).orElseThrow(BoardNotFoundException::new);
        board.updateBoard(boardFormDto.getBoardTitle(), board.getBoardContent());

        return board.getId();
    }

    public void deleteBoard(Long boardId) {
        Board board = boardRepository.findById(boardId).orElseThrow(BoardAjaxNotFoundException::new);
        boardRepository.delete(board);
    }

    @Transactional(readOnly = true)
    public Page<BoardListDto> getBoardPage(BoardSearchDto boardSearchDto, Pageable pageable) {
        return boardRepository.getBoardPage(boardSearchDto, pageable);
    }

}