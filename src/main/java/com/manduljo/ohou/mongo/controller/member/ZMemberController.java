package com.manduljo.ohou.mongo.controller.member;

import com.manduljo.ohou.mongo.constant.AcceptType;
import com.manduljo.ohou.mongo.domain.member.ZMember;
import com.manduljo.ohou.mongo.service.member.ZMemberCommand;
import com.manduljo.ohou.mongo.service.member.ZMemberCriteria;
import com.manduljo.ohou.mongo.service.member.ZMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/mongo-api/members", produces = AcceptType.API_V1)
public class ZMemberController {

  private final ZMemberService userService;

  @GetMapping
  public ResponseEntity<List<ZMember>> findAll(@RequestBody(required = false) ZMemberDto.FindRequest request) {
    return ResponseEntity.ok(userService.findAll(convertCriteria(request)));
  }

  private ZMemberCriteria.FindRequest convertCriteria(ZMemberDto.FindRequest request) {
    return request == null ? new ZMemberCriteria.FindRequest() : ZMemberCriteria.FindRequest.builder()
        .name(request.getName())
        .build();
  }

  @GetMapping("/{id}")
  public ResponseEntity<ZMember> findById(@PathVariable String id) {
    return ResponseEntity.ok(userService.findById(id));
  }

  @PostMapping
  public ResponseEntity<Void> save(@RequestBody ZMemberDto.SaveRequest request) {
    String id = userService.save(
        ZMemberCommand.SaveRequest.builder()
            .name(request.getName())
            .email(request.getEmail())
            .build()
    );
    return ResponseEntity.created(URI.create("/api/users/" + id)).build();
  }

}
