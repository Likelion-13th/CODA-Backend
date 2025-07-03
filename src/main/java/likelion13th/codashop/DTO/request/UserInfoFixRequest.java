package likelion13th.codashop.DTO.request;

import lombok.Setter;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
public class UserInfoFixRequest {
    private String usernickname;
    private String phonenumber;
}
