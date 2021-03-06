package ru.orfac.marine.model.repositories

import org.springframework.data.repository.PagingAndSortingRepository
import ru.orfac.marine.model.AstartesCategory
import ru.orfac.marine.model.SpaceMarine

interface MarineRepository : PagingAndSortingRepository<SpaceMarine, Long> {

  fun findAllByCategory(category: AstartesCategory): List<SpaceMarine>
  fun findAllByHealthGreaterThan(health : Long) : List<SpaceMarine>
}