package ru.orfac.lab2_extra.exceptions

import javax.ws.rs.core.Response
import javax.ws.rs.ext.ExceptionMapper
import javax.ws.rs.ext.Provider

@Provider
class RequestHandlingExceptionMapper : ExceptionMapper<RequestHandlingException> {
  override fun toResponse(ex: RequestHandlingException): Response {
    return Response.status(400)
        .entity(ex.message)
        .build()
  }
}