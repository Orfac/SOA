package ru.orfac.lab2main.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus
import java.lang.Exception

class NotFoundException(msg: String) : RuntimeException(msg)