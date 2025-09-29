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
import likelion13th.codashop.S3.S3Service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;
    private final CategoryRepository categoryRepository;
    private final S3Service s3Service;


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



    //상품 생성
    @Transactional
    public ItemResponseDto createItem(ItemCreateRequest request, MultipartFile file) {
        Item item = new Item(
                request.getItemname(),
                request.getPrice(),
                request.getBrand(),
                request.isNew()
        );

        item.setCategories(categoryRepository.findAllById(request.getCategoryIds()));
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
    public ItemResponseDto fixItem(Long itemId, ItemCreateRequest request) {
        Item item=itemRepository.findById(itemId)
                .orElseThrow(()-> new GeneralException(ErrorCode.ITEM_NOT_FOUND));
        item.setItemname(request.getItemname());
        item.setPrice(request.getPrice());
        item.setBrand(request.getBrand());
        item.setImagePath(request.getImagePath());
        item.setNew(request.isNew());
        item.setCategories(categoryRepository.findAllById(request.getCategoryIds()));

        return ItemResponseDto.from(item);
    }


}
