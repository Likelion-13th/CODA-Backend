package likelion13th.codashop.service;

import jakarta.transaction.Transactional;
import likelion13th.codashop.DTO.request.AdressRequest;
import likelion13th.codashop.DTO.response.AddressResponse;
import likelion13th.codashop.domain.User;
import likelion13th.codashop.domain.Address;
import likelion13th.codashop.repository.UserRepository;
import likelion13th.codashop.global.api.ErrorCode;
import likelion13th.codashop.global.exception.GeneralException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserAddressService {
    private final UserRepository userRepository;

    //내 주소 조회
    public AddressResponse getAddress(User user) {
        return AddressResponse.from(user.getAddress());
    }
    //내 주소 수정
    public AddressResponse fixAdress(User user, AdressRequest request) {
        Address address = new Address(
                request.getZipCode(),
                request.getAddress(),
                request.getAddressDetail()

        );
        user.updateAddress(address);
        return AddressResponse.from(user.getAddress());
    }

}
