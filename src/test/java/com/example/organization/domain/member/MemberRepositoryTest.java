package com.example.organization.domain.member;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.organization.domain.department.Department;
import com.example.organization.domain.department.DepartmentRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    DepartmentRepository departmentRepository;

    @Test
    public void addMember() {
        String name = "테스트 사용자";
        Department root = departmentRepository.findByParentIdIsNull();
        Member m = memberRepository.save(Member.builder()
            .name(name)
            .department(root)
            .build());
        Member added = memberRepository.findById(m.getId()).get();
        assertThat(added.getName()).isEqualTo(name);

        memberRepository.deleteById(m.getId());
    }

}
