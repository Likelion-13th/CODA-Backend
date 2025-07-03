package likelion13th.codashop.service;

import jakarta.transaction.Transactional;
import likelion13th.codashop.DTO.request.ItemCreateRequest;
import likelion13th.codashop.DTO.response.ItemResponseDto;
import likelion13th.codashop.domain.Item;
import likelion13th.codashop.domain.Category;
import likelion13th.codashop.global.api.ErrorCode;
import likelion13th.codashop.global.exception.GeneralException;
import likelion13th.codashop.repository.CategoryRepository;
import likelion13th.codashop.repository.ItemRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;
    private final CategoryRepository categoryRepository;

    // 개별 상품 조회
    @Transactional
    public ItemResponseDto getItemById(Long itemId) {
        return itemRepository.findById(itemId)
                .map(ItemResponseDto::from)
                .orElseThrow(()-> new GeneralException(ErrorCode.ITEM_NOT_FOUND));
    }

    //모든 상품 조회
    @Transactional
    public List<ItemResponseDto> getAllItems() {
        return itemRepository.findAll().stream()
                .map(ItemResponseDto::from)
                .collect(Collectors.toList());
    }

    //카테고리별 상품조회
    @Transactional
    public List<ItemResponseDto> getItemsByCategory(Long categoryId) {
        Category category=categoryRepository.findById(categoryId)
                .orElseThrow(()-> new GeneralException(ErrorCode.CATEGORY_NOT_FOUND));
        return category.getItems().stream()
                .map(ItemResponseDto::from)
                .collect(Collectors.toList());
    }

    //상품 생성
    @Transactional
    public ItemResponseDto createItem(ItemCreateRequest request) {
        Item item = new Item(
                request.getItemname(),
                request.getPrice(),
                request.getBrand(),
                request.getImagePath(),
                request.isNew()
        );

        item.setCategories(request.getCategories());
        itemRepository.save(item);

        return ItemResponseDto.from(item);
    }

    //item 삭제
    @Transactional
    public void deleteItem(Long itemId) {
        if(!itemRepository.existsById(itemId)) {
            throw new GeneralException(ErrorCode.ITEM_NOT_FOUND);
        }
        itemRepository.deleteById(itemId);
    }
    //item 수정
    @Transactional
    public void fixItem(Long itemId, ItemCreateRequest request) {
        Item item=itemRepository.findById(itemId)
                .orElseThrow(()-> new GeneralException(ErrorCode.ITEM_NOT_FOUND));
        item.setItemname(request.getItemname());
        item.setPrice(request.getPrice());
        item.setBrand(request.getBrand());
        item.setImagePath(request.getImagePath());
        item.setNew(request.isNew());
        item.setCategories(request.getCategories());
    }


}
