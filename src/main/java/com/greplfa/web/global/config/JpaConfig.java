package com.greplfa.web.global.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@RequiredArgsConstructor
@Configuration
@EnableJpaAuditing
public class JpaConfig {
}
