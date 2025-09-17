package likelion13th.codashop.service;

import jakarta.transaction.Transactional;
import likelion13th.codashop.DTO.request.AddressRequest;
import likelion13th.codashop.DTO.response.AddressResponse;
import likelion13th.codashop.domain.User;
import likelion13th.codashop.domain.Address;
import likelion13th.codashop.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserAddressService {
    private final UserRepository userRepository;

    //내 주소 조회
    @Transactional
    public AddressResponse getAddress(User user) {
        return AddressResponse.from(user.getAddress());
    }
    //내 주소 수정
    @Transactional
    public AddressResponse fixAdress(User user, AddressRequest request) {
        Address address = new Address(
                request.getZipcode(),
                request.getAddress(),
                request.getAddressDetail()

        );
        user.updateAddress(address);
        return AddressResponse.from(user.getAddress());
    }

}
//final을 통해 레포 객체 생성이 여러번 되지 않고 하나의 객체로 사용할 수 있게 작성
