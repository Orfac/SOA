package ru.orfac.marine.config

import org.springframework.context.annotation.Configuration
import ru.orfac.marine.model.*
import ru.orfac.marine.service.MarineService
import kotlin.random.Random
import kotlin.random.nextInt
import kotlin.random.nextLong

@Configuration
class InitializerConfig(
  var marineService: MarineService,
  val marineUpdateConfiguration: MarineUpdateConfiguration
) {
  companion object {
    private const val SEED = 12345
    val random = Random(SEED)
  }

  private final val marinesInit =
      listOf(
          getRandomMarine(), getRandomMarine(),
          getRandomMarine(), getRandomMarine(),
          getRandomMarine(), getRandomMarine(),
          getRandomMarine(), getRandomMarine(),
          getRandomMarine(), getRandomMarine(),
          getRandomMarine(), getRandomMarine()
      )

  init {
    if (marineUpdateConfiguration.loadNewValues == true){
      marinesInit.forEach { marineService.saveNew(it) }
    }
  }

  private fun getRandomMarine(): SpaceMarine {
    val coordinates = Coordinates(1, 2f)
    val chapter = Chapter(
        "Thousand_of_sons",
        "legion1",
        1000,
        "Prospero")
    return SpaceMarine(
        getRandomName(),
        coordinates,
        random.nextLong(1L..100L),
        random.nextInt(1..3),
        AstartesCategory.values().random(),
        MeleeWeapon.values().random(),
        chapter)
  }

  private fun getRandomName() = arrayOf(
      "Magnus", "Chorus",
      "Fulgrim", "Emperror",
      "Marion", "Psiker",
      "Roberto", "Mihail",
      "Vladimir", "Vitaliy",
      "Darklord")
      .random()
}