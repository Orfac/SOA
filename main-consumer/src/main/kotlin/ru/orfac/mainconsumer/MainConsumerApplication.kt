package ru.orfac.mainconsumer

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient

@SpringBootApplication
@EnableDiscoveryClient
open class MainConsumerApplication

fun main(args: Array<String>) {
  runApplication<MainConsumerApplication>(*args)
}
