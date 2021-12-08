package com.manduljo.ohou.mongo.controller.member;

import com.manduljo.ohou.mongo.constant.AcceptType;
import com.manduljo.ohou.mongo.service.member.ZMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/mongo-api/members", produces = AcceptType.API_V1)
public class ZMemberController {

  private final ZMemberService userService;

}
