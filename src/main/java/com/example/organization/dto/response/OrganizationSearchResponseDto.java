package com.example.organization.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@JsonInclude(Include.NON_NULL)
@Getter
@Setter
public class OrganizationSearchResponseDto {

    private Integer id;
    private String type;
    private String code;
    private String name;
    private Boolean isManager;
    @JsonIgnore
    private Integer parentId;
    @JsonIgnore
    private Integer departmentId;
    @JsonIgnore
    private Integer subDepartmentId;
    private List<OrganizationSearchResponseDto> children;

    public OrganizationSearchResponseDto(Integer id, String type, String code, String name,
        Boolean isManager, Integer parentId, Integer departmentId, Integer subDepartmentId) {
        this.id = id;
        this.type = type;
        this.code = code;
        this.name = name;
        this.isManager = isManager;
        this.parentId = parentId;
        this.departmentId = departmentId;
        this.subDepartmentId = subDepartmentId;
    }

}
