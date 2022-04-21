package com.gxdxx.shop.controller;

import com.gxdxx.shop.dto.BoardFormDto;
import com.gxdxx.shop.dto.BoardSearchDto;
import com.gxdxx.shop.dto.ItemFormDto;
import com.gxdxx.shop.dto.ItemSearchDto;
import com.gxdxx.shop.entity.Board;
import com.gxdxx.shop.entity.Item;
import com.gxdxx.shop.entity.Member;
import com.gxdxx.shop.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @GetMapping(value = "/board/new")   // 게시글 form
    public String boardForm(Model model) {
        model.addAttribute("boardFormDto", new BoardFormDto());
        return "board/boardForm";
    }

    @PostMapping(value = "/board/new")  // 글쓰기
    public String boardNew(@Valid BoardFormDto boardFormDto, BindingResult bindingResult,
                           Principal principal, Model model) {

        if (bindingResult.hasErrors()) {    // 필수값이 들어있는지 검사
            return "board/boardForm";
        }

        String email = principal.getName();
        Long boardId;

        try {
            boardId = boardService.saveBoard(email, boardFormDto);
        } catch (Exception e) {
            model.addAttribute("errorMessage", "글작성 중 에러가 발생했습니다.");
            return "board/boardForm";
        }

        return "redirect:/boards";
    }

    @GetMapping(value = "/board/{boardId}")
    public String boardDtl(Model model, @PathVariable("boardId") Long boardId) {

        BoardFormDto boardFormDto = boardService.getBoardDtl(boardId);
        Member member = boardService.getBoardMember(boardId);

        model.addAttribute("board", boardFormDto);
        model.addAttribute("member", member.getEmail());
        return "board/boardDtl";
    }

    @DeleteMapping(value = "/board/{boardId}")
    public @ResponseBody ResponseEntity deleteBoard(
            @PathVariable("boardId") Long boardId, Principal principal) {

        if (!boardService.validateBoard(boardId, principal.getName())) {
            return new ResponseEntity<String>("삭제 권한이 없습니다.", HttpStatus.FORBIDDEN);
        }

        boardService.deleteBoard(boardId);
        return new ResponseEntity<Long>(boardId, HttpStatus.OK);
    }

    @GetMapping(value = {"/boards", "/boards/{page}"})
    public String boardList(BoardSearchDto boardSearchDto, @PathVariable("page") Optional<Integer> page, Model model) {

        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 10);

        Page<Board> boards = boardService.getBoardPage(boardSearchDto, pageable);

        model.addAttribute("boards", boards);
        model.addAttribute("boardSearchDto", boardSearchDto);
        model.addAttribute("maxPage", 5);

        return "board/boardList";
    }

}