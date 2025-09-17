package likelion13th.codashop.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import likelion13th.codashop.DTO.request.CategoryCreateRequest;
import likelion13th.codashop.DTO.response.CategoryResponse;
import likelion13th.codashop.DTO.response.ItemResponseDto;
import likelion13th.codashop.domain.Category;
import likelion13th.codashop.domain.Item;
import likelion13th.codashop.global.api.ApiResponse;
import likelion13th.codashop.global.api.ErrorCode;
import likelion13th.codashop.global.api.SuccessCode;
import likelion13th.codashop.global.exception.GeneralException;
import likelion13th.codashop.login.auth.jwt.CustomUserDetails;
import likelion13th.codashop.login.service.UserService;
import likelion13th.codashop.service.CategoryService;


import likelion13th.codashop.service.ItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
@Tag(name ="카테고리",description = "카테고리 관련 API 입니다.")
public class CategoryController {
    private final CategoryService categoryService;
    private final ItemService itemService;

    //모든 카테고리 조회
    @GetMapping
    @Operation(summary = "모든 카테고리 조회", description = "모든 카테고리를 조회합니다.")
    public ApiResponse<?> getCategories(){
        List<CategoryResponse> categories=categoryService.getAllCategories();
        if(categories.isEmpty()){
            return ApiResponse.onSuccess(SuccessCode.CATEGORY_ITEMS_EMPTY,Collections.emptyList());
        }
        return ApiResponse.onSuccess(SuccessCode.CATEGORY_ITEMS_GET_SUCCESS,categories);
    }

    //카테고리 생성
    @PostMapping
    @Operation(summary = "카테고리 생성", description = "카테고리를 생성합니다.")
    @PreAuthorize("hasRole('ADMIN')")// 권한 설정
    public ApiResponse<?> addCategory(
            @RequestBody CategoryCreateRequest categoryCreateRequest){
        log.info("[STEP 1] 카테고리 생성 요청 수신..");
        try{
            CategoryResponse newCategory=categoryService.categoryCreate(categoryCreateRequest);
            log.info("[STEP 2] 카테고리 생성 ..");
            return ApiResponse.onSuccess(SuccessCode.OK,newCategory);
        }catch(GeneralException e){
            log.error("< ❌[ERROR] 카테고리 생성 중 예외 발생 {}",e.getReason().getMessage());
            throw e;
        }
        catch (Exception e){
            log.error("❌ [ERROR] 알 수 없는 예외 발생: {}", e.getMessage());
            throw new GeneralException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    //개별 카테고리 조회
    @GetMapping("/{categoryId}")
    @Operation(summary = "개별 카테고리 조회",description = "해당 카테고리를 조회합니다.")
    public ApiResponse<?> getCategory(@PathVariable Long categoryId){
        log.info("[STEP 1] 개별 카테고리 조회 요청 수신..");
        try{
            CategoryResponse category=categoryService.getCategoryById(categoryId);
            log.info("[STEP 2] 개별 카테고리 정보 조회 성공");
            return ApiResponse.onSuccess(SuccessCode.OK,category);
        }
        catch (GeneralException e) {
            log.error("❌ [ERROR] 개별 카테고리 조회 중 예외 발생: {}", e.getReason().getMessage());
            throw e;
        }
        catch (Exception e){
            log.error("❌ [ERROR] 알 수 없는 예외 발생: {}", e.getMessage());
            throw new GeneralException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    //카테고리 수정 요청
    @PutMapping("/{categoryId}")
    @Operation(summary = "카테고리 수정",description = "카테고리를 수정합니다.")
    public ApiResponse<?> updateCategory(
            @PathVariable Long categoryId,
            @RequestBody CategoryCreateRequest request
    ){
        log.info("[STEP 1] 카테고리 수정 요청 수신..");
        try{
            CategoryResponse fixCategory=categoryService.categoryFix(request,categoryId);
            log.info("[STEP 2] 카테고리 수정 성공");
            return ApiResponse.onSuccess(SuccessCode.OK,fixCategory);
        }
        catch (GeneralException e) {
            log.error("❌ [ERROR] 카테고리 수정 중 예외 발생: {}", e.getReason().getMessage());
            throw e;
        }
        catch (Exception e){
            log.error("❌ [ERROR] 알 수 없는 예외 발생: {}", e.getMessage());
            throw new GeneralException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    //카테고리 삭제
    @DeleteMapping("/{categoryId}")
    @Operation(summary = "카테고리 삭제",description = "해당 카테고리를 삭제합니다.")
    public ApiResponse<?> deleteCategory(@PathVariable Long categoryId){
        log.info("[STEP 1] 카테고리 삭제 요청 수신..");
        try{
            itemService.deleteItem(categoryId);
            log.info("[STEP 2] 카테고리 삭제 성공");
            return ApiResponse.onSuccess(SuccessCode.OK,null);
        }
        catch (GeneralException e) {
            log.error("❌ [ERROR] 카테고리 삭제 중 예외 발생: {}", e.getReason().getMessage());
            throw e;
        }
        catch (Exception e){
            log.error("❌ [ERROR] 알 수 없는 예외 발생: {}", e.getMessage());
            throw new GeneralException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    //카테고리별 상품 조회
    @GetMapping("/{categoryId}/items")
    @Operation(summary = "카테고리별 상품 조회",
            description = "해당 카테고리에 속해있는 모든 아이템을 조회합니다.")
    public ApiResponse<?> getItemsByCategory(@PathVariable Long categoryId){
        log.info("[STEP 1] 카테고리 상품 조회 요청 수신..");
        List<ItemResponseDto> categoryItems=categoryService.getItemsByCategory(categoryId);
        if(categoryItems.isEmpty()){
            return ApiResponse.onSuccess(SuccessCode.CATEGORY_ITEMS_EMPTY,Collections.emptyList());
        }
        return ApiResponse.onSuccess(SuccessCode.CATEGORY_ITEMS_GET_SUCCESS,categoryItems);
    }

}
//order 컨트롤러를 참고하여 order서비스 계층의 기능을 활용하여 CRUD기능을 구현
// try-catch를 통해 성공과 실패를 분리하여 예외를 처리 dto형태로 도출된 결과에 결과 코드를 붙여 api응답 형태로 반환