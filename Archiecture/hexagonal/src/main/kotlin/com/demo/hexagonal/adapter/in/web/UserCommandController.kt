package com.demo.hexagonal.adapter.`in`.web

import com.demo.hexagonal.application.port.`in`.UserCommandPort
import com.demo.hexagonal.dto.SaveUserDto
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/user")
class UserCommandController(
    private val userCommandPort: UserCommandPort
) {
    @PostMapping
    fun addUser(@RequestBody saveUserDto: SaveUserDto){
        userCommandPort.addUser(saveUserDto.toDomain())
    }
}