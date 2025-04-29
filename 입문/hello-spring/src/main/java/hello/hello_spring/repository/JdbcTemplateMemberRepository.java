package hello.hello_spring.repository;

import hello.hello_spring.domain.Member;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class JdbcTemplateMemberRepository implements MemberRepository{

    private final JdbcTemplate jdbcTemplate;

    public JdbcTemplateMemberRepository(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }


    @Override
    public Member save(Member member) throws SQLException {
        return null;
    }

    @Override
    public Optional<Member> findMyId(Long id) throws SQLException {
        return Optional.empty();
    }

    @Override
    public Optional<Member> findMyName(String name) throws SQLException {
        return Optional.empty();
    }

    @Override
    public List<Member> findAll() throws SQLException {
        return null;
    }
}
