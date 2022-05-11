package com.gxdxx.shop.controller;

import com.gxdxx.shop.dto.CommentFormDto;
import com.gxdxx.shop.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping(value = "/board/{boardId}/comment")  // 댓글쓰기
    public @ResponseBody
    ResponseEntity commentNew(@Valid @RequestBody CommentFormDto commentFormDto, BindingResult bindingResult,
                              @PathVariable("boardId") Long boardId, Principal principal) {

        if (bindingResult.hasErrors()) {    // 필수값이 들어있는지 검사
            return new ResponseEntity<String>("댓글을 50자 이내로 입력해주세요.", HttpStatus.FORBIDDEN);
        }

        String email = principal.getName();

        try {
            System.out.println(commentService.saveComment(email, boardId, commentFormDto));
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }


        return new ResponseEntity<Long>(boardId, HttpStatus.OK);
    }

    @GetMapping(value = "/board/{boardId}/comment/{commentId}")  // 댓글수정
    public @ResponseBody ResponseEntity updateComment(@PathVariable("boardId") Long boardId, @PathVariable("commentId") Long commentId, Principal principal) {

        if (!commentService.validateComment(commentId, principal.getName())) {
            return new ResponseEntity<String>("수정 권한이 없습니다.", HttpStatus.FORBIDDEN);
        }

        try {
            commentService.updateCommentView(commentId);
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }


        return new ResponseEntity<Long>(boardId, HttpStatus.OK);
    }

    @PostMapping(value = "/board/{boardId}/comment/{commentId}")  // 댓글수정
    public @ResponseBody ResponseEntity updateComment(@Valid @RequestBody CommentFormDto commentFormDto, BindingResult bindingResult,
                                                      @PathVariable("boardId") Long boardId, @PathVariable("commentId") Long commentId, Principal principal) {

        if (bindingResult.hasErrors()) {    // 필수값이 들어있는지 검사
            return new ResponseEntity<String>("댓글을 50자 이내로 입력해주세요.", HttpStatus.FORBIDDEN);
        }

        if (!commentService.validateComment(commentId, principal.getName())) {
            return new ResponseEntity<String>("수정 권한이 없습니다.", HttpStatus.FORBIDDEN);
        }

        try {
            commentService.updateComment(commentFormDto);
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }


        return new ResponseEntity<Long>(boardId, HttpStatus.OK);
    }

    @DeleteMapping(value = "/board/{boardId}/comment/{commentId}")  // 댓글 삭제
    public @ResponseBody ResponseEntity deleteComment(
            @PathVariable("boardId") Long boardId, @PathVariable("commentId") Long commentId, Principal principal) {

        if (!commentService.validateComment(commentId, principal.getName())) {
            return new ResponseEntity<String>("삭제 권한이 없습니다.", HttpStatus.FORBIDDEN);
        }

        commentService.deleteComment(boardId, commentId);
        return new ResponseEntity<Long>(boardId, HttpStatus.OK);
    }

}