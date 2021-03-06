package ru.orfac.marine.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "marine")
class MarineUpdateConfiguration {
  public var loadNewValues: Boolean? = false

}