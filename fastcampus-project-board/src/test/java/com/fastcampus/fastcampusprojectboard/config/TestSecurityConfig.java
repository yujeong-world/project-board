package com.fastcampus.fastcampusprojectboard.config;

import com.boardProject.fastcampusprojectboard.config.SecurityConfig;
import com.boardProject.fastcampusprojectboard.domain.UserAccount;
import com.boardProject.fastcampusprojectboard.repository.UserAccountRepository;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.event.annotation.BeforeTestMethod;

import java.util.Optional;


@Import(SecurityConfig.class)
public class TestSecurityConfig {

    @MockBean private UserAccountRepository userAccountRepository;


    //스프링 테스트를 할 때마다 특정한 주기에 맞춰서 특정한 코드가 실행되게 끔 만듦, 각 테스트 메소드가 실행되기 전에 인증정보를 집어줘라.
    //이 코드가 각종 시큐리티 테스트에 들어가기만 하면 됨!
    @BeforeTestMethod
    public void securitySetUp(){
        BDDMockito.given(userAccountRepository.findById(ArgumentMatchers.anyString())).willReturn(Optional.of(UserAccount.of(
                "uno",
                "pw",
                "uno-test@email.com",
                "uno-test",
                "test memo"
                )
        ));
    }


}
