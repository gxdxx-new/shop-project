package com.gxdxx.shop.controller;

import com.gxdxx.shop.dto.BoardFormDto;
import com.gxdxx.shop.dto.ItemFormDto;
import com.gxdxx.shop.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @GetMapping(value = "/board/new")
    public String boardForm(Model model) {
        model.addAttribute("boardFormDto", new BoardFormDto());
        return "board/boardForm";
    }

    @PostMapping(value = "/board/new")
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

        return "redirect:/";
    }

}