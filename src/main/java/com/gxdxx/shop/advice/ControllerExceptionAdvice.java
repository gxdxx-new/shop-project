package com.gxdxx.shop.advice;

import com.gxdxx.shop.dto.BoardFormDto;
import com.gxdxx.shop.dto.ItemFormDto;
import com.gxdxx.shop.dto.MemberFormDto;
import com.gxdxx.shop.exception.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.w3c.dom.events.EventException;

@ControllerAdvice
@Slf4j
public class ControllerExceptionAdvice {

    @ExceptionHandler(BoardAjaxNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<String> boardAjaxNotFoundException() {
        return new ResponseEntity<String>("존재하지 않는 게시글입니다.", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BoardNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String boardNotFoundException(Model model) {
        model.addAttribute("errorMessage", "존재하지 않는 게시글입니다.");
        model.addAttribute("boardFormDto", new BoardFormDto());
        return "board/boardForm";
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String exception(Exception e, Model model) {
        model.addAttribute("errorMessage", "에러가 발생했습니다.");
        return "main";
    }

    @ExceptionHandler(MemberEmailAlreadyExistsException.class)
    public String memberEmailAlreadyExistsException(MemberEmailAlreadyExistsException e, Model model) {
        model.addAttribute("errorMessage", e.getMessage() + "은 중복된 이메일 입니다.");
        model.addAttribute("memberFormDto", new MemberFormDto());
        return "member/memberForm";
    }

    @ExceptionHandler(CommentNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<String> commentNotFoundException(Model model) {
        return new ResponseEntity<String>("존재하지 않는 댓글입니다.", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ItemNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String itemNotFoundException(Model model) {
        model.addAttribute("errorMessage", "존재하지 않는 상품입니다.");
        model.addAttribute("itemFormDto", new ItemFormDto());
        return "item/itemForm";
    }

    @ExceptionHandler(ItemAjaxNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<String> itemAjaxNotFoundException() {
        return new ResponseEntity<String>("존재하지 않는 상품입니다.", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(OrderNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<String> orderNotFoundException() {
        return new ResponseEntity<String>("존재하지 않는 주문입니다.", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CartItemNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String cartItemNotFoundException(Model model) {
        model.addAttribute("errorMessage", "존재하지 않는 주문상품입니다.");
        return "cart/cartList";
    }

    @ExceptionHandler(ItemImgNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String itemImgNotFoundException(Model model) {
        model.addAttribute("errorMessage", "존재하지 않는 상품이미지입니다.");
        return "item/itemManagement";
    }


    @ExceptionHandler({EventException.class, RuntimeException.class})
    public String eventErrorHandler(RuntimeException exception, Model model){
        model.addAttribute("message", "runtime error");
        return "error";
    }

}