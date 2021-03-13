package de.freshspark.resify

import io.quarkus.oidc.common.runtime.OidcCommonConfig
import io.quarkus.security.identity.SecurityIdentity
import org.eclipse.microprofile.jwt.JsonWebToken
import org.jboss.logging.Logger
import javax.annotation.security.RolesAllowed
import javax.enterprise.inject.Default
import javax.inject.Inject
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType

@Path("/hello-resteasy")
class GreetingResource(val jwt: JsonWebToken,
                        val logger: Logger) {
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    fun hello(): String {
        val principal = jwt.claimNames;
        return "Hello RESTEasy"
    }
}