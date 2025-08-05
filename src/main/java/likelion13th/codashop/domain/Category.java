package likelion13th.codashop.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import likelion13th.codashop.domain.entity.BaseEntity;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Entity
@Getter
@Table(name="categorys")
@NoArgsConstructor

public class Category extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="category_id")
    @Setter(AccessLevel.PRIVATE)
    private Long id;

    @Column(nullable = false)
    private String categoryName;

    //아이템과의 관계설정
    @ManyToMany
    private List<Item> items= new ArrayList<>();

    //생성자
    public Category(String categoryName) {
        this.categoryName = categoryName;
    }
    //카테고명 업데이트
    public void updateCategory(String categoryName){
        this.categoryName = categoryName;
    }

}
//many to many 를 활용한 다대다 매핑
//setter private를 통해 외부에서 접근 못하도록 하여 pk 값 무결성 보장