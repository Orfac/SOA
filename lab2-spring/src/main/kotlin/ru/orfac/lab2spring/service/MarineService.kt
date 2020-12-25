package ru.orfac.lab2spring.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.stereotype.Service
import ru.orfac.lab2spring.exceptions.NotFoundException
import ru.orfac.lab2spring.model.AstartesCategory
import ru.orfac.lab2spring.model.SpaceMarine
import ru.orfac.lab2spring.model.repositories.MarineRepository
import ru.orfac.lab2spring.rest.extensions.PageableParameters
import ru.orfac.lab2spring.rest.extensions.parseMarineCollectionDto
import ru.orfac.lab2spring.service.extenstions.filterByValue
import kotlin.math.min
import kotlin.random.Random

@Service
class MarineService {
  @Autowired
  lateinit var marineRepository: MarineRepository

  fun findById(id: Long): SpaceMarine {
    return marineRepository.findById(id)
        .orElseThrow { NotFoundException("Marine with id=$id was not found") }
  }

  fun saveNew(spaceMarine: SpaceMarine) {
    if (spaceMarine.id == null) {
      marineRepository.save(spaceMarine)
    } else {
      throw RuntimeException("New marine should not have id")
    }
  }

  fun getMarines(): List<SpaceMarine> {
    return marineRepository.findAll().map { it!! }
  }

  fun getMarines(parameters: Map<String, String>): List<SpaceMarine> {
    val dto = parseMarineCollectionDto(parameters)
    var marines = if (dto.sortSequence != null) {
      marineRepository.findSpaceMarinesByAttributes(dto.sortSequence)
    } else getMarines()

    dto.filterList?.forEach { filterAndValue ->
      marines = marines.filterByValue(filterAndValue.first, filterAndValue.second)
    }
    return if (dto.pageableParameters != null) {
      processPageable(marines, dto.pageableParameters)
    } else {
      marines
    }
  }

  fun updateMarineById(id: Long, marine: SpaceMarine) {
    var existedMarine = findById(id)
    existedMarine.category = marine.category
    existedMarine.chapter = marine.chapter
    existedMarine.coordinates = marine.coordinates
    existedMarine.health = marine.health
    existedMarine.heartCount = marine.heartCount
    existedMarine.meleeWeapon = marine.meleeWeapon
    existedMarine.name = marine.name
    marineRepository.save(existedMarine)
  }

  fun deleteMarineById(id: Long) {
    try {
      marineRepository.deleteById(id)
    } catch (ex: EmptyResultDataAccessException) {
      throw NotFoundException("Marine with id=${id} not exist or already removed")
    }
  }

  private fun processPageable(
    marines: List<SpaceMarine>,
    pageableParameters: PageableParameters
  ): List<SpaceMarine> {
    val firstIndex = pageableParameters.pageSize * (pageableParameters.pageIndex - 1)
    val secondIndex =
        min(pageableParameters.pageSize * (pageableParameters.pageIndex), marines.size)
    return marines.slice(firstIndex until secondIndex)
  }

  fun deleteRandomByCategory(category: AstartesCategory) {
    val marines = marineRepository.findAllByCategory(category)
    if (marines.isEmpty()) return
    val randomIndex = Random.nextInt(0, marines.size)
    marineRepository.delete(marines[randomIndex])
  }

  fun marinesWithHpGreater(health: Long): List<SpaceMarine> {
    return marineRepository.findAllByHealthGreaterThan(health)
  }
}