package com.example.organization.domain.department;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DepartmentRepository extends JpaRepository<Department, Integer> {

    Department findByParentIdIsNull();

    Optional<Department> findAllByCode(String code);

    @Query(value = "WITH RECURSIVE cte (id, code, name, parent_id, lvl) AS ("
        + "  SELECT d.*, 1 AS lvl"
        + "  FROM department d"
        + "  WHERE name LIKE %:searchKeyword%"
        + "  UNION ALL"
        + "  SELECT p.*, c.lvl + 1"
        + "  FROM department p"
        + "  INNER JOIN cte c"
        + "  ON p.id = c.parent_id"
        + ")"
        + "SELECT DISTINCT id, code, name, parent_id "
        + "FROM cte ORDER BY lvl DESC", nativeQuery = true)
    List<Department> findAllByNameContaining(@Param("searchKeyword") String searchKeyword);

    @Query(value = "WITH RECURSIVE cte (id, code, name, parent_id, lvl) AS ("
        + "  SELECT d.*, 1 AS lvl"
        + "  FROM department d"
        + "  WHERE EXISTS (SELECT 1 FROM member m WHERE d.id = m.department_id AND m.name LIKE %:searchKeyword%)"
        + "  UNION ALL"
        + "  SELECT p.*, c.lvl + 1"
        + "  FROM department p"
        + "  INNER JOIN cte c"
        + "  ON p.id = c.parent_id"
        + ")"
        + "SELECT DISTINCT c.id, c.code, c.name, c.parent_id "
        + "FROM cte c ORDER BY lvl DESC", nativeQuery = true)
    List<Department> findAllByMemberNameContaining(@Param("searchKeyword") String searchKeyword);

}
