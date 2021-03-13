package com.reservationappservice.Controllers.api

import com.reservationappservice.Models.ResifyUser
import de.freshspark.resify.SecurityInterceptor
import de.freshspark.resify.repositories.UserRepository
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/users")
class UserController(private val userRepository: UserRepository, val securityInterceptor: SecurityInterceptor) {
    @GetMapping()
    fun getAll() = userRepository.findAll()

    @GetMapping("/me")
    fun me() = securityInterceptor.currentUser
}