package ru.orfac.lab2main.rest.dto

import ru.orfac.lab2main.rest.extensions.PageableParameters
import javax.validation.Valid

open class MarineCollectionRequestDto(
  @get:Valid val pageableParameters: PageableParameters?,
  val filterList: List<Pair<String, String>>?,
  val sortSequence: String?
)