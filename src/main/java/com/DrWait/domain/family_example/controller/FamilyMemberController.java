package com.DrWait.domain.family_example.controller;

import com.DrWait.core.security.token.JwtTokenProvider;
import com.DrWait.domain.family_example.dto.MemberAddRequest;
import com.DrWait.domain.family_example.dto.MemberResponse;
import com.DrWait.domain.family_example.service.FamilyMemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/api/family")
@RequiredArgsConstructor
public class FamilyMemberController {
    private final JwtTokenProvider jwtTokenProvider;
    private final FamilyMemberService familyMemberService;

    @PostMapping
    public ResponseEntity<MemberResponse> addMember(
            @RequestBody @Valid MemberAddRequest req,
            HttpServletRequest request) {
        // Validate JWT token
        String token = jwtTokenProvider.resolveToken(request);
        if (token == null || !jwtTokenProvider.validateToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        // Extract userId from JWT token
        UUID ownerId = UUID.fromString(jwtTokenProvider.getUserId(token));

        // Get response from service
        MemberResponse response = familyMemberService.addMember(ownerId, req);

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
            @PathVariable Long groupId,
            @PathVariable UUID userId) {
        // You may validate the userId from the JWT token here if needed ^_^
        familyMemberService.removeMember(groupId, userId);
        // Return no content status after successful deletion
        return ResponseEntity.noContent().build();
    }
}
