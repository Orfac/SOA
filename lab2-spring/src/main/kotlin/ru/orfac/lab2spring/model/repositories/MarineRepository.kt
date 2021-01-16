package ru.orfac.lab2spring.model.repositories

import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.data.repository.query.Param
import ru.orfac.lab2spring.model.AstartesCategory
import ru.orfac.lab2spring.model.SpaceMarine

interface MarineRepository : PagingAndSortingRepository<SpaceMarine, Long> {

  fun findAllByCategory(category: AstartesCategory): List<SpaceMarine>
  fun findAllByHealthGreaterThan(health : Long) : List<SpaceMarine>
}