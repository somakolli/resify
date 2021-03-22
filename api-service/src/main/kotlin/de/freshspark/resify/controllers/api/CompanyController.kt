package de.freshspark.resify.controllers.api

import de.freshspark.resify.SecurityInterceptor
import de.freshspark.resify.models.Company
import de.freshspark.resify.models.ResifyUser
import de.freshspark.resify.repositories.CompanyRepository
import de.freshspark.resify.repositories.UserRepository
import org.springframework.web.bind.annotation.*
import javax.validation.constraints.Email

@RestController
@RequestMapping("/api/company")
class CompanyController(val securityInterceptor: SecurityInterceptor,
                        val companyRepository: CompanyRepository,
                        val userRepository: UserRepository) {
  @PostMapping
  fun createCompany(@RequestBody company: Company): Company {
    val user = securityInterceptor.currentUser
    company.owner = user
    return companyRepository.save(company)
  }
  @PutMapping("/addUser")
  fun addUser(@RequestParam @Email email: String ): ResifyUser =
    userRepository.findByEmail(email)?:
        userRepository.save(ResifyUser(email = email))
}