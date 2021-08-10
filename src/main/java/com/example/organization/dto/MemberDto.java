package com.example.organization.dto;

import com.example.organization.domain.member.Member;
import lombok.Getter;

@Getter
public class MemberDto {

    private Integer id;
    private String type;
    private String name;
    private Boolean isManager;

    public MemberDto(Member entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.type = "Member";
        this.isManager = entity.getIsManager();
    }

}
