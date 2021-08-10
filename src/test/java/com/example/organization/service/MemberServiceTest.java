package com.example.organization.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.organization.domain.member.Member;
import com.example.organization.domain.member.MemberRepository;
import com.example.organization.dto.request.MemberManipulateRequestDto;
import com.example.organization.dto.response.MemberManipulateResponseDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MemberServiceTest {

    @Autowired
    private MemberService service;

    @Autowired
    private MemberRepository repository;

    @Test
    public void addMemberTest() {
        MemberManipulateRequestDto dto = MemberManipulateRequestDto.builder()
            .name("테스트 멤버")
            .isManager(true)
            .departmentId(2)
            .build();
        MemberManipulateResponseDto responseDto = service.addMember(dto);
        Member added = repository.findById(responseDto.getId()).get();
        assertThat(added.getName()).isEqualTo(dto.getName());
    }

}
