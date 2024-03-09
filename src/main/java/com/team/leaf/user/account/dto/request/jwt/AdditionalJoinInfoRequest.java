package com.team.leaf.user.account.dto.request.jwt;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

@Data
public class AdditionalJoinInfoRequest {

    @NotBlank(message = "이메일이 비어있습니다.")
    private String email;

    private String name;

    private String birthday;

    private String gender;

    private String universityName;

    List<String> interestCategories;

    public AdditionalJoinInfoRequest(String emali, String name, String birthday, String gender, String universityName, List<String> interestCategories) {
        this.email = emali;
        this.name = name;
        this.birthday = birthday;
        this.gender = gender;
        this.universityName = universityName;
        this.interestCategories = interestCategories;
    }
}
