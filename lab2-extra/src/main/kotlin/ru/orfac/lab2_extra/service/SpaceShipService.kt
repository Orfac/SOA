package ru.orfac.lab2_extra.service

import ru.orfac.lab2_extra.client.ClientApi
import ru.orfac.lab2_extra.config.EntityManagerConfig
import ru.orfac.lab2_extra.exceptions.RequestHandlingException
import ru.orfac.lab2_extra.model.SpaceShip
import ru.orfac.lab2_extra.utils.isEnglishAlphabet
import javax.persistence.EntityManager
import kotlin.RuntimeException

object SpaceShipService {

  private const val table = "SpaceShip"
  private val entityManager: EntityManager = EntityManagerConfig.getEntityManager()
  private val clientApi = ClientApi

  fun create(id: Long, name: String) {
    if (!name.isEnglishAlphabet()) {
      throw RequestHandlingException("Ship name should contain only english alphabet letters")
    }
    val spaceShip = SpaceShip(name)
    spaceShip.id = id

    entityManager.transaction.begin()
    entityManager.persist(spaceShip)
    entityManager.transaction.commit()
  }

  fun update(id: Long, marineId: Long) {
    val spaceShip = entityManager.find(SpaceShip::class.java, id)

    val marineIds = spaceShip.marineIds.parseMarineIds()
    if (marineId in marineIds) {
      throw RequestHandlingException("Marine is already loaded")
    }

    if (!clientApi.checkMarine(marineId)) {
      throw RequestHandlingException("Marine was not found")
    }

    if (marineIds.isEmpty()) {
      spaceShip.marineIds = marineId.toString()
    } else {
      spaceShip.marineIds += ",$marineId"
    }
    entityManager.transaction.begin()
    entityManager.persist(spaceShip)
    entityManager.transaction.commit()
  }

  fun get(): List<SpaceShip> {
    val list = entityManager.createQuery("SELECT e FROM $table e")
        .resultList as List<SpaceShip>
    return list.sortedBy { it.id }
  }

  private fun String.parseMarineIds(): List<Long> {
    if (this.isEmpty()) return emptyList()
    return this.split(",").map { it.toLong() }
  }

}