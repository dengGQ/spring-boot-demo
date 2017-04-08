package com.dgq.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import com.dgq.service.impl.UserServiceImpl;

/**
 * @EnableWebSecurity:禁用Spring-Boot的默认Secrity配置，配合@Configuration启用自定义配置，需要扩展至WebSecurityConfigrerAdapter
 * @EnableGlobalMethodSecurity(prePostEnabled = true): 启用Security注解，例如最常用的@PreAuthorize
 * @author dgq
 *
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private UserServiceImpl userService;
	
	/**
	 * 
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http
			.formLogin().loginPage("/loginPage").permitAll()			
			.and()
			.authorizeRequests().antMatchers("/", "/index", "/login", "loginPage", "/send2", "/send1").permitAll()
			.anyRequest().authenticated()
			.and()
			.logout().permitAll()
			.and()
			.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
		
	}
	
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
		auth.inMemoryAuthentication()
				.withUser("dgq").password("111111").roles("ADMIN");
	}
	
	/**
	 * Web层面的配置，一般用来配置无需安全检查的路径
	 */
	@Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/js/**", "/css/**", "/images/**", "/**/favicon.ico");
    }
	
	/**
	 * 身份验证配置，用于注入自定义身份验证Bean和密码校验规则
	 */
	@Override
	protected void configure(AuthenticationManagerBuilder auth)
			throws Exception {
		auth.userDetailsService(userService);
	}
	
	/*private CsrfTokenRepository csrfTokenRepository(){
		HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
		
		repository.setSessionAttributeName("_csrf");
		
		return repository;
	}*/
}
