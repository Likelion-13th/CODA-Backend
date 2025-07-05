package likelion13th.codashop.service;

import jakarta.transaction.Transactional;
import likelion13th.codashop.DTO.request.CategoryCreateRequest;
import likelion13th.codashop.DTO.response.ItemResponseDto;
import likelion13th.codashop.domain.Category;
import likelion13th.codashop.DTO.response.CategoryResponse;
import likelion13th.codashop.global.api.ErrorCode;
import likelion13th.codashop.global.exception.GeneralException;
import likelion13th.codashop.repository.CategoryRepository;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    // 모든 카테고리 조회
    @Transactional
    public List<CategoryResponse> getAllCategories() {
        return categoryRepository.findAll().stream()
                .map(CategoryResponse::from)
                .collect(Collectors.toList());
    }

    //개별 카테고리 조회
    @Transactional
    public CategoryResponse getCategoryById(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .map(CategoryResponse::from)
                .orElseThrow(()-> new GeneralException(ErrorCode.CATEGORY_NOT_FOUND));
    }

    //카테고리 수정
    @Transactional
    public CategoryResponse categoryFix(CategoryCreateRequest request,Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(()-> new GeneralException(ErrorCode.CATEGORY_NOT_FOUND));
        category.updateCategory(request.getCategoryName());
        return CategoryResponse.from(category);
    }

    //카테고리 추가
    @Transactional
    public CategoryResponse categoryCreate(CategoryCreateRequest request){
        Category category=new Category(request.getCategoryName());
        categoryRepository.save(category);
        return CategoryResponse.from(category);
    }

    //카테고리 삭제
    @Transactional
    public void deleteCategory(Long categoryId) {
        if(!categoryRepository.existsById(categoryId)) {
            throw new GeneralException(ErrorCode.CATEGORY_NOT_FOUND);
        }
        categoryRepository.deleteById(categoryId);
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
}
