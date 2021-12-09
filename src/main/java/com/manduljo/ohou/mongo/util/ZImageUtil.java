package com.manduljo.ohou.mongo.util;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Component
@Slf4j
@RequiredArgsConstructor
public class ZImageUtil {

  @Value("${resources.location}")
  private String resources_location;

  public String generateImagePath(String id, MultipartFile multipartFile) {
    if (multipartFile == null) {
      return null;
    }
    if (multipartFile.getContentType() == null || !isValidContentType(multipartFile.getContentType())) {
      throw new RuntimeException("잘못된 컨텐츠입니다.");
    }

    File file = new File(resources_location + "member_profile/" + id);
    file.mkdirs();

    String extension = assignmentExtensionType(multipartFile.getContentType());
    String uuid = UUID.randomUUID().toString();
    String imageName = uuid + extension;
    file = new File(file.getPath() + "/" + imageName);

    try {
      multipartFile.transferTo(file);
    } catch (IOException e) {
      throw new RuntimeException("이미지 파일 쓰기에 실패하였습니다.");
    }

    log.info("file={}", file.getAbsolutePath());

    return "member_profile/" + id + "/" + imageName;
  }

  public Boolean isValidContentType(String contentType) {
    return contentType.contains("image/png") || contentType.contains("image/jpeg");
  }

  public String assignmentExtensionType(String contentType) {
    if (contentType.contains("image/png")) {
      return ".png";
    }
    return ".jpg";
  }

}
