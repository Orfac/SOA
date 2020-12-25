package ru.orfac.lab2spring.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import org.springframework.web.filter.CorsFilter

@Configuration
class CorsFilterConfiguration {
  @Bean
  fun corsFilter(): CorsFilter {
    val source = UrlBasedCorsConfigurationSource()
    val config = CorsConfiguration()
    config.allowCredentials = true
    config.addAllowedOrigin("*") // this allows all origin
    config.addAllowedHeader("*") // this allows all headers
    config.addAllowedMethod("OPTIONS")
    config.addAllowedMethod("HEAD")
    config.addAllowedMethod("GET")
    config.addAllowedMethod("PUT")
    config.addAllowedMethod("POST")
    config.addAllowedMethod("DELETE")
    config.addAllowedMethod("PATCH")
    source.registerCorsConfiguration("/**", config)
    return CorsFilter(source)
  }
}