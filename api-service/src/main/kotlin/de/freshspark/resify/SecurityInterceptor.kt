package de.freshspark.resify

import com.reservationappservice.Models.ResifyUser
import com.reservationappservice.Models.Role
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
class SecurityInterceptor() : ContainerRequestFilter{

    @Inject
    @field: Default
    lateinit var jwt: JsonWebToken;

    @Inject
    @field: Default
    lateinit var userRepository: UserRepository

    @Inject
    @field: Default
    lateinit var logger: Logger

    var currentUser: ResifyUser? = null;

    override fun filter(requestContext: ContainerRequestContext) {
        val requestUri = requestContext.uriInfo.path;
        if(requestUri.startsWith("/public"))
            return
        if(requestContext.method == "OPTIONS")
            return
        jwt.name ?: throw NotAuthorizedException("authentication failed")
        currentUser = userRepository.findByEmail(jwt.name)?:
            userRepository.save(ResifyUser(null, jwt.name, role = Role.User))
    }
}