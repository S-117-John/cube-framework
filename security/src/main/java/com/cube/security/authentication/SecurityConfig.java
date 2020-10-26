package com.cube.security.authentication;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    public SecurityConfig() {
    }

    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(new String[]{"/api/**"});
    }
}
