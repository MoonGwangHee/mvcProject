package inflearn.mvcpractice;

import inflearn.mvcpractice.entity.Member;
import inflearn.mvcpractice.repository.MemoryMemberRepository;
import inflearn.mvcpractice.service.MemberService;
import org.aspectj.lang.annotation.After;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class MemberServiceTest {

    MemberService memberService;
    MemoryMemberRepository memberRepository;

    @BeforeEach
    public void beforeEach() {
        memberRepository = new MemoryMemberRepository();
        memberService = new MemberService(memberRepository);
    }

    @AfterEach
    public void afterEach() {
        memberRepository.clearStore();
    }

    @Test
    public void 회원가입() throws Exception {

        // Given
        Member member = new Member();
        member.setName("Moon");

        // when
        Long savedId = memberService.join(member);

        // Then
        Member findMember = memberRepository.findById(savedId).get();
        assertEquals(member.getName(), findMember.getName());
    }
    
    @Test
    public void 중복_회원_예외() throws Exception {
        
        // Given
        Member member1 = new Member();
        member1.setName("Moon");
        
        Member member2 = new Member();
        member2.setName("Moon");
        
        // when
        memberService.join(member1);
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));
        
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
    }
}
