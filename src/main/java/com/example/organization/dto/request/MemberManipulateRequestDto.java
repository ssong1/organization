package com.example.organization.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * 멤버 추가, 수정, 삭제 dto
 */
@Setter
@Getter
public class MemberManipulateRequestDto {

    private String name;
    private Boolean isManager;
    private Integer departmentId;
    private Integer subDepartmentId;

    @Builder
    public MemberManipulateRequestDto(String name, Boolean isManager, Integer departmentId,
        Integer subDepartmentId) {
        this.name = name;
        this.isManager = isManager;
        this.departmentId = departmentId;
        this.subDepartmentId = subDepartmentId;
    }

}
