package com.demo.hexagonal.controller

import com.demo.hexagonal.dto.SaveUserDto
import com.demo.hexagonal.service.UserCommandService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/user")
class UserCommandController(
    private val userCommandService: UserCommandService
) {
    @PostMapping
    fun addUser(@RequestBody saveUserDto: SaveUserDto){
        userCommandService.addUser(saveUserDto)
    }
}