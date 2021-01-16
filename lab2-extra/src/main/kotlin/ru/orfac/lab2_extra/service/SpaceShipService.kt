package ru.orfac.lab2_extra.service

import ru.orfac.lab2_extra.client.ClientApi
import ru.orfac.lab2_extra.config.EntityManagerConfig
import ru.orfac.lab2_extra.exceptions.RequestHandlingException
import ru.orfac.lab2_extra.model.SpaceShip
import ru.orfac.lab2_extra.utils.isEnglishAlphabet
import javax.persistence.EntityManager

object SpaceShipService {

  private const val table = "SpaceShip"
  private val entityManager: EntityManager = EntityManagerConfig.getEntityManager()
  private val clientApi = ClientApi

  fun create(id: Long, name: String) {
    checkNameContainsEnglishLettersOnly(name)
    checkShipIsNotExisted(id)

    val spaceShip = SpaceShip(name)
    spaceShip.id = id

    save(spaceShip)
  }



  fun update(id: Long, marineId: Long) {
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

  fun get(): List<SpaceShip> {
    val list = entityManager.createQuery("SELECT e FROM $table e")
        .resultList as List<SpaceShip>
    return list.sortedBy { it.id }
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

}