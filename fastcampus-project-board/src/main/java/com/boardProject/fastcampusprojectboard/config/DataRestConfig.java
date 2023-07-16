package com.boardProject.fastcampusprojectboard.config;

import com.boardProject.fastcampusprojectboard.domain.UserAccount;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;

@Configuration
public class DataRestConfig {

    @Bean
    public RepositoryRestConfigurer repositoryRestConfigurer(){
        return RepositoryRestConfigurer.withConfig((config, cors)->
                config.exposeIdsFor(UserAccount.class)
                );
    }
}
