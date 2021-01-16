package ru.orfac.lab2spring.rest.extensions

import ru.orfac.lab2spring.config.Utils
import ru.orfac.lab2spring.exceptions.RequestHandlingException
import ru.orfac.lab2spring.rest.dto.MarineCollectionRequestDto
import javax.validation.constraints.Min

data class PageableParameters(
  @get:Min(1, message = "Page size starts from 1") var pageSize: Int,
  @get:Min(1, message = "Page index starts from 1") var pageIndex: Int
)

fun parseMarineCollectionDto(parameters: Map<String, String>): MarineCollectionRequestDto {
  parameters.checkIfCollectionRequest()

  val filterParams = mutableListOf<Pair<String, String>>()
  parameters.forEach {
    if (it.key in Utils.PossibleValues) {
      filterParams.add(Pair(it.key, it.value))
    }
  }

  val isSorting = parameters.isSorting()
  val sortBy: String? = if (isSorting) parameters["sortBy"] else null

  val isPageable = parameters.isPageable()
  val pageableParameters: PageableParameters? =
      if (isPageable) parameters.getPageableParams() else null


  return MarineCollectionRequestDto(pageableParameters, filterParams, sortBy)

}

fun Map<String, String>.getPageableParams(): PageableParameters {
  val pageSizeString = this["pageSize"]
  val pageNumberString = this["pageNumber"]
  var pageNumber = 1
  var pageSize = 1
  try {
    pageNumber = pageNumberString!!.toInt()
    pageSize = pageSizeString!!.toInt()
  } catch (ex: NumberFormatException) {
    throw RequestHandlingException("Page number and page size should be integer values")
  }
  return PageableParameters(pageSize = pageSize, pageIndex = pageNumber)
}

fun Map<String, String>.isPageable(): Boolean {
  val pageSizeString = this["pageSize"]
  val pageNumberString = this["pageNumber"]
  return pageNumberString != null && pageSizeString != null
}

fun Map<String, String>.isSorting(): Boolean {
  val sortingString = this["sortBy"]
  return sortingString != null
}

fun String.isEnglishAlphabet(): Boolean {
  return this.toCharArray().all { it1 ->
    it1 in 'a'..'z' || it1 in 'A'..'Z'
  }
}
fun String.isEnglishAlphabetWithUnderLineOrDots(): Boolean {
  return this.toCharArray().all { it1 ->
    it1 in 'a'..'z' || it1 in 'A'..'Z' || it1=='_' || it1 =='.'
  }
}


private fun Map<String, String>.checkIfCollectionRequest() {
  val parameterNames = this.keys
  if (!parameterNames.toList().all {
        it in Utils.PossibleValues || it == "pageSize" || it == "pageNumber" || it == "sortBy"
      }) {
    throw RequestHandlingException("Extra parameters except pagination, sorting and filtering are added")
  }
}
