package likelion13th.codashop.DTO.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class S3Item {
    private String itemName;
    private String url;

    public S3Item(String itemName, String url) {
        this.itemName = itemName;
        this.url = url;
    }
}
