package com.example.organization.domain.department;

import com.example.organization.domain.member.Member;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED) // 무분별한 new 사용을 막기 위해 protected로 설정
@Getter
@Entity
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true)
    private String code;

    private String name;

    // parent department 자기 참조
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Department parent;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "parent")
    private List<Department> children = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "department")
    private List<Member> member = new ArrayList<>();

    // Entity는 new 를 통한 생성보다는 Builder 패턴을 사용
    @Builder
    public Department(String code, String name, Department parent) {
        this.code = code;
        this.name = name;
        this.parent = parent;
    }

    // Entity는 되도록이면 Setter 사용을 지양(객체의 일관성을 보장하기 위해)
    // 메소드를 통해 값 변경 처리
    public void update(String code, String name, Department parent) {
        this.code = code;
        this.name = name;
        this.parent = parent;
    }

}
