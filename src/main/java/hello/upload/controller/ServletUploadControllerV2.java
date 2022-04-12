package hello.upload.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Collection;

@Slf4j
@Controller
@RequestMapping("/servlet/v2")
public class ServletUploadControllerV2 {

    //lombok거 말고 spring거 써야함. application.properties에 있는 속성들 가져올 수 있음
    @Value("${file.dir}")
    private String fileDir;

    @GetMapping("/upload")
    public String newFile() {
        return "upload-form";
    }

    @PostMapping("/upload")
    public String saveFileV1(HttpServletRequest request) throws IOException, ServletException {
        log.info("request={}", request);

        String itemName = request.getParameter("itemName");
        log.info("itemName= {}", itemName);

        Collection<Part> parts = request.getParts();
        log.info("parts = {}", parts);

        for (Part part : parts) {
            log.info("===== PART =====");
            log.info("name={}", part.getName());
            Collection<String> headerNames = part.getHeaderNames();
            for (String headerName : headerNames) {
                log.info("header {} : {}", headerName, part.getHeader(headerName));

            }
            //편의메서드
            log.info("submitted Filename={}", part.getSubmittedFileName()); // 내부에서는 content-disposition .파싱해서 filename에서 찾고....
            log.info("size={}", part.getSize());

            //Read data
            InputStream inputStream = part.getInputStream();
            String body = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);// 바이너리를 문자로 바꿀 때는 꼭 캐릭터셋 정해줘야
            log.info("body {}" ,body);

            //save file
            if (StringUtils.hasText(part.getSubmittedFileName())) {

                String fullPath = fileDir + part.getSubmittedFileName();
                log.info("save fullPath = {}", fullPath);
                part.write(fullPath);
            }
        }

        return "upload-form";
    }
}
