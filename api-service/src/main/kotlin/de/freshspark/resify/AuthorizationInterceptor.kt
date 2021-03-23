package de.freshspark.resify

import org.jboss.logging.Logger
import javax.enterprise.inject.Default
import javax.inject.Inject
import javax.ws.rs.container.ContainerRequestContext
import javax.ws.rs.container.ContainerRequestFilter
import javax.ws.rs.ext.Provider

@CompanyRequired
@Provider
class AuthorizationInterceptor: ContainerRequestFilter {
  @Inject
  @field: Default
  lateinit var logger: Logger

  @Inject
  @field: Default
  lateinit var authenticationInterceptor: AuthenticationInterceptor

  override fun filter(p0: ContainerRequestContext?) {
    if(authenticationInterceptor.currentUser.company == null)
      throw NoCompanyException(authenticationInterceptor.currentUser.id.toString())
  }
}