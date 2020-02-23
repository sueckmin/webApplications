package com.moons.webApplications.router

import com.moons.webApplications.handler.CoupangApiHandler
import org.springframework.context.annotation.Bean
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.router

@Component
class Router {

    @Bean
    fun routerFunction(coupangApiHandler: CoupangApiHandler) = router {
        accept(MediaType.APPLICATION_JSON).nest {
            "/v1".nest {
                GET("/deeplink", coupangApiHandler::deeplinkGen)
                GET("/bestcategory/{categoryId}", coupangApiHandler::bestCategoryGet)
                GET("/product", coupangApiHandler::productSearch)
            }
        }
    }
}