package likelion13th.codashop.controller;

import io.swagger.v3.oas.annotations.Operation;
import likelion13th.codashop.DTO.response.UserInfoResponse;
import likelion13th.codashop.DTO.request.UserInfoFixRequest;
import likelion13th.codashop.DTO.response.UserMilegeResponse;
import likelion13th.codashop.domain.User;
import likelion13th.codashop.service.UserInfoService;
import likelion13th.codashop.login.service.UserService;
import likelion13th.codashop.login.auth.jwt.CustomUserDetails;
import likelion13th.codashop.global.api.ApiResponse;
import likelion13th.codashop.global.api.ErrorCode;
import likelion13th.codashop.global.api.SuccessCode;
import likelion13th.codashop.global.exception.GeneralException;
import likelion13th.codashop.service.UserInfoService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/user/me")
@RequiredArgsConstructor
public class UserInfoController {
    private final UserService userService;
    private final UserInfoService userInfoService;

    //내 정보 조회
    @GetMapping
    @Operation(summary = "내 정보 조회", description = "내 정보를 조회합니다.")
    public ApiResponse<?> getUserInfo(
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ){
        log.info("[STEP 1] 내 정보 조회 요청 수신");
        try{
            User user= userService.findByProviderId(customUserDetails.getProviderId())
                    .orElseThrow(()-> new GeneralException(ErrorCode.USER_NOT_FOUND));

            UserInfoResponse userInfoResponse = userInfoService.getUserInfo(user);
            log.info("[STEP 2] 내 정보 조회 성공");
            return ApiResponse.onSuccess(SuccessCode.USER_INFO_GET_SUCCESS, userInfoResponse);
        }
        catch (GeneralException e) {
            log.error("❌ [ERROR] 내정보 조회 중 예외 발생: {}", e.getReason().getMessage());
            throw e;
        }
        catch (Exception e){
            log.error("❌ [ERROR] 알 수 없는 예외 발생: {}", e.getMessage());
            throw new GeneralException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    // 내 정보 수정
    @PutMapping
    @Operation(summary = "내 정보 수정",description = "내 정보를 수정합니다.")
    public ApiResponse<?> updateUserInfo(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestBody UserInfoFixRequest request
    ){

        log.info("[STEP 1] 내 정보 수정 요청 수신..");
        try{
            User user= userService.findByProviderId(customUserDetails.getProviderId())
                    .orElseThrow(()-> new GeneralException(ErrorCode.USER_NOT_FOUND));
            UserInfoResponse fixedUser=userInfoService.fixUserInfo(user,request);
            log.info("[STEP 2] 내 정보 수정 성공 ..");
            return ApiResponse.onSuccess(SuccessCode.OK,fixedUser);
        }
        catch (GeneralException e) {
            log.error("❌ [ERROR] 내 정보 수정 중 예외 발생: {}", e.getReason().getMessage());
            throw e;
        }
        catch (Exception e){
            log.error("❌ [ERROR] 알 수 없는 예외 발생: {}", e.getMessage());
            throw new GeneralException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    //내 마일리지 조회
    @GetMapping("/mileage")
    @Operation(summary = "내 마일리지 조회",description = "현제 나의 마일리지를 조회합니다.")
    public ApiResponse<?> getUserMileage(
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ){

        log.info("[STEP 1] 내 마일리지 조회 요청 수신..");
        try{
            User user= userService.findByProviderId(customUserDetails.getProviderId())
                    .orElseThrow(()-> new GeneralException(ErrorCode.USER_NOT_FOUND));
            UserMilegeResponse myMilage=userInfoService.getUserMileges(user);
            log.info("[STEP 2]  내 마일리지 조회 성공 ..");
            return ApiResponse.onSuccess(SuccessCode.OK,myMilage);
        }
        catch (GeneralException e) {
            log.error("❌ [ERROR] 내 마일리지 조회 중 예외 발생: {}", e.getReason().getMessage());
            throw e;
        }
        catch (Exception e){
            log.error("❌ [ERROR] 알 수 없는 예외 발생: {}", e.getMessage());
            throw new GeneralException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

}

// getmapping request 매핑을 통해 api 호출
// operation 을 통해 swagger 문서화