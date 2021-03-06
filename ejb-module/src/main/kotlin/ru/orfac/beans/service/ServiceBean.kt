package ru.orfac.beans.service

import com.fasterxml.jackson.databind.ObjectMapper
import ru.orfac.beans.client.ClientApi
import ru.orfac.beans.config.EntityManagerConfig
import ru.orfac.beans.model.SpaceShip
import ru.orfac.beans.utils.isEnglishAlphabet
import ru.orfac.shared.RequestHandlingException
import ru.orfac.shared.Service
import javax.ejb.EJBException
import javax.ejb.Remote
import javax.ejb.Stateless
import javax.persistence.EntityManager

@Stateless(name = "MarineService")
@Remote(Service::class)
open class ServiceBean : Service {

  var entityManager: EntityManager = EntityManagerConfig.getEntityManager()
  private val mapper = ObjectMapper()
  private val clientApi = ClientApi

  open override fun create(id: Long, name: String) {
    try {
      checkNameContainsEnglishLettersOnly(name)
      checkShipIsNotExisted(id)

      val spaceShip = SpaceShip(name)
      spaceShip.id = id

      save(spaceShip)
    } catch (ex: RequestHandlingException) {
      throw EJBException(ex.message)
    }

  }

  open override fun update(id: Long, marineId: Long) {
    try {
      val spaceShip = findSpaceShip(id)
      val marineIds = spaceShip.marineIds.parseMarineIds()

      checkMarineIsNotLoadedBefore(marineId, marineIds)
      checkMarineExists(marineId)

      if (marineIds.isEmpty()) {
        spaceShip.marineIds = marineId.toString()
      } else {
        spaceShip.marineIds += ",$marineId"
      }
      save(spaceShip)
    } catch (ex: RequestHandlingException) {
      throw EJBException(ex.message)
    }
  }

  private fun checkMarineIsNotLoadedBefore(
    marineId: Long,
    marineIds: List<Long>
  ) {
    if (marineId in marineIds) {
      throw RequestHandlingException("Marine is already loaded")
    }
  }

  private fun checkMarineExists(marineId: Long) {
    if (!clientApi.checkMarine(marineId)) {
      throw RequestHandlingException("Marine was not found")
    }
  }

  open override fun get(): List<String> {
    try {
      val list = entityManager.createQuery("SELECT e FROM $table e")
          .resultList as List<SpaceShip>
      return list.sortedBy { it.id }.map { mapper.writeValueAsString(it) }
    } catch (ex: RequestHandlingException) {
      throw EJBException(ex.message)
    }
  }

  private fun findSpaceShip(id: Long): SpaceShip {
    return entityManager.find(SpaceShip::class.java, id)
      ?: throw RequestHandlingException("Spaceship with id $id doesn't exist")
  }

  private fun checkShipIsNotExisted(id: Long) {
    val existedSpaceShip = entityManager.find(SpaceShip::class.java, id)
    if (existedSpaceShip != null) {
      throw RequestHandlingException("Ship with id $id already existed")
    }
  }

  private fun checkNameContainsEnglishLettersOnly(name: String) {
    if (!name.isEnglishAlphabet()) {
      throw RequestHandlingException("Ship name should contain only english alphabet letters")
    }
  }

  private fun String.parseMarineIds(): List<Long> {
    if (this.isEmpty()) return emptyList()
    return this.split(",").map { it.toLong() }
  }

  private fun save(spaceShip: SpaceShip) {
    entityManager.transaction.begin()
    entityManager.persist(spaceShip)
    entityManager.transaction.commit()
  }

  companion object {
    private const val table = "SpaceShip"
  }

}