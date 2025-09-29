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
import likelion13th.codashop.DTO.response.S3Item;

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
        S3Item s3Result=s3Service.uploadFileForItem(file);
        Item item = new Item(
                request.getItemname(),
                request.getPrice(),
                s3Result.getUrl(),
                s3Result.getItemName(),
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
        Item item=itemRepository.findById(itemId).orElseThrow(()-> new GeneralException(ErrorCode.ITEM_NOT_FOUND));
        s3Service.deleteFile(item.getS3ImgKey());
        itemRepository.deleteById(itemId);
    }
    //item 수정
    @Transactional
    public ItemResponseDto fixItem(Long itemId, ItemCreateRequest request,MultipartFile file) {
        Item item=itemRepository.findById(itemId)
                .orElseThrow(()-> new GeneralException(ErrorCode.ITEM_NOT_FOUND));
        s3Service.deleteFile(item.getS3ImgKey());
        S3Item s3Result=s3Service.uploadFileForItem(file);
        item.setItemname(request.getItemname());
        item.setPrice(request.getPrice());
        item.setBrand(request.getBrand());
        item.setImagePath(s3Result.getUrl());
        item.setS3ImgKey(s3Result.getItemName());
        item.setNew(request.isNew());
        item.setCategories(categoryRepository.findAllById(request.getCategoryIds()));

        return ItemResponseDto.from(item);
    }


}
