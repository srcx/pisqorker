package cz.srnet.pisqorker.rest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.RequestMatcher;

@Configuration
@EnableWebSecurity
@Order(1)
class RestSecurityConfig extends WebSecurityConfigurerAdapter {

	private final @NonNull TokenAuthenticationProvider provider;

	public RestSecurityConfig(@NonNull TokenAuthenticationProvider provider) {
		super();
		this.provider = provider;
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) {
		auth.authenticationProvider(provider);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		RequestMatcher apiRequestMatcher = RestApiVersions.apiRequestMatcher();
		http.requestMatcher(apiRequestMatcher).sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().exceptionHandling()
				.defaultAuthenticationEntryPointFor(forbiddenEntryPoint(), apiRequestMatcher).and()
				.authenticationProvider(provider)
				.addFilterBefore(restAuthenticationFilter(), AnonymousAuthenticationFilter.class).authorizeRequests()
				.requestMatchers(apiRequestMatcher).authenticated().and().csrf().disable().formLogin().disable()
				.httpBasic().disable().logout().disable();
	}

	@Bean
	TokenAuthenticationFilter restAuthenticationFilter() throws Exception {
		TokenAuthenticationFilter filter = new TokenAuthenticationFilter(RestApiVersions.apiRequestMatcher());
		filter.setAuthenticationManager(authenticationManager());
		filter.setAuthenticationSuccessHandler(successHandler());
		return filter;
	}

	@Bean
	SimpleUrlAuthenticationSuccessHandler successHandler() {
		SimpleUrlAuthenticationSuccessHandler successHandler = new SimpleUrlAuthenticationSuccessHandler();
		successHandler.setRedirectStrategy(new NoRedirectStrategy());
		return successHandler;
	}

	@Bean
	AuthenticationEntryPoint forbiddenEntryPoint() {
		return new HttpStatusEntryPoint(HttpStatus.FORBIDDEN);
	}

}
