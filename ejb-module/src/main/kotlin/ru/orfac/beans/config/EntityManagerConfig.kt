package ru.orfac.beans.config

import javax.persistence.EntityManager
import javax.persistence.Persistence

object EntityManagerConfig {
  private var entityManager: EntityManager

  init {
    val pointUnit = Persistence.createEntityManagerFactory("spaceship-unit")
    entityManager = pointUnit.createEntityManager()
  }

  fun getEntityManager(): EntityManager {
    return entityManager
  }

  fun setEntityManager(entityManager: EntityManager) {
    EntityManagerConfig.entityManager = entityManager
  }
}