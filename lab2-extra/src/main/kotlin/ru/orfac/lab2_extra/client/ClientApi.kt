package ru.orfac.lab2_extra.client

import javax.ws.rs.client.ClientBuilder
import javax.ws.rs.core.MediaType

object ClientApi {
  private val client = ClientBuilder.newClient()
  private const val API_URL = "https://localhost:8443/marines"
  fun checkMarine(id: Long): Boolean {
    val marineResponse = client
        .target(API_URL)
        .path(id.toString())
        .request(MediaType.APPLICATION_XML)
        .get()
    print(marineResponse)
    return marineResponse.status < 400
  }
}