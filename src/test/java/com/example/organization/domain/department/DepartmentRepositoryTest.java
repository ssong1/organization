package com.example.organization.domain.department;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DepartmentRepositoryTest {

    @Autowired
    DepartmentRepository departmentRepository;

    @Test
    public void saveDepartment() {
        String name = "테스트 부서";
        String code = "T001";
        Department root = departmentRepository.findByParentIdIsNull();
        Department d = departmentRepository.save(Department.builder()
            .name(name)
            .code(code)
            .parent(root)
            .build());
        Department added = departmentRepository.findById(d.getId()).get();
        assertThat(added.getName()).isEqualTo(name);
        assertThat(added.getCode()).isEqualTo(code);

        departmentRepository.deleteById(added.getId());
    }

}
