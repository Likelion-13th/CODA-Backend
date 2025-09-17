package likelion13th.codashop.DTO.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
public class AddressRequest {
    private String zipcode;
    private String address;
    private String addressDetail;
}
//NoArge를 통한 기본 생성자 생성
// requestbody가 나중에 값을 주입 getter 를 통해 req내용물 꺼내올 수 있도록 작성