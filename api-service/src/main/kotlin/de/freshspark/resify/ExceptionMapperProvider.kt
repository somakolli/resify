package de.freshspark.resify

import org.hibernate.exception.ConstraintViolationException
import javax.ws.rs.core.Response
import javax.ws.rs.ext.ExceptionMapper
import javax.ws.rs.ext.Provider

class DataIntegrityViolationException(message: String) : Exception(message)
class CalendarRouteDuplicate(message: String): Exception(message)

@Provider
class DataIntegrityViolation : ExceptionMapper<DataIntegrityViolationException>{
    override fun toResponse(exception: DataIntegrityViolationException?): Response =
        Response.status(409).build()
}

@Provider
class ConstraintViolation: ExceptionMapper<CalendarRouteDuplicate> {
    override fun toResponse(exception: CalendarRouteDuplicate?): Response =
       Response.status(409).build()
}

@Provider
class NoSuchElement: ExceptionMapper<NoSuchElementException> {
    override fun toResponse(exception: NoSuchElementException?): Response =
        Response.status(404).build()

}