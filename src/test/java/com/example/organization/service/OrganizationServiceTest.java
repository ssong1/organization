package com.example.organization.service;

import com.example.organization.domain.department.Department;
import com.example.organization.domain.department.DepartmentRepository;
import com.example.organization.dto.request.DepartmentManipulateRequestDto;
import com.example.organization.dto.response.DepartmentManipulateResponseDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrganizationServiceTest {

    @Autowired
    private OrganizationService service;

    @Autowired
    private DepartmentRepository repository;

    @Test
    public void addDepartmentTest() {
        DepartmentManipulateRequestDto dto = DepartmentManipulateRequestDto.builder()
            .deptCode("T100")
            .name("테스트 부서")
            .parentId(1)
            .build();
        DepartmentManipulateResponseDto responseDto = service.addDepartment(dto);
        Department added = repository.findById(responseDto.getId()).get();
        assertThat(added.getName()).isEqualTo(dto.getName());
    }

}
