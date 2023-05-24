package com.demo.hexagonal.adapter.out.persistence

import com.demo.hexagonal.application.port.out.UserSavePort
import com.demo.hexagonal.domain.User
import com.demo.hexagonal.repository.UserSaveRepository
import org.springframework.stereotype.Service

@Service
class UserSavePersistenceAdapter(
    private val userSaveRepository: UserSaveRepository
): UserSavePort {
    override fun saveUser(user: User) {
        userSaveRepository.save(UserEntity.of(user))
    }
}