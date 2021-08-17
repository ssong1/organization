package com.example.organization.domain.member;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Integer> {

    List<Member> findAllByNameContaining(String searchKeyword);

}
