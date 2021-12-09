package com.manduljo.ohou.mongo.controller.member;

import com.manduljo.ohou.ApiCommonResponse;
import com.manduljo.ohou.mongo.constant.AcceptType;
import com.manduljo.ohou.mongo.service.member.ZMemberCommand;
import com.manduljo.ohou.mongo.service.member.ZMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/mongo-api", produces = AcceptType.API_V1)
public class ZMemberLegacyController {

  private final ZMemberService memberService;

  @PostMapping("/join")
  @ResponseStatus(HttpStatus.CREATED)
  public ApiCommonResponse<String> createMember(@RequestBody ZMemberDto.CreateMemberRequest request) {
    ZMemberCommand.CreateMemberCommand command = toCommand(request);
    ZMemberCommand.CreateMemberInfo info = memberService.createMember(command);
    ZMemberDto.CreateMemberResponse response = toResponse(info);
    return new ApiCommonResponse<>(String.valueOf(HttpStatus.OK.value()), "회원가입 성공", response.getId());
  }

  private ZMemberCommand.CreateMemberCommand toCommand(ZMemberDto.CreateMemberRequest request) {
    return ZMemberCommand.CreateMemberCommand.builder()
        .email(request.getEmail())
        .nickname(request.getNickname())
        .password(request.getPassword())
        .build();
  }

  private ZMemberDto.CreateMemberResponse toResponse(ZMemberCommand.CreateMemberInfo info) {
    return ZMemberDto.CreateMemberResponse.builder()
        .id(info.getId())
        .build();
  }

  @GetMapping("/member/{id}")
  public ApiCommonResponse<ZMemberDto.GetMemberDetailResponse> getMemberDetail(@PathVariable(name = "id") String memberId) {
    ZMemberCommand.GetMemberDetailInfo info = memberService.getMemberDetail(memberId);
    ZMemberDto.GetMemberDetailResponse response = toResponse(info);
    return new ApiCommonResponse<>(String.valueOf(HttpStatus.OK.value()), "멤버 상세 조회 성공", response);
  }

  private ZMemberDto.GetMemberDetailResponse toResponse(ZMemberCommand.GetMemberDetailInfo info) {
    return ZMemberDto.GetMemberDetailResponse.builder()
        .email(info.getEmail())
        .nickname(info.getNickname())
        .gender(info.getGender())
        .profileImage(info.getProfileImage())
        .introduce(info.getIntroduce())
        .build();
  }

  @PutMapping("/member/{id}")
  public ApiCommonResponse<ZMemberDto.UpdateMemberResponse> updateMemberInfo(
      @PathVariable(name = "id") String memberId,
      @RequestBody ZMemberDto.UpdateMemberRequest request
  ) {
    ZMemberCommand.UpdateMemberCommand command = toCommand(memberId, request);
    ZMemberCommand.UpdateMemberInfo info = memberService.updateMember(command);
    ZMemberDto.UpdateMemberResponse response = toResponse(info);
    return new ApiCommonResponse<>(String.valueOf(HttpStatus.OK.value()), "멤버 정보 변경 성공", response);
  }

  private ZMemberCommand.UpdateMemberCommand toCommand(String memberId, ZMemberDto.UpdateMemberRequest request) {
    return ZMemberCommand.UpdateMemberCommand.builder()
        .id(memberId)
        .nickname(request.getNickname())
        .profileImage(request.getProfileImage())
        .introduce(request.getIntroduce())
        .gender(request.getGender())
        .build();
  }

  private ZMemberDto.UpdateMemberResponse toResponse(ZMemberCommand.UpdateMemberInfo info) {
    return ZMemberDto.UpdateMemberResponse.builder()
        .email(info.getEmail())
        .nickname(info.getNickname())
        .profileImage(info.getProfileImage())
        .introduce(info.getIntroduce())
        .gender(info.getGender())
        .build();
  }

  @PutMapping("/member/{id}/password")
  public ApiCommonResponse<String> updateMemberPassword(
      @PathVariable(name = "id") String memberId,
      @RequestBody ZMemberDto.UpdateMemberPasswordRequest request
  ) {
    ZMemberCommand.UpdateMemberPasswordCommand command = toCommand(memberId, request);
    ZMemberCommand.UpdateMemberPasswordInfo info = memberService.updateMemberPassword(command);
    ZMemberDto.UpdateMemberPasswordResponse response = toResponse(info);
    return new ApiCommonResponse<>(String.valueOf(HttpStatus.OK.value()), "멤버 비밀번호 변경 성공", response.getId());
  }

  private ZMemberCommand.UpdateMemberPasswordCommand toCommand(String memberId, ZMemberDto.UpdateMemberPasswordRequest request) {
    return ZMemberCommand.UpdateMemberPasswordCommand.builder()
        .id(memberId)
        .password(request.getPassword())
        .checkPassword(request.getCheckPassword())
        .build();
  }

  private ZMemberDto.UpdateMemberPasswordResponse toResponse(ZMemberCommand.UpdateMemberPasswordInfo info) {
    return ZMemberDto.UpdateMemberPasswordResponse.builder()
        .id(info.getId())
        .build();
  }

}
