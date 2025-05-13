package jpabook.jpashop.service;

import jakarta.persistence.EntityManager;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    EntityManager em;

    @Test
    @DisplayName("회원가입")
    public void join() throws Exception {
        // given
        Member member = new Member();
        member.setName("kim");
        // when
        Long saveId = memberService.join(member);
        
        // then (가입이 되었는지 확인. "kim"을 넣은 member 객체와 memberRepository에서 saveId로 찾은 객체가 같은지)
        em.flush(); // 영속성 컨텍스트에 있는 데이터를 쿼리로 날려서 DB에 저장시킨다
        assertEquals(member, memberRepository.findOne(saveId));
    }

    @Test
    @DisplayName("중복 회원 예외")
    public void duplicationUserException() throws Exception {
        // given
        Member member1 = new Member();
        member1.setName("kim");
        Member member2 = new Member();
        member2.setName("kim");
        
        // when
        memberService.join(member1);
        
        // then
//        try {
//            memberService.join(member2);
//        }catch(IllegalStateException e){
//            return;
//        }
        // IllegalStateException이 발생하지 않으면 테스트 실패
        assertThrows(IllegalStateException.class, () -> memberService.join(member2));
    }

}