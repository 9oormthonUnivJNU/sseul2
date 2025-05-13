package jpabook.jpashop.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jpabook.jpashop.domain.Member;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository // 컴포넌트 스캔에 의해 자동으로 등록(스프링 빈으로 등록)
public class MemberRepository {
    @PersistenceContext // EntityManager를 영속성 컨텍스트로 등록해 주입받을 수 있음
    private EntityManager em;

    //영속성 컨텍스트에 member 객체 넣음(트랜젝션이 커밋되는 시점에 DB에 반영-> insert 쿼리 날아감)
    public void save(Member member){
        em.persist(member);
    }

    // find 메서드 사용 em.find(타입, pk);
    public Member findOne(Long id){
        return em.find(Member.class, id);
    }

    // JPQL은 from의 대상이 엔티티
    public List<Member> findAll(){
        return em.createQuery("select m from Member m", Member.class) // JPQL은 엔티티를 대상으로 매핑한다. SQL은 테이블을 기준
                .getResultList();
    }


    public List<Member> findName(String name){
        return em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
    }
}
