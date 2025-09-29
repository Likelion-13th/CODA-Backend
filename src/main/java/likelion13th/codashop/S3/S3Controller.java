package likelion13th.codashop.S3;

import io.swagger.v3.oas.annotations.Operation;
import likelion13th.codashop.global.api.ApiResponse;
import likelion13th.codashop.global.api.ErrorCode;
import likelion13th.codashop.global.api.SuccessCode;
import likelion13th.codashop.global.exception.GeneralException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/s3")
public class S3Controller {

    final S3Service s3Service;


    @PostMapping(value="/upload",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "s3 업로드",description = "aws s3 이미지 업로드 후 url 반환 multipart 타입으로 업로드")
    public ApiResponse<?> uploadFile(@RequestPart("photo") MultipartFile[] files) {
        List<String> urls= new ArrayList<>();
        for(MultipartFile file : files) {
            if (file.isEmpty()) throw new GeneralException(ErrorCode.S3_FILE_EMPTY);
            String contentType = file.getContentType();
            if(contentType==null || !contentType.startsWith("image/")) throw new GeneralException(ErrorCode.S3_INVALID_FILE_TYPE);

            String fileUrl= Optional.ofNullable(s3Service.uploadFile(file))
                    .orElseThrow(()->new GeneralException(ErrorCode.S3_UPLOAD_FAILED));
            urls.add(fileUrl);
        }

        return ApiResponse.onSuccess(SuccessCode.S3_UPLOAD_SUCCESS, urls);
    }

}
