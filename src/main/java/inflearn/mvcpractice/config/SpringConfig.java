package inflearn.mvcpractice.config;


import inflearn.mvcpractice.repository.JdbcMemberRepository;
import inflearn.mvcpractice.repository.MemberRepository;
import inflearn.mvcpractice.repository.MemoryMemberRepository;
import inflearn.mvcpractice.service.MemberService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class SpringConfig {

    private final DataSource dataSource;

    public SpringConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }


    @Bean
    public MemberService memberService() {
        return new MemberService(memberRepository());
    }

    @Bean
    public MemberRepository memberRepository() {
//        return new MemoryMemberRepository();
        return new JdbcMemberRepository(dataSource);
    }
}
