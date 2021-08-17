package com.example.organization.controller;

import com.example.organization.dto.request.DepartmentManipulateRequestDto;
import com.example.organization.dto.request.MemberManipulateRequestDto;
import com.example.organization.dto.request.OrganizationSearchRequestDto;
import com.example.organization.dto.response.DepartmentManipulateResponseDto;
import com.example.organization.dto.response.MemberManipulateResponseDto;
import com.example.organization.service.MemberService;
import com.example.organization.service.OrganizationService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class OrganizationApiController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final OrganizationService organizationService;
    private final MemberService memberService;

    @GetMapping(path = "/api/organizations")
    public Object getOrganizations(
        @ModelAttribute OrganizationSearchRequestDto request) {
        String deptCode = request.getDeptCode(); // 기준 부서코드
        Boolean deptOnly = request.getDeptOnly(); // 부서만
        String searchType = request.getSearchType(); // 검색 대상(dept: 부서, member: 부서원)
        String searchKeword = request.getSearchKeyword(); // 검색어
        logger.info("deptCode: " + deptCode);
        logger.info("deptOnly: " + deptOnly);
        logger.info("searchType: " + searchType);
        logger.info("searchKeword: " + searchKeword);

        if (deptOnly && searchType != null) {
            throw new IllegalArgumentException("deptOnly와 searchType 파라미터를 같이 사용할 수 없습니다.");
        }
        if (searchType != null && searchKeword == null) {
            throw new IllegalArgumentException(
                "searchType 파라미터를 사용할 경우 searchKeyword 파라미터도 필요합니다.");
        }

        if (deptOnly) { // 부서 만 조회 조건
            if (deptCode != null) { // deptCode 조회 조건
                return organizationService.findDeptCodeDeptOnly(deptCode);
            } else {
                return organizationService.findDeptOnly();
            }
        } else {
            if (searchType == null) { // 부서와 멤버 모두 조회 조건
                if (deptCode != null) { // deptCode 조회 조건
                    return organizationService.findDeptCode(deptCode);
                } else {
                    return organizationService.findAll();
                }
            } else { // 검색 키워드 조건
                if ("dept".equals(searchType)) {
                    return organizationService.findDeptOnlyByKeyword(searchKeword);
                } else if ("member".equals(searchType)) {
                    return organizationService.findAllByKeyword(searchKeword);
                }
            }
            return null;
        }
    }

    @PostMapping("/org/dept")
    public DepartmentManipulateResponseDto addDepartment(
        @RequestBody DepartmentManipulateRequestDto dto) {
        return organizationService.addDepartment(dto);
    }

    @PutMapping("/org/dept/{deptId}")
    public DepartmentManipulateResponseDto modifyDepartment(@PathVariable Integer deptId,
        @RequestBody DepartmentManipulateRequestDto dto) {
        return organizationService.modifyDepartment(deptId, dto);
    }

    @DeleteMapping("/org/dept/{deptId}")
    public DepartmentManipulateResponseDto deleteDepartment(@PathVariable Integer deptId) {
        return organizationService.deleteDepartment(deptId);
    }

    @PostMapping("/org/member")
    public MemberManipulateResponseDto addMember(
        @RequestBody MemberManipulateRequestDto dto) {
        return memberService.addMember(dto);
    }

    @PutMapping("/org/member/{memberId}")
    public MemberManipulateResponseDto modifyMember(@PathVariable Integer memberId,
        @RequestBody MemberManipulateRequestDto dto) {
        return memberService.modifyMember(memberId, dto);
    }

    @DeleteMapping("/org/member/{memberId}")
    public MemberManipulateResponseDto deleteMember(@PathVariable Integer memberId) {
        return memberService.deleteMember(memberId);
    }

}
