package likelion13th.codashop.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@AllArgsConstructor
@Getter
public class Address {

    @Column(nullable = false)
    private String zipcode;

    @Column(nullable = false)
    private String address; //

    @Column(nullable = false)
    private String addressDetail; //

    public Address() {
        this.zipcode = "10540";
        this.address = "경기도 고양시 덕양구 항공대학로 76";
        this.addressDetail = "한국항공대학교";
    }
}
//embedd 타입으로 유저 객체 안에 addr관련 컬럼을 address객체로 관리하도록함
//생성자를 통해 기본 default 값으로 해당 주소를 넣어 관리