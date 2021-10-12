package com.manduljo.ohou.mongo.controller.member;

import com.manduljo.ohou.mongo.constant.AcceptType;
import com.manduljo.ohou.mongo.domain.member.MMember;
import com.manduljo.ohou.mongo.service.member.MMemberCommand;
import com.manduljo.ohou.mongo.service.member.MMemberCriteria;
import com.manduljo.ohou.mongo.service.member.MMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/members", produces = AcceptType.API_V1)
public class MMemberController {

  private final MMemberService userService;

  @GetMapping
  public ResponseEntity<List<MMember>> findAll(@RequestBody(required = false) MMemberDto.FindRequest request) {
    return ResponseEntity.ok(userService.findAll(convertCriteria(request)));
  }

  private MMemberCriteria.FindRequest convertCriteria(MMemberDto.FindRequest request) {
    return request == null ? new MMemberCriteria.FindRequest() : MMemberCriteria.FindRequest.builder()
        .name(request.getName())
        .build();
  }

  @GetMapping("/{id}")
  public ResponseEntity<MMember> findById(@PathVariable String id) {
    return ResponseEntity.ok(userService.findById(id));
  }

  @PostMapping
  public ResponseEntity<Void> save(@RequestBody MMemberDto.SaveRequest request) {
    String id = userService.save(
        MMemberCommand.SaveRequest.builder()
            .name(request.getName())
            .email(request.getEmail())
            .build()
    );
    return ResponseEntity.created(URI.create("/api/users/" + id)).build();
  }

}
