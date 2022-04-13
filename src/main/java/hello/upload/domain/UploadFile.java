package hello.upload.domain;

import lombok.Data;

@Data
public class UploadFile {
    private String uploadFileName;
    private String storeFileName; // 파일이 덮어씌워지지 않도록 내부에서 관리하는 별도의 이름 필요

    public UploadFile(String uploadFileName, String storeFileName) {
        this.uploadFileName = uploadFileName;
        this.storeFileName = storeFileName;
    }
}
