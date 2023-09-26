package org.boot.hf.admin.config;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.boot.hf.admin.AppUtils;
import org.quincy.rock.core.cache.ObjectCache;
import org.quincy.rock.core.vo.Result;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.Nullable;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.access.vote.RoleVoter;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.DefaultFilterInvocationSecurityMetadataSource;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

/**
	* <b>SecurityConfig。</b>
	* <p><b>详细说明：</b></p>
	* <!-- 在此添加详细说明 -->
	* 无。
	* 
	* @version 1.0
	* @author mex2000
	* @since 1.0
	*/
@Configuration
@SuppressWarnings({ "unchecked", "rawtypes" })
public class SpringSecurityConfig {
	@Bean
	public AuthenticationProvider authenticationProvider(UserDetailsService userDetailsService,
			PasswordEncoder passwordEncoder) {
		//登录认证Provider
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setHideUserNotFoundExceptions(false);
		provider.setUserDetailsService(userDetailsService);
		provider.setPasswordEncoder(passwordEncoder);
		return provider;
	}

	@Bean
	public PersistentTokenRepository persistentTokenRepository(DataSource dataSource) {
		//Token持久化Dao
		JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
		tokenRepository.setDataSource(dataSource);
		tokenRepository.setCreateTableOnStartup(false);
		return tokenRepository;
	}

	@Bean
	public SessionRegistry sessionRegistry() {
		//会话管理
		return new SessionRegistryImpl();
	}

	@Bean
	public AccessDecisionManager accessDecisionManager() {
		//投票决定请求是否通过
		RoleVoter voter = new RoleVoter();
		AffirmativeBased adm = new AffirmativeBased(Arrays.asList(voter));
		adm.setAllowIfAllAbstainDecisions(false); //弃权算反对票
		return adm;
	}

	@Bean
	public FilterInvocationSecurityMetadataSource securityMetadataSource() {
		LinkedHashMap<RequestMatcher, Collection<ConfigAttribute>> requestMap = new LinkedHashMap<>();
		requestMap.put(new AntPathRequestMatcher("/employee/**"), SecurityConfig.createList("ROLE_ADMIN"));
		//requestMap.put(new AntPathRequestMatcher("/job/**"), SecurityConfig.createList("ROLE_ADMIN"));
		DefaultFilterInvocationSecurityMetadataSource securityMetadataSource = new DefaultFilterInvocationSecurityMetadataSource(
				requestMap);
		return securityMetadataSource;
	}

	@Bean
	public SecurityFilterChain securityFilterChain(@Nullable HttpSecurity http,
			PersistentTokenRepository tokenRepository, UserDetailsService userDetailsService) throws Exception {
		if (http == null)
			return null;
		//允许匿名通过的url
		http.authorizeRequests().antMatchers("/css/**", "/fonts/**", "/images/**", "/js/**", "/pagecss/**",
				"/pagejs/**", "/index.html", "/loginUser", "/captcha.jpg").permitAll();
		//权限
		http.authorizeRequests().antMatchers("/employee/**").hasAnyRole("ADMIN", "USER").anyRequest().authenticated();

		//exception
		http.exceptionHandling().accessDeniedHandler((req, resp, e) -> {
			Result<Boolean> result = Result.toResult("1008", "未授权!", e);
			AppUtils.writeJson(resp, result.result(false));
		});
		
		//login
		http.formLogin().loginPage("/login.html").loginProcessingUrl("/login").defaultSuccessUrl("/loginSuccess", true)
				.failureHandler((req, resp, e) -> {
					Result<Boolean> result;
					if (e instanceof LockedException) {
						result = Result.toResult("1004", "账户被锁定!");
					} else if (e instanceof DisabledException) {
						result = Result.toResult("1005", "账户被禁用!");
					} else if (e instanceof AccountExpiredException || e instanceof CredentialsExpiredException) {
						result = Result.toResult("1006", "账户或密码已过期!");
					} else {
						result = Result.toResult("1001", "账户名或密码输入错误!", e);
					}
					AppUtils.writeJson(resp, result.result(false));
				}).permitAll();
		//logout
		http.logout().permitAll();
		//captcha
		http.addFilterBefore((req, resp, chain) -> {
			HttpServletRequest request = ((HttpServletRequest) req);
			AntPathRequestMatcher ant = new AntPathRequestMatcher("/login");
			if (AppUtils.useCaptcha && ant.matches(request)) {
				String captcha = req.getParameter("captcha");
				ObjectCache<String> oc = (ObjectCache) request.getSession()
						.getAttribute(AppUtils.PUBLIC_LAST_CAPTCHA_KEY);
				String verifyCode = oc == null ? null : oc.get();
				if (verifyCode == null || !verifyCode.equals(captcha)) {
					AppUtils.writeJson(resp, Result.toResult("1003", "验证码不正确!").result(Boolean.FALSE));
					return;
				}
			}
			chain.doFilter(req, resp);
		}, UsernamePasswordAuthenticationFilter.class);
		//rememberMe
		http.rememberMe().tokenRepository(tokenRepository).tokenValiditySeconds(3600).rememberMeParameter("rememberMe")
				.rememberMeCookieName("rememberMe").key(PasswordConfig.SECURE_KEY_STRING);
		//session
		http.sessionManagement().maximumSessions(1).maxSessionsPreventsLogin(false).expiredSessionStrategy(event -> {
			AppUtils.writeJson(event.getResponse(), Result.toResult("1009", "你被踢了!").result(Boolean.FALSE));
		});
		//
		return http.headers().frameOptions().sameOrigin().and().csrf().disable().build();
	}
}
