package hello.upload.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Collection;

@Slf4j
@Controller
@RequestMapping("/spring")
public class SpringUploadController {

    //lombok거 말고 spring거 써야함. application.properties에 있는 속성들 가져올 수 있음
    @Value("${file.dir}")
    private String fileDir;

    @GetMapping("/upload")
    public String newFile() {
        return "upload-form";
    }

    @PostMapping("/upload")
    public String saveFile(@RequestParam String itemName, @RequestParam MultipartFile file, HttpServletRequest request) throws IOException { //request는 없어도 되는데 로그용
        log.info("request = {}", request);
        log.info("itemName = {}", itemName);
        log.info("multiPart = {}", file);

        if (!file.isEmpty()) {
            String fullPath = fileDir + file.getOriginalFilename();
            log.info(StringUtils.getFilenameExtension(fullPath));
            log.info(StringUtils.getFilename(fullPath));
            log.info(StringUtils.stripFilenameExtension(fullPath));

            log.info("fullPath = {}", fullPath);
            File file1 = new File(fullPath);

            log.info(file1.getName());

            file.transferTo(file1);
        }
        return "upload-form";

    }
}
