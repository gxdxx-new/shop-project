package com.gxdxx.shop.service;

import com.gxdxx.shop.dto.*;
import com.gxdxx.shop.entity.*;
import com.gxdxx.shop.repository.BoardRepository;
import com.gxdxx.shop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityNotFoundException;

import static com.gxdxx.shop.entity.Board.createBoard;

@Service
@Transactional
@RequiredArgsConstructor
public class BoardService {

    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;

    public Long saveBoard(String email, BoardFormDto boardFormDto) throws Exception {

        Member member = memberRepository.findByEmail(email);

        //글 등록
        Board board = createBoard(member, boardFormDto);
        boardRepository.save(board);

        return board.getId();
    }

    @Transactional(readOnly = true)
    public BoardFormDto getBoardForm(Long boardId) {

        Board board = boardRepository.findById(boardId).orElseThrow(EntityNotFoundException::new);
        BoardFormDto boardFormDto = new BoardFormDto();
        boardFormDto.setId(board.getId());
        boardFormDto.setBoardTitle(board.getBoardTitle());
        boardFormDto.setBoardContent(board.getBoardContent());

        return boardFormDto;
    }

    @Transactional(readOnly = true)
    public BoardDetailDto getBoardDetail(Long boardId) {

        Board board = boardRepository.findById(boardId).orElseThrow(EntityNotFoundException::new);
        BoardDetailDto boardDetailDto = new BoardDetailDto();
        boardDetailDto.setId(board.getId());
        boardDetailDto.setBoardTitle(board.getBoardTitle());
        boardDetailDto.setBoardContent(board.getBoardContent());
        boardDetailDto.setHits(board.getHits());
        boardDetailDto.setCreatedBy(board.getCreatedBy());
        boardDetailDto.setRegisterTime(board.getRegisterTime());

        return boardDetailDto;
    }

    public void hitsCount(Long boardId) {
        Board board = boardRepository.findById(boardId).orElseThrow(EntityNotFoundException::new);

        board.setHits(board.getHits() + 1);

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

        //상품 수정
        Board board = boardRepository.findById(boardFormDto.getId()).orElseThrow(EntityNotFoundException::new);
        board.updateBoard(boardFormDto);

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

}