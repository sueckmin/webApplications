package com.moons.webApplications

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.reactive.config.EnableWebFlux

@SpringBootApplication
@EnableWebFlux
class WebApplicationsApplication

fun main(args: Array<String>) {
	runApplication<WebApplicationsApplication>(*args)
}