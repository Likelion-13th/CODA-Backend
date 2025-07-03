package likelion13th.codashop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import likelion13th.codashop.DTO.response.UserMilegeResponse;
import likelion13th.codashop.DTO.response.UserInfoResponse;
import likelion13th.codashop.DTO.request.UserInfoFixRequest;
import likelion13th.codashop.domain.User;
import likelion13th.codashop.repository.UserRepository;
import likelion13th.codashop.global.api.ErrorCode;
import likelion13th.codashop.global.exception.GeneralException;



@Service
@RequiredArgsConstructor
public class UserInfoService {
    private final UserRepository userRepository;

    //내정보 조회
    @Transactional
    public UserInfoResponse getUserInfo(User user) {
        return UserInfoResponse.from(user);
    }
    //내정보 수정
    @Transactional
    public UserInfoResponse fixUserInfo(User user, UserInfoFixRequest request) {
        user.setUsernickname(request.getUsernickname());
        user.setPhoneNumber(request.getPhonenumber());
        return UserInfoResponse.from(user);
    }
    //내 마일리지 조회
    @Transactional
    public UserMilegeResponse getUserMileges(User user) {
        return UserMilegeResponse.from(user);
    }

}
