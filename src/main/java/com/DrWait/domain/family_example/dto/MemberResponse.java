package com.DrWait.domain.family_example.dto;

import java.util.UUID;

public record MemberResponse(
        Long familyGroupId,
        UUID userId,
        String role,
        boolean isConfirmed
) { }
