package com.example.organization.dto.request;

import lombok.Getter;

/**
 * 조직도 검색 dto
 * 공백 또는 빈 값은 null 처리 함
 */
@Getter
public class OrganizationSearchRequestDto {

    private String deptCode;
    private Boolean deptOnly = false;
    private String searchType;
    private String searchKeyword;

    public String getDeptCode() {
        return deptCode;
    }

    public void setDeptCode(String deptCode) {
        deptCode = deptCode.trim();
        this.deptCode = "".equals(deptCode) ? null : deptCode;
    }

    public void setDeptOnly(Boolean deptOnly) {
        this.deptOnly = deptOnly;
    }

    public void setSearchType(String searchType) {
        searchType = searchType.trim();
        this.searchType = "".equals(searchType) ? null : searchType;
    }

    public void setSearchKeyword(String searchKeyword) {
        searchKeyword = searchKeyword.trim();
        this.searchKeyword = "".equals(searchKeyword) ? null : searchKeyword;
    }

}
