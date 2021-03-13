package com.reservationappservice.Controllers.api

import de.freshspark.resify.repositories.UserRepository
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("/api/test-info")
class TestController(private val userRepository: UserRepository) {
    @GetMapping("/test-user")
    fun getTestUserId() = userRepository.findByEmail("s.makolli@aol.de")
}