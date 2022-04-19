package com.gxdxx.shop.service;

import com.gxdxx.shop.dto.*;
import com.gxdxx.shop.entity.Board;
import com.gxdxx.shop.entity.Item;
import com.gxdxx.shop.entity.ItemImg;
import com.gxdxx.shop.entity.Member;
import com.gxdxx.shop.repository.BoardRepository;
import com.gxdxx.shop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

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

    public Long saveBoard(String email, BoardFormDto boardFormDto) throws Exception {

        Member member = memberRepository.findByEmail(email);

        //글 등록
        Board board = createBoard(member, boardFormDto);
        boardRepository.save(board);

        return board.getId();
    }

    @Transactional(readOnly = true)
    public Page<Board> getBoardPage(BoardSearchDto boardSearchDto, Pageable pageable) {
        return boardRepository.getBoardPage(boardSearchDto, pageable);
    }

}