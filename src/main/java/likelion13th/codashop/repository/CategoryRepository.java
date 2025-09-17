package likelion13th.codashop.repository;

import org.springframework.stereotype.Repository;
import likelion13th.codashop.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    //카테고리 id 기반 카테고리 탐색
    Optional<Category> findById(Long categoryId);
    //카테고리 이름 기반 카테고리 탐색
    Optional<Category> findByCategoryName(String categoryName);
}
//이름 검색기능 추가