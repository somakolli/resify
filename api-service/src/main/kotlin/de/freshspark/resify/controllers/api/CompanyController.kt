package de.freshspark.resify.controllers.api

import de.freshspark.resify.AuthenticationInterceptor
import de.freshspark.resify.CompanyRequired
import de.freshspark.resify.DataIntegrityViolationException
import de.freshspark.resify.NoAuthorizationException
import de.freshspark.resify.models.Company
import de.freshspark.resify.models.ResifyUser
import de.freshspark.resify.repositories.CompanyRepository
import de.freshspark.resify.repositories.UserRepository
import org.springframework.web.bind.annotation.*
import javax.validation.constraints.Email
import javax.ws.rs.GET
import javax.ws.rs.POST
import javax.ws.rs.PUT
import javax.ws.rs.Path
@Path("/api/company")
class CompanyController(
  val authenticationInterceptor: AuthenticationInterceptor,
  val companyRepository: CompanyRepository,
  val userRepository: UserRepository) {

  val currentUser = authenticationInterceptor.currentUser
  fun canCreateCompany() {
    if(currentUser.company!==null)
      throw DataIntegrityViolationException("already in company")
  }
  fun canAddUser(company: Company) {
    if(!company.admins.contains(currentUser) || company.owner != currentUser)
      throw NoAuthorizationException("not authorized to add user to company")
  }

  @POST
  fun createCompany(@RequestBody company: Company): Company {
    if(company.name == null || company.name == "")
      throw DataIntegrityViolationException("no-company-name")
    canCreateCompany()
    company.owner = currentUser
    currentUser.company = company
    val returnCompany = companyRepository.save(company)
    userRepository.save(currentUser)
    return returnCompany
  }
  @PUT
  @Path("/addUser")
  @CompanyRequired
  fun addUser(@RequestParam @Email email: String ): ResifyUser {
    canAddUser(currentUser.company!!)
    val user = userRepository.findByEmail(email) ?: userRepository.save(
      ResifyUser(
        email = email
      )
    )
    user.company = authenticationInterceptor.currentUser.company
    return user
  }
}