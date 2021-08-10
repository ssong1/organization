package com.example.organization.dto.response;

import lombok.Builder;
import lombok.Getter;

/**
 * 부서 추가, 수정, 삭제 응답 dto
 */
@Getter
public class DepartmentManipulateResponseDto {

    private String message;
    private Integer id;
    private String deptCode;
    private String name;
    private Integer parentId;

    @Builder
    public DepartmentManipulateResponseDto(String message, Integer id, String deptCode, String name,
        Integer parentId) {
        this.message = message;
        this.id = id;
        this.deptCode = deptCode;
        this.name = name;
        this.parentId = parentId;
    }

}
