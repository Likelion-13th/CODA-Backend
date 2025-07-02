package likelion13th.codashop.DTO.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AdressRequest {
    private String zipCode;
    private String address;
    private String addressDetail;
}
