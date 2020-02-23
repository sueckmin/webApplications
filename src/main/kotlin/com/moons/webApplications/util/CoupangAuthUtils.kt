package com.moons.webApplications.util

import org.apache.commons.codec.binary.Hex
import org.springframework.stereotype.Service
import java.nio.charset.Charset
import java.security.GeneralSecurityException
import java.text.SimpleDateFormat
import java.util.*
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

@Service
class CoupangAuthUtils {
    val ALGORITHM : String = "HmacSHA256"
    val STANDARD_CHARSET : Charset = Charset.forName("UTF-8")

    fun generate(method : String, uri : String, secretKey : String, accessKey : String) : String {
        var parts = uri.split("?")

        if (parts.size > 2) {
            throw RuntimeException("incorrect uri format")
        } else {
            println(parts)
            var path = parts[0]
            var query = ""
            if (parts.size == 2) {
                query = parts[1]
            }

            var dateFormatGmt = SimpleDateFormat("yyMMdd'T'HHmmss'Z'")
            dateFormatGmt.timeZone = TimeZone.getTimeZone("GMT")
            var datetime = dateFormatGmt.format(Date())
            var message = datetime + method + path + query
            var signature : String
            try {
                var signingKey : SecretKeySpec = SecretKeySpec(secretKey.toByteArray(STANDARD_CHARSET), ALGORITHM)
                var mac = Mac.getInstance(ALGORITHM)
                mac.init(signingKey)
                var rawHmac : ByteArray = mac.doFinal(message.toByteArray(STANDARD_CHARSET))
                signature = Hex.encodeHexString(rawHmac)
            } catch (e : GeneralSecurityException) {
                throw IllegalArgumentException("Unexpected error while creating hash: " + e.message, e)
            }
            var result = String.format("CEA algorithm=%s, access-key=%s, signed-date=%s, signature=%s", "HmacSHA256", accessKey, datetime, signature)
            return result
        }
    }
}