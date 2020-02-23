package com.moons.webApplications.domain

data class CoupangProductData(var productId : String,
                              var productName : String,
                              var productPrice : Int,
                              var productImage : String,
                              var productUrl : String,
                              var categoryName : String? = null,
                              var keyword : String,
                              var rank : Int,
                              var isRocket : Boolean)