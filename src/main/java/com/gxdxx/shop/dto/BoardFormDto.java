package com.gxdxx.shop.dto;

import com.gxdxx.shop.constant.ItemSellStatus;
import com.gxdxx.shop.entity.Item;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class BoardFormDto {

    private Long id;

    @NotBlank(message = "글제목은 필수 입력 값입니다.")
    private String boardTitle;

    @NotBlank(message = "글내용은 필수 입력 값입니다.")
    private String boardContent;

}