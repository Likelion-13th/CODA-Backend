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
@AllArgsConstructor
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

}
