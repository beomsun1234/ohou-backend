package com.manduljo.ohou.util;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

@Component
@Slf4j
@RequiredArgsConstructor
public class ImageUtil {

    @Value("${resources.location}")
    private String resources_location;

    public String genreateImagePath(String email, MultipartFile multipartFile) throws IOException {
        String extension = assignmentExtensionType(Objects.requireNonNull(multipartFile.getContentType()));
        String uuid = UUID.randomUUID().toString();
        File file = new File(resources_location+email);
        if(!file.exists()){
            // mkdir() 함수와 다른 점은 상위 디렉토리가 존재하지 않을 때 그것까지 생성
            file.mkdirs();
        }
        String imageName = uuid+extension;
        file = new File( resources_location+email + "/" + imageName);
        multipartFile.transferTo(file);
        log.info("file={}", file.getAbsolutePath());
        return email + "/" + imageName;
    }

    public String assignmentExtensionType(String contentType){
        if(contentType.contains("image/png")){
            return ".png";
        }
        return ".jpg";
    }

    public Boolean checkContentType(String contentType){
        if(contentType.contains("image/jpeg")||contentType.contains("image/png")){
            return true;
        }
        return false;
    }

}
