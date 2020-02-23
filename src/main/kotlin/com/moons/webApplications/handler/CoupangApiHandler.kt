package com.moons.webApplications.handler

import com.moons.webApplications.service.CoupangApiService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.ServerResponse.ok
import org.springframework.web.reactive.function.server.body
import reactor.core.publisher.Mono

@Component
class CoupangApiHandler {

    @Autowired
    lateinit var testService: CoupangApiService

    fun deeplinkGen(request: ServerRequest) : Mono<ServerResponse> {
        return ok().contentType(MediaType.APPLICATION_JSON)
                .body(testService.deeplinkGen())
    }

    fun bestCategoryGet(request: ServerRequest) : Mono<ServerResponse> {
        var size = request.queryParam("limit").orElse("5").toInt()
        return ok().body(testService.bestCategoryGet(request.pathVariable("categoryId"), size))
    }

    fun productSearch(request: ServerRequest) : Mono<ServerResponse> {
        var size = request.queryParam("limit").orElse("5").toInt()
        return ok().body(testService.productSearch(request.queryParam("product").get(), size))
    }
}