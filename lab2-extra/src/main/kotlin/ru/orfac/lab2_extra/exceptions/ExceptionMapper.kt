package ru.orfac.lab2_extra.exceptions

import javax.ws.rs.core.Response
import javax.ws.rs.ext.ExceptionMapper

class MapperA : ExceptionMapper<RequestHandlingException> {
  override fun toResponse(ex: RequestHandlingException): Response {
    return Response.status(400)
        .entity("Mapper A: " + ex.message)
        .build()
  }
}