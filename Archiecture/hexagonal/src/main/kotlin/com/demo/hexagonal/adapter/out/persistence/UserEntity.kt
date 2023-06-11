package com.demo.hexagonal.adapter.out.persistence

import com.demo.hexagonal.domain.User
import javax.persistence.*

@Entity
@Table(name = "user_info")
class UserEntity(
    @Id
    @Column(name = "id")
    val id: String,

    val name: String,
    val age: Int,
    val birthDay: String
) {
    companion object {
        fun of(user: User): UserEntity {
            return UserEntity(
                id = "${user.name}-${user.age}",
                name = user.name,
                age = user.age,
                birthDay = user.birth
            )
        }
    }
}

/*


@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
 */