package com.example.organization.dto.response;

import lombok.Builder;
import lombok.Getter;

/**
 * 멤버 추가, 수정, 삭제 응답 dto
 */
@Getter
public class MemberManipulateResponseDto {

    private String message;
    private Integer id;
    private String name;
    private Boolean isManager;
    private Integer departmentId;
    private Integer subDepartmentId;

    @Builder
    public MemberManipulateResponseDto(String message, Integer id, String name, Boolean isManager,
        Integer departmentId, Integer subDepartmentId) {
        this.message = message;
        this.id = id;
        this.name = name;
        this.isManager = isManager;
        this.departmentId = departmentId;
        this.subDepartmentId = subDepartmentId;
    }

}
