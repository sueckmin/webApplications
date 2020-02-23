package com.moons.webApplications.domain

data class CoupangSearchRes (var rCode : String,
                             var rMessage : String,
                             var data : data)

data class data (var landingUrl : String,
                 var productData : ArrayList<CoupangProductData>)