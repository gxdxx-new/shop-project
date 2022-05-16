package com.gxdxx.shop.service;

import com.gxdxx.shop.dto.*;
import com.gxdxx.shop.entity.Item;
import com.gxdxx.shop.entity.ItemImg;
import com.gxdxx.shop.exception.ItemNotFoundException;
import com.gxdxx.shop.repository.ItemImgRepository;
import com.gxdxx.shop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ItemService {

    private final ItemRepository itemRepository;
    private final ItemImgService itemImgService;
    private final ItemImgRepository itemImgRepository;

    public Long saveItem(ItemFormDto itemFormDto, List<MultipartFile> itemImgFileList) throws Exception {

        //상품 등록
        Item item = Item.builder()
                .itemName(itemFormDto.getItemName())
                .price(itemFormDto.getPrice())
                .itemDescription(itemFormDto.getItemDescription())
                .itemSellStatus(itemFormDto.getItemSellStatus())
                .stockQuantity(itemFormDto.getStockQuantity())
                .build();
        itemRepository.save(item);

        //이미지 등록
        for (int i = 0; i < itemImgFileList.size(); i++) {
            ItemImg itemImg = ItemImg.saveItemImg(item, i);
            itemImgService.saveItemImg(itemImg, itemImgFileList.get(i));
        }

        return item.getId();
    }

    @Transactional(readOnly = true)
    public ItemFormDto getItemDtl(Long itemId) {

        Item item = itemRepository.findById(itemId).orElseThrow(ItemNotFoundException::new);
        ItemFormDto itemFormDto = ItemFormDto.builder()
                .id(item.getId())
                .itemName(item.getItemName())
                .itemDescription(item.getItemDescription())
                .price(item.getPrice())
                .itemSellStatus(item.getItemSellStatus())
                .build();

        List<ItemImg> itemImgList = itemImgRepository.findByItemIdOrderByIdAsc(itemId);
        List<ItemImgDto> itemImgDtoList = new ArrayList<>();
        for (ItemImg itemImg : itemImgList) {
            ItemImgDto itemImgDto = ItemImgDto.builder()
                    .id(itemImg.getId())
                    .imgName(itemImg.getImgName())
                    .oriImgName(itemImg.getOriImgName())
                    .imgUrl(itemImg.getImgUrl())
                    .repImgYn(itemImg.getRepImgYn())
                    .build();
            itemImgDtoList.add(itemImgDto);
        }
        itemFormDto.setItemImgDtoList(itemImgDtoList);

        return itemFormDto;
    }

    public Long updateItem(ItemFormDto itemFormDto, List<MultipartFile> itemImgFileList) throws Exception {

        //상품 수정
        Item item = itemRepository.findById(itemFormDto.getId()).orElseThrow(ItemNotFoundException::new);
        item.updateItem(itemFormDto.getItemName(), itemFormDto.getPrice(), itemFormDto.getStockQuantity(), itemFormDto.getItemDescription(), itemFormDto.getItemSellStatus());

        List<Long> itemImgIds = itemFormDto.getItemImgIds();

        //이미지 등록
        for (int i = 0; i < itemImgFileList.size(); i++) {
            itemImgService.updateItemImg(itemImgIds.get(i), itemImgFileList.get(i));
        }

        return item.getId();
    }

    @Transactional(readOnly = true)
    public Page<ItemListDto> getAdminItemPage(ItemSearchDto itemSearchDto, Pageable pageable) {
        return itemRepository.getAdminItemPage(itemSearchDto, pageable);
    }

    @Transactional(readOnly = true)
    public Page<MainItemDto> getMainItemPage(ItemSearchDto itemSearchDto, Pageable pageable) {
        return itemRepository.getMainItemPage(itemSearchDto, pageable);
    }

}