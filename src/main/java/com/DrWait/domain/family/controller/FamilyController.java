package com.DrWait.domain.family.controller;

import com.DrWait.domain.family.dto.MemberAddRequest;
import com.DrWait.domain.family.dto.MemberListResponse;
import com.DrWait.domain.family.dto.MemberResponse;
import com.DrWait.domain.family.entity.FamilyGroup;
import com.DrWait.domain.family.entity.FamilyMember;
import com.DrWait.domain.family.service.FamilyGroupService;
import com.DrWait.domain.family.service.FamilyMemberService;
import com.DrWait.domain.user.entity.User;
import com.DrWait.global.error.CustomException;
import com.DrWait.global.error.ErrorCode;
import com.DrWait.global.security.auth.service.AuthService;
import com.DrWait.global.security.token.JwtTokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.HashSet;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/family")
public class FamilyController {

    private final JwtTokenProvider jwtTokenProvider;
    private final AuthService authService;
    private final FamilyMemberService familyMemberService;
    private final FamilyGroupService familyGroupService;

    @PostMapping
    public ResponseEntity<MemberResponse> addMember(
            @RequestBody @Valid MemberAddRequest req,
            HttpServletRequest request) {
        // Validate JWT token
        String token = jwtTokenProvider.resolveToken(request);
        if (token == null || !jwtTokenProvider.validateToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        // Extract user from JWT token
        User owner = authService.getUserByBearerToken(token);

        // Get response from service
        MemberResponse response = familyMemberService.addMember(owner, req);

        // URI location
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{userId}")
                .buildAndExpand(response.userId())
                .toUri();

        // Return response with created status
        return ResponseEntity.created(location).body(response);
    }

    @DeleteMapping("/{groupId}/{userId}")
    public ResponseEntity<Void> deleteMember(
            @PathVariable("groupId") Long groupId,
            @PathVariable("userId") UUID userId) {

        // You may validate the userId from the JWT token here if needed ^_^
        familyMemberService.removeMember(groupId, userId);
        // Return no content status after successful deletion
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<MemberListResponse> getMyFamilyMembers(HttpServletRequest request){
        String token = jwtTokenProvider.resolveToken(request);
        if(token == null || !jwtTokenProvider.validateToken(token)){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        User user = authService.getUserByBearerToken(token);
        Optional<FamilyMember> familyMember = familyMemberService.getMembersByUser(user);

        // if not join any group, don't save DB, just send empty value
        if(familyMember.isEmpty()) throw new CustomException(ErrorCode.FAMILY_GROUP_NOT_FOUND);

        if(!familyMember.get().isConfirmed()) {
            HashSet<FamilyMember> justMine = new HashSet<>();
            justMine.add(familyMember.get());
            return ResponseEntity.ok(new MemberListResponse(justMine));
        }

        return ResponseEntity.ok(familyGroupService.getGroupMembersByGroupId(familyMember.get().getFamilyGroup().getId()));
    }

    @PostMapping("/confirm")
    public ResponseEntity<?> confirmFamilyMember(HttpServletRequest request){
        String token = jwtTokenProvider.resolveToken(request);
        if(token == null || !jwtTokenProvider.validateToken(token)){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        User user = authService.getUserByBearerToken(token);
        familyMemberService.confirmedMember(user);
        return ResponseEntity.ok("가족 그룹에 등록되었습니다.");
    }
}
