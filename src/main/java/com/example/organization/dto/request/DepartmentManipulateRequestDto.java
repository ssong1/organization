package com.example.organization.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * 부서 추가, 수정, 삭제 dto
 */
@Setter
@Getter
public class DepartmentManipulateRequestDto {

    private String deptCode;
    private String name;
    private Integer parentId;

    @Builder
    public DepartmentManipulateRequestDto(String deptCode, String name, Integer parentId) {
        this.deptCode = deptCode;
        this.name = name;
        this.parentId = parentId;
    }

}
