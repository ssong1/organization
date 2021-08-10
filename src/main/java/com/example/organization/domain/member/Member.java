package com.example.organization.domain.member;

import com.example.organization.domain.department.Department;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private Boolean isManager;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
    private Department department;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sub_department_id")
    private Department subDepartment;

    @Builder
    public Member(String name, Boolean isManager, Department department, Department subDepartment) {
        this.name = name;
        this.isManager = isManager;
        this.department = department;
        this.subDepartment = subDepartment;
    }

    public void update(String name, Boolean isManager, Department department,
        Department subDepartment) {
        this.name = name;
        this.isManager = isManager;
        this.department = department;
        this.subDepartment = subDepartment;
    }

}
