package ru.orfac.marine.service.extensions

import ru.orfac.marine.exceptions.RequestHandlingException

fun List<ru.orfac.marine.model.SpaceMarine>.filterByValue(
  filterName: String,
  parameter: String
): List<ru.orfac.marine.model.SpaceMarine> {
  return this.filter {
    when (filterName) {
      "name" -> {
        it.name == parameter
      }
      "id" -> {
        it.id == parameter.toLong()
      }
      "coordinates_x" -> {
        it.coordinates.x == parameter.toLong()
      }
      "coordinates_y" -> {
        it.coordinates.y == parameter.toFloat()
      }
      "creation_date" -> {
        val localDateTimeAdapter =
            ru.orfac.marine.xml.LocalDateTimeAdapter()
        localDateTimeAdapter.marshal(it.creationDate) == parameter
      }
      "health" -> {
        it.health == parameter.toLong()
      }
      "heart_count" -> {
        it.heartCount == parameter.toInt()
      }
      "category" -> {
        it.category == ru.orfac.marine.model.AstartesCategory.valueOf(parameter)
      }
      "melee_weapon" -> {
        it.meleeWeapon == ru.orfac.marine.model.MeleeWeapon.valueOf(parameter)
      }
      "chapter_name" -> {
        it.chapter.name == parameter
      }
      "chapter_parent_legion" -> {
        it.chapter.parentLegion == parameter
      }
      "chapter_marines_count" -> {
        it.chapter.marinesCount == parameter.toLong()
      }
      "chapter_world" -> {
        it.chapter.world == parameter
      }
      else -> {
        throw RequestHandlingException("Bad filter parameter $parameter")
      }
    }
  }
}
