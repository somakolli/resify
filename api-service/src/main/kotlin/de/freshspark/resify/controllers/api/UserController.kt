package de.freshspark.resify.controllers.api

import de.freshspark.resify.AuthenticationInterceptor
import de.freshspark.resify.repositories.UserRepository
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/users")
class UserController(private val userRepository: UserRepository,
                     val authenticationInterceptor: AuthenticationInterceptor) {
    @GetMapping()
    fun getAll() = userRepository.findAll()

    @GetMapping("/me")
    fun me() = authenticationInterceptor.currentUser
}