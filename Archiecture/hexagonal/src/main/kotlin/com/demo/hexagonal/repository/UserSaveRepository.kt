package com.demo.hexagonal.repository

import com.demo.hexagonal.adapter.out.persistence.UserEntity
import org.springframework.data.jpa.repository.JpaRepository

interface UserSaveRepository: JpaRepository<UserEntity, Long>