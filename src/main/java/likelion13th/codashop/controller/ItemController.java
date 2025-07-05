package likelion13th.codashop.controller;

import io.swagger.v3.oas.annotations.Operation;
import likelion13th.codashop.DTO.request.OrderCreateRequest;
import likelion13th.codashop.DTO.response.ItemResponseDto;
import likelion13th.codashop.DTO.request.ItemCreateRequest;
import likelion13th.codashop.DTO.response.OrderResponseDto;
import likelion13th.codashop.domain.Category;
import likelion13th.codashop.domain.Item;
import likelion13th.codashop.global.api.ApiResponse;
import likelion13th.codashop.global.api.ErrorCode;
import likelion13th.codashop.global.api.SuccessCode;
import likelion13th.codashop.global.exception.GeneralException;
import likelion13th.codashop.service.CategoryService;
import likelion13th.codashop.service.ItemService;
import likelion13th.codashop.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/items")
@RequiredArgsConstructor

public class ItemController {
    private final ItemService itemService;
    private final CategoryService categoryService;

    //모든 상품 조
    @GetMapping
    @Operation(summary = "모든 상품 조회", description = "모든 상품을 목록으로 조회합니다.")
    public ApiResponse<?> getAllItems() {
        List<ItemResponseDto> items = itemService.getAllItems();
        if (items.isEmpty()) {
            return ApiResponse.onSuccess(SuccessCode.CATEGORY_ITEMS_EMPTY,Collections.emptyList());
        }
        return ApiResponse.onSuccess(SuccessCode.ITEM_GET_SUCCESS,items);
    }

    //개별 상품 조회
    @GetMapping("/{itemId}")
    @Operation(summary = "개별상품조회",description = "아이템을 개별 조회합니다.")
    public ApiResponse<?> getItem(@PathVariable Long itemId) {
        log.info("[STEP 1 개별 상품 조회 요청 수신..]");
        try{
            ItemResponseDto item = itemService.getItemById(itemId);
            log.info("[STEP 2] 개별 상품 조회 성공");
            return ApiResponse.onSuccess(SuccessCode.ITEM_GET_SUCCESS,item);
        }
        catch(GeneralException e){
            log.error("❌ [ERROR] 개별 상품 조회중 예외 발생: {}",e.getReason().getMessage());
            throw e;
        }
        catch(Exception e){
            log.error("[ERROR] 알 수 없는 예외 발생: {}",e.getMessage());
            throw new GeneralException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    //상품 생성
    @PostMapping
    @Operation(summary = "상품 생성", description = "요청한 상품을 생성합니다.")
    public ApiResponse<?> createItem(
            @RequestBody ItemCreateRequest request
    ){
        log.info("[STEP 1] 상품 생성 요청 수신..");
        try{
            ItemResponseDto newItem=itemService.createItem(request);
            log.info("[STEP 2] 상품 생성 성공");
            return ApiResponse.onSuccess(SuccessCode.OK,newItem);
        }
        catch(GeneralException e){
            log.error("< ❌[ERROR] 상품 생성 중 예외 발생 {}",e.getReason().getMessage());
            throw e;
        }
        catch (Exception e){
            log.error("❌ [ERROR] 알 수 없는 예외 발생: {}", e.getMessage());
            throw new GeneralException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    //상품 수정
    @PutMapping("/{itemId}")
    @Operation(summary ="상품 수정", description = "요청 상품 정보를 수정합니다.")
    public ApiResponse<?> updateItem(
            @PathVariable Long itemId,
            @RequestBody ItemCreateRequest request
    ){
        log.info("[STEP 1] 상품 정보 수정 요청 수신..");
        try{
            ItemResponseDto fixedItem=itemService.fixItem(itemId,request);
            log.info("[STEP 2] 상품 정보 수정 성공");
            return ApiResponse.onSuccess(SuccessCode.OK,fixedItem);
        }
        catch (GeneralException e) {
            log.error("❌ [ERROR] 개별 주문 조회 중 예외 발생: {}", e.getReason().getMessage());
            throw e;
        }
        catch (Exception e){
            log.error("❌ [ERROR] 알 수 없는 예외 발생: {}", e.getMessage());
            throw new GeneralException(ErrorCode.INTERNAL_SERVER_ERROR);
        }

    }

    //상품 삭제
    @DeleteMapping("/{itemId}")
    @Operation(summary = "상품 삭제", description = "해당 상품을 삭제합니다.")
    public ApiResponse<?> deleteItem(
            @PathVariable Long itemId
    ){
        log.info("[STEP 1] 상품 삭제 요청 수신..");
        try{
            itemService.deleteItem(itemId);
            log.info("[STEP 2] ");
            return ApiResponse.onSuccess(SuccessCode.OK,null);

        }catch (GeneralException e) {
            log.error("❌ [ERROR] 개별 주문 조회 중 예외 발생: {}", e.getReason().getMessage());
            throw e;
        }
        catch (Exception e){
            log.error("❌ [ERROR] 알 수 없는 예외 발생: {}", e.getMessage());
            throw new GeneralException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

}

