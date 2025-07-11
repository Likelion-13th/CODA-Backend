package likelion13th.codashop.domain;

import jakarta.persistence.*;
import likelion13th.codashop.domain.entity.BaseEntity;
import likelion13th.codashop.login.auth.jwt.RefreshToken;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    @Setter(AccessLevel.PRIVATE)
    private Long id;

    // 카카오 고유 ID
    @Column(nullable = false, unique = true)
    private String providerId;

    // 카카오 닉네임 (중복 허용)
    @Column(nullable = false)
    private String usernickname;

    // 휴대폰 번호 (선택 사항, 기본값 null)
    @Column(nullable = true)
    private String phoneNumber;

    // 계정 삭제 가능 여부 (기본값 true)
    @Column(nullable = false)
    private boolean deletable = true;

    // 마일리지 (기본값 0, 비즈니스 메서드로만 관리)
    @Column(nullable = false)
    @Setter(AccessLevel.NONE)
    private int maxMileage = 0;

    // 최근 총 구매액 (기본값 0, 비즈니스 메서드로만 관리)
    @Column(nullable = false)
    @Setter(AccessLevel.NONE)
    private int recentTotal = 0;

    // Refresh Token 관계 설정 (1:1)
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private RefreshToken auth;

    // 주소 정보 (임베디드 타입)
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "zipcode", column = @Column(name = "zipcode", nullable = false)),
            @AttributeOverride(name = "address", column = @Column(name = "address", nullable = false)),
            @AttributeOverride(name = "addressDetail", column = @Column(name = "address_detail", nullable = false))
    })
    private Address address;

    // 주소 저장/수정 메서드 추가
    public void updateAddress(Address address) {
        this.address = address;
    }

    // 주문 정보 (1:N 관계)
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Order> orders = new ArrayList<>();

    // 주문 추가 메서드
    public void addOrder(Order order) {
        this.orders.add(order);
        order.setUser(this);
    }

    // 마일리지 사용
    public void useMileage(int mileage) {
        if (mileage < 0) {
            throw new IllegalArgumentException("사용할 마일리지는 0보다 커야 합니다.");
        }
        if (this.maxMileage < mileage) {
            throw new IllegalArgumentException("마일리지가 부족합니다.");
        }
        this.maxMileage -= mileage;
    }

    // 마일리지 적립
    public void addMileage(int mileage) {
        if (mileage < 0) {
            throw new IllegalArgumentException("적립할 마일리지는 0보다 커야 합니다.");
        }
        this.maxMileage += mileage;
    }

    // 총 결제 금액 업데이트
    public void updateRecentTotal(int amount) {
        int newTotal = this.recentTotal + amount;
        if (newTotal < 0) {
            throw new IllegalArgumentException("총 결제 금액은 음수가 될 수 없습니다.");
        }
        this.recentTotal = newTotal;
    }
}
