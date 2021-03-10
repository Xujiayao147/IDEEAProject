package top.xujiayao.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.access.AccessDeniedHandler;
import top.xujiayao.service.UserService;

import java.io.PrintWriter;

/**
 * @author Xujiayao
 */
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	final UserService userService;

	public WebSecurityConfig(UserService userService) {
		this.userService = userService;
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userService);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			  .antMatchers("/admin/**", "/reg").hasRole("超级管理员")///admin/**的URL都需要有超级管理员角色，如果使用.hasAuthority()方法来配置，需要在参数中加上ROLE_,如下.hasAuthority("ROLE_超级管理员")
			  .anyRequest().authenticated()//其他的路径都是登录后即可访问
			  .and().formLogin().loginPage("/login_page").successHandler((httpServletRequest, httpServletResponse, authentication) -> {
			httpServletResponse.setContentType("application/json;charset=utf-8");
			PrintWriter out = httpServletResponse.getWriter();
			out.write("{\"status\":\"success\",\"msg\":\"登录成功\"}");
			out.flush();
			out.close();
		})
			  .failureHandler((httpServletRequest, httpServletResponse, e) -> {
				  httpServletResponse.setContentType("application/json;charset=utf-8");
				  PrintWriter out = httpServletResponse.getWriter();
				  out.write("{\"status\":\"error\",\"msg\":\"登录失败\"}");
				  out.flush();
				  out.close();
			  }).loginProcessingUrl("/login")
			  .usernameParameter("username").passwordParameter("password").permitAll()
			  .and().logout().permitAll().and().csrf().disable().exceptionHandling().accessDeniedHandler(getAccessDeniedHandler());
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/blogimg/**", "/index.html", "/static/**");
	}

	@Bean
	AccessDeniedHandler getAccessDeniedHandler() {
		return new AuthenticationAccessDeniedHandler();
	}
}