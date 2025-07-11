
package likelion13th.codashop.controller;

import io.swagger.v3.oas.annotations.Operation;
import likelion13th.codashop.DTO.request.AdressRequest;
import likelion13th.codashop.DTO.response.AddressResponse;
import likelion13th.codashop.domain.User;
import likelion13th.codashop.domain.Address;
import likelion13th.codashop.global.api.ApiResponse;
import likelion13th.codashop.global.api.ErrorCode;
import likelion13th.codashop.global.api.SuccessCode;
import likelion13th.codashop.global.exception.GeneralException;
import likelion13th.codashop.login.auth.jwt.CustomUserDetails;
import likelion13th.codashop.login.service.UserService;
import likelion13th.codashop.service.UserAddressService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/users/me/address")
@RequiredArgsConstructor
public class UserAddressController {
    private final UserAddressService userAddressService;
    private final UserService userService;

    //내 주소 조회
    @GetMapping
    @Operation(summary = "내 주소 조회",description = "내 주소를 조회합니다.")
    public ApiResponse<?> getAddress(
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ){
        User user = userService.findByProviderId(customUserDetails.getProviderId())
                .orElseThrow(() -> new GeneralException(ErrorCode.USER_NOT_FOUND));
        log.info("[STEP 1] 내 주소 조회 요청 수신 ..");
        try{
            AddressResponse myAddress=userAddressService.getAddress(user);
            log.info("[STEP 2] 내 주소 조회 성공");
            return ApiResponse.onSuccess(SuccessCode.ADDRESS_GET_SUCCESS,myAddress);
        }
        catch (GeneralException e) {
            log.error("❌ [ERROR] 내 주소 조회 중 예외 발생: {}", e.getReason().getMessage());
            throw e;
        }
        catch (Exception e){
            log.error("❌ [ERROR] 알 수 없는 예외 발생: {}", e.getMessage());
            throw new GeneralException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    //내 주소 수정
    @PutMapping
    @Operation(summary = "내 주소 수정", description = "내 주소를 수정 합니다.")
    public ApiResponse<?> updateAddress(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestBody AdressRequest request
    ){
        User user = userService.findByProviderId(customUserDetails.getProviderId())
                .orElseThrow(() -> new GeneralException(ErrorCode.USER_NOT_FOUND));
        log.info("[STEP 1] 내 주소 수정 요청 수신 ..");
        try{
            AddressResponse myAddress=userAddressService.fixAdress(user,request);
            log.info("[STEP 2] 내 주소 수정 성공");
            return ApiResponse.onSuccess(SuccessCode.ADDRESS_SAVE_SUCCESS,myAddress);
        }
        catch (GeneralException e) {
            log.error("❌ [ERROR] 내 주소 수정 중 예외 발생: {}", e.getReason().getMessage());
            throw e;
        }
        catch (Exception e){
            log.error("❌ [ERROR] 알 수 없는 예외 발생: {}", e.getMessage());
            throw new GeneralException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

}
