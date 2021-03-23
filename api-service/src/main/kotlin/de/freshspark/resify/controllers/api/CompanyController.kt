package de.freshspark.resify.controllers.api

import de.freshspark.resify.AuthenticationInterceptor
import de.freshspark.resify.CompanyRequired
import de.freshspark.resify.models.Company
import de.freshspark.resify.models.ResifyUser
import de.freshspark.resify.repositories.CompanyRepository
import de.freshspark.resify.repositories.UserRepository
import org.springframework.web.bind.annotation.*
import javax.validation.constraints.Email
import javax.ws.rs.POST
import javax.ws.rs.PUT
import javax.ws.rs.Path
@Path("/api/company")
class CompanyController(val authenticationInterceptor: AuthenticationInterceptor,
                        val companyRepository: CompanyRepository,
                        val userRepository: UserRepository) {
  @POST
  fun createCompany(@RequestBody company: Company): Company {
    val user = authenticationInterceptor.currentUser
    company.owner = user
    user.company = company
    val company = companyRepository.save(company)
    userRepository.save(user)
    return company
  }
  @PUT
  @Path("/addUser")
  @CompanyRequired
  fun addUser(@RequestParam @Email email: String ): ResifyUser =
    userRepository.findByEmail(email)?:
        userRepository.save(ResifyUser(email = email,
          company = authenticationInterceptor.currentUser.company))
}