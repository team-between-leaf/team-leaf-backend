package com.team.leaf.user.account.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDate;

@Entity
@Getter
public class UserDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userDetailId;

    private boolean isStudent;

    private String Image;

    private boolean isReceiveMarketing;

    private LocalDate birthDate;

}
