package ru.orfac.lab2_extra.web

import ru.orfac.lab2_extra.exceptions.RequestHandlingException
import ru.orfac.lab2_extra.service.SpaceShipService
import javax.ws.rs.*
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

@Path("/")
open class MarineResource {
  private val shipService = SpaceShipService

  @POST
  @Path("/create/{id}/{name}")
  open fun create(@PathParam("id") id: Long?, @PathParam("name") name: String?): Response {
    if (id == null || name == null){
      throw RequestHandlingException("Required long(integer value) id and english letters string name")
    }
    shipService.create(id, name)
    return Response.ok().build()
  }

  @POST
  @Path("/{id}/load/{marineid}")
  open fun load(@PathParam("id") id: Long?, @PathParam("marineid") marineId: Long?): Response {
    if (id == null || marineId == null){
      throw RequestHandlingException("Required long(integer value) id and long marineId")
    }
    shipService.update(id, marineId)
    return Response.ok().build()
  }

  @GET
  @Path("/get")
  @Produces(MediaType.APPLICATION_JSON)
  open fun getShips(): Response {
    return Response.ok().entity(shipService.get()).build()
  }

}