package com.example.organization.dto;

import com.example.organization.domain.department.Department;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;

@JsonInclude(Include.NON_NULL)
@Getter
public class OrganizationDto {

    private Integer id;
    private String type;
    private String code;
    private String name;
    private List<Object> children;

    private Integer parentId;

    public OrganizationDto(Department entity) {
        this.id = entity.getId();
        this.type = "";
        this.code = entity.getCode();
        this.name = entity.getName();
        this.children = entity.getChildren()
            .stream().map(OrganizationDto::new)
            .collect(Collectors.toList());
        this.children.addAll(entity.getMember()
            .stream().map(MemberDto::new)
            .collect(Collectors.toList()));
    }

    public OrganizationDto(Integer id, String type, String code, String name, Integer parentId) {
        this.id = id;
        this.type = type;
        this.code = code;
        this.name = name;
        this.parentId = parentId;
    }

    public void setChildren(List<Object> children) {
        this.children = children;
    }

}
