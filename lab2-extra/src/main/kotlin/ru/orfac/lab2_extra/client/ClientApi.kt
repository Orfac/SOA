package ru.orfac.lab2_extra.client

import java.lang.System.getenv
import javax.ws.rs.client.Client
import javax.ws.rs.client.ClientBuilder
import javax.ws.rs.core.MediaType

object ClientApi {
  private const val API_ENV_VARIABLE = "API_URL"
  private val apiEndpointUrl: String = getApiUrl()
  private val client: Client = buildClient()

  fun checkMarine(id: Long): Boolean {
    val marineResponse = client
        .target(apiEndpointUrl)
        .path(id.toString())
        .request(MediaType.APPLICATION_XML)
        .get()
    return marineResponse.status < 400
  }

  private fun buildClient(): Client {
    return ClientBuilder.newBuilder().hostnameVerifier { _, _ -> true }.build()
  }

  private fun getApiUrl(): String {
    return getenv().getOrDefault(
        API_ENV_VARIABLE,
        "https://localhost:8443/lab2-spring-0.0.1-SNAPSHOT/marines")
  }
}