package com.DrWait.domain.hospital.dto;

import java.util.UUID;

public record HospitalInfoResponse(
    UUID id,
    String name,
    String department,
    String address,
    String telephone,
    String websiteUrl
) {

}
