# 조직도 API
## 개발 환경
- Java 1.8
- Spring Boot 2.3.12.RELEASE
- h2 db
## API 서버 구동 방법
- Application.java 실행
- postman을 사용하여 테스트 했습니다.
- postman을 통하여 post, put 메소드를 사용할 경우 body를 raw로 선택 후 dropdown 메뉴에서 JSON 선택 하고 내용을 작성하세요.
## API
- 전제 조직도 조회: [get] http://localhost:8080/api/organizations
- 전체 부서만 조회: [get] http://localhost:8080/api/organizations?deptOnly=true
- deptCode 조회(부서만): [get] http://localhost:8080/api/organizations?deptOnly=true&deptCode=S100
- deptCode 조회: [get] http://localhost:8080/api/organizations?deptCode=D100
- keyword 조회(부서만): [get] http://localhost:8080/api/organizations?searchType=dept&searchKeyword=개발
- keyword 조회: 미완성


- 부서 추가: [post] http://localhost:8080/org/dept
    ```
        {
            "deptCode": "T110",
            "name": "테스트 부서",
            "parentId": 2
        }
    ```
- 부서 수정: [put] http://localhost:8080/org/dept/4
    ```
        {
            "deptCode": "D120",
            "name": "회계팀을 플랫폼개발부로 옮김",
            "parentId": 6
        }
    ```
- 부서 삭제: [delete] http://localhost:8080/org/dept/11


- 멤버 추가: [post] http://localhost:8080/org/member
    ```
        {
            "name": "테스트 팀장",
            "isManager": true,
            "departmentId": 3,
            "subDepartmentId": 6
        }
    ```
- 멤버 수정: [put] http://localhost:8080/org/member/101
    ```
        {
            "name": "테스트 팀장",
            "isManager": true,
            "departmentId": 3
        }
    ```
- 멤버 삭제: [delete] http://localhost:8080/org/member/103