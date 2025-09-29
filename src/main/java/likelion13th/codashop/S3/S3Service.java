package likelion13th.codashop.S3;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import likelion13th.codashop.DTO.response.S3Item;

import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class S3Service {

    private final AmazonS3 amazonS3;
    private final S3Properties s3Properties;

    public String uploadFile(MultipartFile file) {
        String bucketName= s3Properties.getBucket();
        String fileName= UUID.randomUUID()+"_"+file.getOriginalFilename();
        try{
            ObjectMetadata metadata= new ObjectMetadata();
            metadata.setContentType(file.getContentType());
            metadata.setContentLength(file.getSize());
            amazonS3.putObject(bucketName,fileName,file.getInputStream(),metadata);
            return amazonS3.getUrl(bucketName,fileName).toString();
        }catch (IOException | AmazonS3Exception e) {
            throw new RuntimeException("s3 upload failed", e);
        }
    }
    public void deleteFile(String fileName) {
        String bucketName= s3Properties.getBucket();
        try{
            amazonS3.deleteObject(bucketName,fileName);
        }catch(AmazonS3Exception e){
            throw new RuntimeException("s3 delete failed",e);
        }
    }
    public String getUrl(String fileName){
        String bucketName= s3Properties.getBucket();
        try{
            return amazonS3.getUrl(bucketName,fileName).toString();
        }catch (AmazonS3Exception e){
            throw new RuntimeException("s3 url failed",e);
        }
    }
    public S3Item uploadFileForItem(MultipartFile file) {
        String bucketName= s3Properties.getBucket();
        String fileName= UUID.randomUUID()+"_"+file.getOriginalFilename();
        try{
            ObjectMetadata metadata= new ObjectMetadata();
            metadata.setContentType(file.getContentType());
            metadata.setContentLength(file.getSize());
            amazonS3.putObject(bucketName,fileName,file.getInputStream(),metadata);
            String url=amazonS3.getUrl(bucketName, fileName).toString();
            return new S3Item(fileName,url);
        }catch (IOException | AmazonS3Exception e) {
            throw new RuntimeException("s3 upload failed",e);
        }
    }
}
