package com.example.organization.service;

import com.example.organization.domain.department.Department;
import com.example.organization.domain.department.DepartmentRepository;
import com.example.organization.domain.member.Member;
import com.example.organization.domain.member.MemberRepository;
import com.example.organization.dto.request.MemberManipulateRequestDto;
import com.example.organization.dto.response.MemberManipulateResponseDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class MemberService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final DepartmentRepository departmentRepository;
    private final MemberRepository memberRepository;

    // 멤버 추가
    @Transactional
    public MemberManipulateResponseDto addMember(MemberManipulateRequestDto dto) {
        Department department = departmentRepository.findById(dto.getDepartmentId())
            .orElseThrow(() -> new IllegalArgumentException("입력하신 department_id에 해당하는 부서가 없습니다."));
        Department subDepartment = null;
        if (dto.getSubDepartmentId() != null) {
            subDepartment = departmentRepository.findById(dto.getSubDepartmentId()).orElseThrow(
                () -> new IllegalArgumentException("입력하신 sub_department_id에 해당하는 부서가 없습니다."));
        }

        Member newMember = Member.builder()
            .name(dto.getName())
            .isManager(dto.getIsManager())
            .department(department)
            .subDepartment(subDepartment)
            .build();
        Integer id = memberRepository.saveAndFlush(newMember).getId();
        Member added = memberRepository.findById(id).get();
        return dtoCreator(added, "정상적으로 추가되었습니다.");
    }

    // 멤버 수정
    @Transactional
    public MemberManipulateResponseDto modifyMember(Integer memberId,
        MemberManipulateRequestDto dto) {
        Member target = memberRepository.findById(memberId)
            .orElseThrow(() -> new IllegalArgumentException("입력하신 id에 해당하는 멤버가 없습니다."));
        Department department = departmentRepository.findById(dto.getDepartmentId())
            .orElseThrow(() -> new IllegalArgumentException("입력하신 department_id에 해당하는 부서가 없습니다."));
        Department subDepartment = null;
        if (dto.getSubDepartmentId() != null) {
            subDepartment = departmentRepository.findById(dto.getSubDepartmentId()).orElseThrow(
                () -> new IllegalArgumentException("입력하신 sub_department_id에 해당하는 부서가 없습니다."));
        }
        target.update(dto.getName(), dto.getIsManager(), department, subDepartment);
        try {
            memberRepository.saveAndFlush(target);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("deptCode가 중복됩니다.");
        }
        return dtoCreator(target, "정상적으로 수정되었습니다.");
    }

    // 멤버 삭제
    @Transactional
    public MemberManipulateResponseDto deleteMember(Integer id) {
        Member target = memberRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("입력하신 id에 속하는 멤버가 없습니다."));
        memberRepository.delete(target);
        return dtoCreator(target, "정상적으로 삭제되었습니다.");
    }

    private MemberManipulateResponseDto dtoCreator(Member member, String message) {
        return MemberManipulateResponseDto.builder()
            .message(message)
            .id(member.getId())
            .name(member.getName())
            .isManager(member.getIsManager())
            .departmentId(member.getDepartment().getId())
            .subDepartmentId(
                member.getSubDepartment() == null ? null : member.getSubDepartment().getId())
            .build();
    }

}
