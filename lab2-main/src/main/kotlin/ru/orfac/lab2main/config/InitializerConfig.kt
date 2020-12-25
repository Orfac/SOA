package ru.orfac.lab2main.config

import org.springframework.context.annotation.Configuration
import org.springframework.core.annotation.Order
import ru.orfac.lab2main.model.*
import ru.orfac.lab2main.service.MarineService

@Configuration
class InitializerConfig(var marineService: MarineService) {

  private fun getMarine(name: String): SpaceMarine {
    val coordinates = Coordinates(1, 2f)
    val chapter = Chapter("Thousand_of_sons", "legion1", 1000, "Prospero")
    return SpaceMarine(
        name,
        coordinates,
        1,
        1,
        AstartesCategory.AGGRESSOR,
        MeleeWeapon.CHAIN_SWORD,
        chapter)
  }

  private final val marinesInit =
      listOf(
          getMarine("vasya"), getMarine("petya"),
          getMarine("Vova"), getMarine("Immanuil1"),
          getMarine("vova0"), getMarine("Immanuil2"),
          getMarine("vova1"), getMarine("Immanuil Velikiy"),
          getMarine("vova2123"), getMarine("Privet kto"),
          getMarine("vova3123"), getMarine("Ya petr")
      )

  init {
    marinesInit.forEach { marineService.saveNew(it) }
  }
}