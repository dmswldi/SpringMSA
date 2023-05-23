package com.optimagrowth.license;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;

// 이 클래스가 bean을 정의하는 출처라고 스프링 컨테이너에 알림
// 1. 내부적으로 애플리케이션 클래스를 configuration 클래스로 지정
// 2. 자바 클래스 경로에 있는 모든 클래스를 자동으로 스캔
@SpringBootApplication
@RefreshScope// config 데이터 변경 시 서버 재실행 기능
//@EnableDiscoveryClient// 1. Eureka Discovery Client
@EnableFeignClients// 3. Feign Client
public class LicensingServiceApplication {

    public static void main(String[] args) {
        // ApplicationContext 객체를 반환
        SpringApplication.run(LicensingServiceApplication.class, args);
    }

    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver localeResolver = new SessionLocaleResolver();
        localeResolver.setDefaultLocale(Locale.US);// 기본 로케일 설정
        return localeResolver;
    }

    @Bean
    public ResourceBundleMessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setUseCodeAsDefaultMessage(true);// 메시지가 발견되지 않아도 에러를 던지지 않고 메시지 코드를 반환
        messageSource.setBasenames("messages");// 언어 프로퍼티 파일의 기본 이름을 설정
        return messageSource;
    }

    @LoadBalanced// 2. LB를 지원하는 Rest Template
    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

}
