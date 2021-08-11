package com.example.organization.service;

import static java.util.stream.Collectors.groupingBy;

import com.example.organization.domain.department.Department;
import com.example.organization.domain.department.DepartmentRepository;
import com.example.organization.dto.DepartmentDto;
import com.example.organization.dto.OrganizationDto;
import com.example.organization.dto.request.DepartmentManipulateRequestDto;
import com.example.organization.dto.response.DepartmentManipulateResponseDto;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class OrganizationService {

    private final DepartmentRepository departmentRepository;

    /**
     * 모든 부서와 멤버 찾기
     *
     * @return OrganizationDto
     */
    @Transactional(readOnly = true)
    public OrganizationDto findAll() {
        return new OrganizationDto(departmentRepository.findByParentIdIsNull());
    }

    /**
     * 모든 부서만 찾기
     *
     * @return DepartmentDto
     */
    @Transactional(readOnly = true)
    public DepartmentDto findDeptOnly() {
        return new DepartmentDto(departmentRepository.findByParentIdIsNull());
    }

    /**
     * deptCode로 부서와 멤버 찾기
     *
     * @param deptCode 부서코드
     * @return OrganizationDto
     */
    @Transactional(readOnly = true)
    public OrganizationDto findDeptCode(String deptCode) {
        return new OrganizationDto(departmentRepository.findAllByCode(deptCode)
            .orElseThrow(() -> new IllegalArgumentException("입력하신 deptCode에 해당하는 부서가 없습니다.")));
    }

    /**
     * deptCode로 부서만 찾기
     *
     * @param deptCode 부서코드
     * @return DepartmentDto
     */
    @Transactional(readOnly = true)
    public DepartmentDto findDeptCodeDeptOnly(String deptCode) {
        return new DepartmentDto(departmentRepository.findAllByCode(deptCode)
            .orElseThrow(() -> new IllegalArgumentException("입력하신 deptCode에 해당하는 부서가 없습니다.")));
    }

    /**
     * 키워드로 부서와 멤버 찾기
     *
     * @param searchKeyword 키워드
     * @return OrganizationDto
     */
    @Transactional(readOnly = true)
    public OrganizationDto findAllByKeyword(String searchKeyword) {
        // TODO: 2021-08-10 개발 중 
        return null;
    }

    /**
     * 키워드로 부서만 찾기
     *
     * @param searchKeyword 키워드
     * @return DepartmentDto
     */
    @Transactional(readOnly = true)
    public DepartmentDto findDeptOnlyByKeyword(String searchKeyword) {
        List<Department> departments = departmentRepository.findAllByNameContaining(searchKeyword);
        if (departments.size() == 0) {
            throw new IllegalArgumentException("입력하신 keyword에 해당하는 부서가 없습니다.");
        }

        // parent_id로 그룹핑 함
        Map<Integer, List<DepartmentDto>> groupedByParentId = departments.stream()
            .map(e -> new DepartmentDto(e.getId(), "", e.getCode(), e.getName(),
                // root는 parent_id 가 null이므로 삼항 연산자로 0 셋팅
                e.getParent() == null ? 0 : e.getParent().getId()))
            .collect(groupingBy(DepartmentDto::getParentId));
        DepartmentDto rootDto = groupedByParentId.get(0).get(0);
        addChildren(rootDto, groupedByParentId);
        return rootDto;
    }

    private void addChildren(DepartmentDto parent,
        Map<Integer, List<DepartmentDto>> groupedByParentId) {
        // 1. parent_Id로 child를 찾음
        List<DepartmentDto> children = groupedByParentId.get(parent.getId());
        if (children == null) { // 종료 조건
            return;
        }
        // 2. children 셋팅
        parent.setChildren(children);
        // 3. 재귀적으로 children들에 대해서도 수행
        children.forEach(s -> addChildren(s, groupedByParentId));
    }

    /**
     * 부서 추가
     *
     * @param dto 부서 추가, 수정, 삭제 요청 dto
     * @return DepartmentManipulateResponseDto 부서 추가, 수정, 삭제 응답 dto
     */
    @Transactional
    public DepartmentManipulateResponseDto addDepartment(DepartmentManipulateRequestDto dto) {
        Department parent = departmentRepository.findById(dto.getParentId())
            .orElseThrow(() -> new IllegalArgumentException("입력하신 parent_id에 해당하는 부서가 없습니다."));
        Department newDept = Department.builder()
            .code(dto.getDeptCode())
            .name(dto.getName())
            .parent(parent)
            .build();
        Integer id;
        try {
            id = departmentRepository.saveAndFlush(newDept).getId();
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("deptCode가 중복됩니다.");
        }
        Department added = departmentRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("부서 추가 중 오류 발생"));
        return dtoCreator(added, "정상적으로 추가되었습니다.");
    }

    /**
     * 부서 수정
     *
     * @param dto 부서 추가, 수정, 삭제 요청 dto
     * @return DepartmentManipulateResponseDto 부서 추가, 수정, 삭제 응답 dto
     */
    @Transactional
    public DepartmentManipulateResponseDto modifyDepartment(Integer deptId,
        DepartmentManipulateRequestDto dto) {
        Department target = departmentRepository.findById(deptId)
            .orElseThrow(() -> new IllegalArgumentException("입력하신 id에 해당하는 부서가 없습니다."));
        Department parent = departmentRepository.findById(dto.getParentId())
            .orElseThrow(() -> new IllegalArgumentException("입력하신 parent_id에 해당하는 부서가 없습니다."));
        target.update(dto.getDeptCode(), dto.getName(), parent);
        try {
            departmentRepository.saveAndFlush(target);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("deptCode가 중복됩니다.");
        }
        return dtoCreator(target, "정상적으로 수정되었습니다.");
    }

    /**
     * 부서 삭제
     *
     * @param id 부서 id
     * @return DepartmentManipulateResponseDto 부서 추가, 수정, 삭제 응답 dto
     */
    @Transactional
    public DepartmentManipulateResponseDto deleteDepartment(Integer id) {
        Department target = departmentRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("입력하신 id에 속하는 부서가 없습니다."));
        if (target.getChildren().size() > 0 || target.getMember().size() > 0) {
            throw new IllegalArgumentException("하위 부서 또는 멤버가 존재하므로 삭제할 수 없습니다.");
        }
        departmentRepository.delete(target);
        return dtoCreator(target, "정상적으로 삭제되었습니다.");
    }

    private DepartmentManipulateResponseDto dtoCreator(Department department, String message) {
        return DepartmentManipulateResponseDto.builder()
            .message(message)
            .id(department.getId())
            .deptCode(department.getCode())
            .name(department.getName())
            .parentId(department.getParent().getId())
            .build();
    }

}
