package com.demo.jpastudy.service

import com.demo.jpastudy.entity.AddressEntity
import com.demo.jpastudy.entity.UserEntity
import com.demo.jpastudy.repository.AddressRepository
import com.demo.jpastudy.repository.UserRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.context.SpringBootTest

 @AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
 @DataJpaTest
//@SpringBootTest
internal class UserServiceTest {

    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var addressRepository: AddressRepository

    @BeforeEach
    fun createUser() {
        val user = UserEntity(
            name = "demo"
        )

        val address = AddressEntity(
            address = "address1",
            user = user
        )
        user.addressList.add(address)

        val saveUser = userRepository.save(user)
    }

//    @Transactional
    @Test
    fun getAddressByUserId() {
        val demo = userRepository.findByName("demo")
        println("demo: $demo")
    }
}
