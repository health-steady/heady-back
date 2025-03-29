package com.heady.headyback.common.config;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.heady.headyback.auth.context.LoginArgumentResolver;
import com.heady.headyback.auth.jwt.JwtInterceptor;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

	private final JwtInterceptor jwtInterceptor;
	private final LoginArgumentResolver loginArgumentResolver;

	@Override
	public void addCorsMappings(final CorsRegistry registry) {
		registry.addMapping("/**")
				.allowedOrigins("http://localhost:3000")
				.allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS")
				.allowCredentials(true)
				.exposedHeaders(HttpHeaders.LOCATION);
		WebMvcConfigurer.super.addCorsMappings(registry);
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(jwtInterceptor)
				.addPathPatterns("/api/**")
				.excludePathPatterns("/auth/**"); // 인증 없는 경로 제외
	}

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
		resolvers.add(loginArgumentResolver);
	}
}
