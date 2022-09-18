package com.demo.lock.lock.repository

import com.demo.lock.lock.entity.Product
import org.springframework.data.jpa.repository.JpaRepository

interface ProductRepository : JpaRepository<Product, Long>
