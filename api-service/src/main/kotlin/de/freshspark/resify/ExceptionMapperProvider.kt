package de.freshspark.resify

import org.hibernate.exception.ConstraintViolationException
import javax.ws.rs.core.Response
import javax.ws.rs.ext.ExceptionMapper
import javax.ws.rs.ext.Provider

class DataIntegrityViolationException(message: String) : Exception(message)
class CalendarRouteDuplicate(message: String): Exception(message)
class NoAuthorizationException(message: String): Exception(message)
class NoCompanyException(message: String): Exception(message)

@Provider
class DataIntegrityViolation : ExceptionMapper<DataIntegrityViolationException>{
    override fun toResponse(exception: DataIntegrityViolationException?): Response =
        Response.status(409).entity(exception).build()
}

@Provider
class ConstraintViolation: ExceptionMapper<CalendarRouteDuplicate> {
    override fun toResponse(exception: CalendarRouteDuplicate?): Response =
       Response.status(409).entity(exception).build()
}

@Provider
class NoSuchElement: ExceptionMapper<NoSuchElementException> {
    override fun toResponse(exception: NoSuchElementException?): Response =
        Response.status(404).build()

}
@Provider
class NotAuthorizedException: ExceptionMapper<NoAuthorizationException> {
  override fun toResponse(exception: NoAuthorizationException?): Response = 
        Response.status(401).entity(exception).build()
}

@Provider
class NoCompany: ExceptionMapper<NoCompanyException> {
  override fun toResponse(p0: NoCompanyException?): Response {
    return Response.status(401, "noCompany").entity("no company").build()
  }

}
