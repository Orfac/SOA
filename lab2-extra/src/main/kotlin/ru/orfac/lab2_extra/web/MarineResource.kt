package ru.orfac.lab2_extra.web

import ru.orfac.lab2_extra.service.SpaceShipService
import javax.ws.rs.*
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

@Path("/")
open class MarineResource {
  private val shipService = SpaceShipService

  @POST
  @Path("/create/{id}/{name}")
  open fun create(@PathParam("id") id: Long, @PathParam("name") name: String): Response {
    shipService.create(id, name)
    return Response.ok().entity("Service online$id $name").build()
  }

  @POST
  @Path("/{id}/load/{marineid}")
  open fun load(@PathParam("id") id: Long, @PathParam("marineid") marineId: Long): Response {
    shipService.update(id, marineId)
    return Response.ok().entity("Service online$id $marineId").build()
  }

  @GET
  @Path("/get")
  @Produces(MediaType.APPLICATION_JSON)
  open fun getNotification(): Response {
    return Response.ok().entity(shipService.get()).build()
  }

}