package com.example.organization.dto;

import com.example.organization.domain.department.Department;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;

@Getter
public class DepartmentDto {

    private Integer id;
    private String type;
    private String code;
    private String name;
    private List<DepartmentDto> children;

    @JsonIgnore
    private Integer parentId;

    public DepartmentDto(Department entity) {
        this.id = entity.getId();
        this.type = "";
        this.code = entity.getCode();
        this.name = entity.getName();
        this.children = entity.getChildren()
            .stream().map(DepartmentDto::new).collect(Collectors.toList());
    }

    public DepartmentDto(Integer id, String type, String code, String name,
        Integer parentId) {
        this.id = id;
        this.type = type;
        this.code = code;
        this.name = name;
        this.parentId = parentId;
    }

    public void setChildren(List<DepartmentDto> children) {
        this.children = children;
    }

}
