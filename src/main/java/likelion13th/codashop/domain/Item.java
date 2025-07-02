package likelion13th.codashop.domain;

import jakarta.persistence.*;
import likelion13th.codashop.domain.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name ="item")
@NoArgsConstructor
@AllArgsConstructor

public class Item extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.PRIVATE)
    private Long id;

    @Column (nullable = false)
    private String item_name;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private String imagePath;

    @Column(nullable = false)
    private String brand;

    @Column(nullable = false)
    @Setter
    private boolean inNew=false;

    //카테고리와 연관관계 설정
    @ManyToMany
    private List<Category> categories = new ArrayList<>();

    //order 와 연관관계 설정
    @OneToMany(mappedBy = "item",cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Order> orders = new ArrayList<>();

    public Item(String item_name, int price , String thumbnail_img, String brand, boolean isNew ){
        this.item_name = item_name;
        this.price = price;
        this.imagePath = thumbnail_img;
        this.brand = brand;
        this.inNew = isNew;
    }
}
