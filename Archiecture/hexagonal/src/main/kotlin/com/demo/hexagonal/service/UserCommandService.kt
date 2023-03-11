package com.demo.hexagonal.service

import com.demo.hexagonal.dto.SaveUserDto
import org.springframework.stereotype.Service


@Service
class UserCommandService {
    fun addUser(saveUserDto: SaveUserDto) {
        // ...
    }
}