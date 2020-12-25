package ru.orfac.lab2_extra.utils

fun String.isEnglishAlphabet(): Boolean {
  return this.toCharArray().all { it1 ->
    it1 in 'a'..'z' || it1 in 'A'..'Z'
  }
}
