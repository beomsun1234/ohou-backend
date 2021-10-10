package com.manduljo.ohou.mongo.controller;

import com.manduljo.ohou.mongo.constant.AcceptType;
import com.manduljo.ohou.mongo.domain.MUser;
import com.manduljo.ohou.mongo.service.MUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/api/users", produces = AcceptType.API_V1)
public class MUserController {

  private final MUserService mUserService;

  @GetMapping
  public ResponseEntity<List<MUser>> findAll() {
    return ResponseEntity.ok(mUserService.findAll());
  }

  @GetMapping("/{id}")
  public ResponseEntity<MUser> findById(@PathVariable String id) {
    return ResponseEntity.ok(mUserService.findById(id));
  }

}
