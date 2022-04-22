package com.gxdxx.shop.repository;

import com.gxdxx.shop.dto.ItemListDto;
import com.gxdxx.shop.dto.ItemSearchDto;
import com.gxdxx.shop.dto.MainItemDto;
import com.gxdxx.shop.entity.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ItemRepositoryCustom {

    Page<ItemListDto> getAdminItemPage(ItemSearchDto itemSearchDto, Pageable pageable);

    Page<MainItemDto> getMainItemPage(ItemSearchDto itemSearchDto, Pageable pageable);

}