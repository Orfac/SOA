package ru.orfac.marine

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient

@SpringBootApplication
@EnableDiscoveryClient
class MarineApplication

fun main(args: Array<String>) {
  runApplication<MarineApplication>(*args)
}
