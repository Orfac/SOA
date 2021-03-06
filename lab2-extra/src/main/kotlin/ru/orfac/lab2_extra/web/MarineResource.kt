package ru.orfac.lab2_extra.web

import ru.orfac.shared.RequestHandlingException
import ru.orfac.shared.Service
import javax.ejb.EJB
import javax.naming.InitialContext
import javax.ws.rs.*
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response
import java.util.Hashtable
import javax.ejb.EJBException
import javax.naming.Context

@Path("/")
open class MarineResource {
  public lateinit var shipService: Service

  init {
    lookupForShipService()
  }

  @POST
  @Path("/create/{id}/{name}")
  open fun create(@PathParam("id") id: Long?, @PathParam("name") name: String?): Response {
    if (id == null || name == null) {
      throw RequestHandlingException("Required long(integer value) id and english letters string name")
    }
    try {
      shipService.create(id, name)
    } catch (ex: EJBException) {
      return Response.status(400)
          .entity(ex.message)
          .build()
    }
    return Response.ok().build()
  }

  @POST
  @Path("/{id}/load/{marineid}")
  open fun load(@PathParam("id") id: Long?, @PathParam("marineid") marineId: Long?): Response {
    if (id == null || marineId == null) {
      throw RequestHandlingException("Required long(integer value) id and long marineId")
    }
    try {
      shipService.update(id, marineId)
    } catch (ex: EJBException) {
      return Response.status(if (ex.message == "Marine server does not respond") 500 else 400)
          .entity(ex.message)
          .build()
    }
    return Response.ok().build()
  }

  @GET
  @Path("/get")
  @Produces(MediaType.APPLICATION_JSON)
  open fun getShips(): Response {
    return try {
      Response.ok().entity(shipService.get()).build()
    } catch (ex: RequestHandlingException) {
      Response.status( 400)
          .entity(ex.message)
          .build()
    }

  }

  private fun lookupForShipService() {
    val jndiProperties: Hashtable<String, String> = Hashtable<String, String>()
    jndiProperties[Context.INITIAL_CONTEXT_FACTORY] =
        "org.wildfly.naming.client.WildFlyInitialContextFactory"
    jndiProperties[Context.PROVIDER_URL] = System.getenv().getOrDefault(
        "PROVIDER_URL",
        "http-remoting://localhost:8081")
    val context: Context = InitialContext(jndiProperties)
    shipService =
        context.lookup("ejb:/ejb-archive/MarineService!ru.orfac.shared.Service") as Service
  }
}