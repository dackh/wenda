package me.dack.wenda.configuration;

import javax.annotation.Resource;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import me.dack.wenda.intercepter.LoginInterceptor;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {

	

	@Resource
	 LoginInterceptor loginInterceptor;


	    @Override
	    public void addInterceptors(InterceptorRegistry registry) {
	        registry.addInterceptor(loginInterceptor).addPathPatterns("/**")
	        	.excludePathPatterns("/user/*");
	    }
	
	

}
