package com.manduljo.ohou.mongo.controller;

import com.manduljo.ohou.mongo.constant.AcceptType;
import com.manduljo.ohou.mongo.domain.muser.MUser;
import com.manduljo.ohou.mongo.service.MUserCommand;
import com.manduljo.ohou.mongo.service.MUserCriteria;
import com.manduljo.ohou.mongo.service.MUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/api/users", produces = AcceptType.API_V1)
public class MUserController {

  private final MUserService mUserService;

  @GetMapping
  public ResponseEntity<List<MUser>> findAll(@RequestBody(required = false) MUserDto.FindRequest request) {
    return ResponseEntity.ok(mUserService.findAll(convertCriteria(request)));
  }

  private MUserCriteria.FindRequest convertCriteria(MUserDto.FindRequest request) {
    return request == null ? new MUserCriteria.FindRequest() : MUserCriteria.FindRequest.builder()
        .name(request.getName())
        .build();
  }

  @GetMapping("/{id}")
  public ResponseEntity<MUser> findById(@PathVariable String id) {
    return ResponseEntity.ok(mUserService.findById(id));
  }

  @PostMapping
  public ResponseEntity<Void> save(@RequestBody MUserDto.SaveRequest request) {
    String id = mUserService.save(
        MUserCommand.SaveRequest.builder()
            .name(request.getName())
            .age(request.getAge())
            .build()
    );
    return ResponseEntity.created(URI.create("/api/users/" + id)).build();
  }

}
