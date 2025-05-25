package com.DrWait.domain.hospital.dto;

import com.DrWait.domain.hospital.entity.Hospital;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class HospitalInfoResponseDto {

    private String username;
    private String name;
    private String department;
    private String address;
    private String telephone;
    private String websiteUrl;

    public HospitalInfoResponseDto(Hospital hospital){
        this.username = hospital.getUsername();
        this.name = hospital.getName();
        this.department = hospital.getDepartment();
        this.address = hospital.getAddress();
        this.telephone = hospital.getTelephone();
        this.websiteUrl = hospital.getWebsiteUrl();
    }
}
