package cz.srnet.pisqorker.rest;

import org.springframework.lang.NonNull;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

final class RestApiVersions {

	private static final @NonNull String apiPathPrefix = "/api";
	static final @NonNull String v1 = apiPathPrefix + "/1";

	static @NonNull RequestMatcher apiRequestMatcher() {
		return new AntPathRequestMatcher(apiPathPrefix + "/**");
	}

}
