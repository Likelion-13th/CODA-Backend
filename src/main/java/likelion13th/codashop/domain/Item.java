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

public class Item extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.PRIVATE)
    private Long id;

    @Column (nullable = false)
    private String itemname;

    @Column(nullable = false)
    private int price;

    @Column(nullable = true)
    private String imagePath;

    @Column(nullable = false)
    private String brand;

    @Column(nullable = false)
    @Setter
    private boolean isNew=false;

    @Column(nullable = true)
    private String s3ImgKey;

    //카테고리와 연관관계 설정
    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<>();

    //order 와 연관관계 설정
    /*
    @OneToMany(mappedBy = "item",cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Order> orders = new ArrayList<>();
    */
    public Item(String itemname, int price, String thumbnail_img,String imgKey, String brand, boolean isNew ){
        this.itemname = itemname;
        this.price = price;
        this.imagePath = thumbnail_img;
        this.s3ImgKey=imgKey;
        this.brand = brand;
        this.isNew = isNew;
    }
}
// one to many를 활용하여 카테고라와 매핑 1대 다 관계 설정
// base entity를 활용하여 upload at update at 컬럼 추가