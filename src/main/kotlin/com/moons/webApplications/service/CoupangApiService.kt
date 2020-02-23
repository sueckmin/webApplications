package com.moons.webApplications.service

import com.fasterxml.jackson.databind.util.JSONPObject
import com.moons.webApplications.util.CoupangAuthUtils
import org.apache.http.HttpEntity
import org.apache.http.HttpHost
import org.apache.http.HttpRequest
import org.apache.http.HttpResponse
import org.apache.http.client.methods.RequestBuilder
import org.apache.http.entity.StringEntity
import org.apache.http.impl.client.HttpClientBuilder
import org.apache.http.util.EntityUtils
import org.bson.Document
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class CoupangApiService {
    val REQUEST_POST_METHOD = "POST"
    val REQUEST_GET_METHOD = "GET"
    val DOMAIN = "https://api-gateway.coupang.com"
    val DEEPLINK_URL = "/v2/providers/affiliate_open_api/apis/openapi/deeplink"
    val BEST_CATEGORIES_URL = "/v2/providers/affiliate_open_api/apis/openapi/products/bestcategories/"
    var SEARCH_URL = "/v2/providers/affiliate_open_api/apis/openapi/products/search?keyword="
    //val BEST_CATEGORIES_URL = "/v2/providers/affiliate_open_api/apis/openapi/products/bestcategories/1001?limit=50"
    //var SEARCH_URL = "/v2/providers/affiliate_open_api/apis/openapi/products/search?keyword=food&limit50"
    val ACCESS_KEY = "f25da0e6-4323-4329-b38f-a3f740e55346"
    val SECRET_KEY = "560a8363b6c7929215c4c8512817de32761025b7"
    val REQUEST_JSON = "{\"coupangUrls\": [\"https://www.coupang.com/np/search?component=&q=good&channel=user\",\"https://www.coupang.com/np/coupangglobal\"]}"

    @Autowired
    lateinit var coupangService: CoupangAuthUtils

    fun deeplinkGen() : Mono<String> {
        var authorization = coupangService.generate(REQUEST_POST_METHOD, DEEPLINK_URL, SECRET_KEY, ACCESS_KEY)
        var entity : StringEntity = StringEntity(REQUEST_JSON, "UTF-8")
        entity.setContentEncoding("UTF-8")
        entity.setContentType("application/json")

        var host : HttpHost = HttpHost.create(DOMAIN)
        var requst : HttpRequest = RequestBuilder.post(DEEPLINK_URL)
                .setEntity(entity).addHeader("Authorization", authorization).build()

        var httpResponse : HttpResponse = HttpClientBuilder.create().build().execute(host, requst)
        var result = EntityUtils.toString(httpResponse.entity)
        return Mono.just(result)
    }

    fun bestCategoryGet(category : String, size : Int) : Mono<String> {
        var url = BEST_CATEGORIES_URL + category + "?limit="+size
        var authorization = coupangService.generate(REQUEST_GET_METHOD, url, SECRET_KEY, ACCESS_KEY)

        var entity : StringEntity = StringEntity(REQUEST_JSON, "UTF-8")
        entity.setContentEncoding("UTF-8")
        entity.setContentType("application/json")

        var host : HttpHost = HttpHost.create(DOMAIN)
        var requst : HttpRequest = RequestBuilder.get(url)
                .setEntity(entity).addHeader("Authorization", authorization).build()
        var httpResponse : HttpResponse = HttpClientBuilder.create().build().execute(host, requst)
        var result = EntityUtils.toString(httpResponse.entity)
        return Mono.just(result)
    }

    fun productSearch(product: String, size: Int) : Mono<String> {
        var url = SEARCH_URL+product+"&limit="+size
        var authorization = coupangService.generate(REQUEST_GET_METHOD, url, SECRET_KEY, ACCESS_KEY)
        println(authorization)
        var entity : StringEntity = StringEntity(REQUEST_JSON, "UTF-8")
        entity.setContentEncoding("UTF-8")
        entity.setContentType("application/json")

        var host : HttpHost = HttpHost.create(DOMAIN)
        var requst : HttpRequest = RequestBuilder.get(url)
                .setEntity(entity).addHeader("Authorization", authorization).build()
        var httpResponse : HttpResponse = HttpClientBuilder.create().build().execute(host, requst)
        var result = EntityUtils.toString(httpResponse.entity)
        return Mono.just(result)
    }
}