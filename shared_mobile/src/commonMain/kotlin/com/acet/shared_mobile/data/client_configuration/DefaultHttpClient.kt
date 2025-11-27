package com.acet.shared_mobile.client_configuration

import com.acet.shared_mobile.client_configuration.DefaultClient.BASE_HOST
import com.acet.shared_mobile.client_configuration.DefaultClient.BEARER_TOKEN
import io.ktor.client.HttpClient
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.URLProtocol
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

object DefaultClient {
    const val BASE_HOST = "api.themoviedb.org"
    const val REQUEST_TIMEOUT = 30000L
    const val CONNECT_TIMEOUT = 60000L
    const val BEARER_TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI5ZTYzYTAwYWIzM2YwZjVlZTE3ZmRkZjFhNzE0YzFlMCIsIm5iZiI6MTc2NDI1Njg4Ny43NjE5OTk4LCJzdWIiOiI2OTI4NmM3NzkyZWRjNjQwZWQyNDhlYWEiLCJzY29wZXMiOlsiYXBpX3JlYWQiXSwidmVyc2lvbiI6MX0.yARshpDwC0N-o56NNKnOIS-DT2XemJGD6lHF_l2QoTY"
}

class DefaultHttpClient {
    fun install() = HttpClient {
        expectSuccess = true

        install(Logging) {
            logger =
                object : Logger {
                    override fun log(message: String) {
                        println(message)
                    }
                }
            level = LogLevel.BODY
        }

        install(ContentNegotiation) {
            json(
                Json {
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true
                    encodeDefaults = true
                },
            )
        }

        install(HttpTimeout) {
            connectTimeoutMillis = DefaultClient.CONNECT_TIMEOUT
            requestTimeoutMillis = DefaultClient.REQUEST_TIMEOUT
        }

        install(DefaultRequest) {
            url {
                protocol = URLProtocol.HTTPS
                host = BASE_HOST
            }

            header("accept", "\"application/json\"")
            header("Authorization", "Bearer $BEARER_TOKEN")
            contentType(ContentType.Application.Json)
        }
    }
}