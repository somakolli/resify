package de.freshspark.resify

import de.freshspark.resify.models.Company
import de.freshspark.resify.models.ResifyUser
import de.freshspark.resify.models.Role
import de.freshspark.resify.repositories.UserRepository
import org.eclipse.microprofile.jwt.JsonWebToken
import javax.enterprise.context.RequestScoped
import javax.enterprise.inject.Default
import javax.inject.Inject
import javax.ws.rs.NotAuthorizedException
import javax.ws.rs.container.ContainerRequestContext
import javax.ws.rs.container.ContainerRequestFilter
import javax.ws.rs.container.PreMatching
import javax.ws.rs.ext.Provider
import org.jboss.logging.Logger



@Provider
@PreMatching
class AuthenticationInterceptor : ContainerRequestFilter{

  @Inject
  @field: Default
  lateinit var jwt: JsonWebToken;

  @Inject
  @field: Default
  lateinit var userRepository: UserRepository

  @Inject
  @field: Default
  lateinit var logger: Logger

  lateinit var currentUser: ResifyUser

  override fun filter(requestContext: ContainerRequestContext) {
    val requestUri = requestContext.uriInfo.path;
    if(requestUri.startsWith("/public"))
      return
    if(requestContext.method == "OPTIONS")
      return
    jwt.name ?: throw NotAuthorizedException("authentication failed")
    currentUser = userRepository.findByEmail(jwt.name)?:
        userRepository.save(ResifyUser(jwt.name, role = Role.User))
  }
}