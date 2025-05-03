package hello.hello_spring.repository;

import hello.hello_spring.domain.Member;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface MemberRepository {
    Member save(Member member) throws SQLException;
    Optional<Member> findById(Long id) throws SQLException;
    Optional<Member> findByName(String name) throws SQLException;

    List<Member> findAll() throws SQLException;
}
