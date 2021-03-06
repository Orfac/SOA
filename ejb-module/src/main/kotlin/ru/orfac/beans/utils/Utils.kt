package ru.orfac.beans.utils

import ru.orfac.beans.model.SpaceShip

fun String.isEnglishAlphabet(): Boolean {
  return this.toCharArray().all { it1 ->
    it1 in 'a'..'z' || it1 in 'A'..'Z'
  }
}
fun List<SpaceShip>.sortToString():List<String>{
  return this.sortedBy { it.id }.map { it.toString() }
}
