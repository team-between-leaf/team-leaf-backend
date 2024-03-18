package com.team.leaf.user.account.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InterestCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String category;

    private String category1;

    private String category2;

    private String category3;

    private String category4;

    private String category5;

    @OneToMany(mappedBy = "interestCategory", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AccountInterest> accountInterests = new ArrayList<>();

}
