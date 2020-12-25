package ru.orfac.lab2main.model.repositories

import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.data.repository.query.Param
import ru.orfac.lab2main.model.AstartesCategory
import ru.orfac.lab2main.model.SpaceMarine

interface MarineRepository : PagingAndSortingRepository<SpaceMarine, Long> {

  @Query(value = "SELECT p  FROM SpaceMarine  p  ORDER BY :#{#attributes}")
  fun findSpaceMarinesByAttributes(@Param("attributes") attributes: String): List<SpaceMarine>
  fun findAllByCategory(category: AstartesCategory): List<SpaceMarine>
  fun findAllByHealthGreaterThan(health : Long) : List<SpaceMarine>
}