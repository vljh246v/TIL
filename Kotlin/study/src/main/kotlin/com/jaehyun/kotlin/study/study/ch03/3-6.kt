package com.jaehyun.kotlin.study.study.ch03

class User(val id: Int, val name: String, val address: String)

fun saveUser(user: User){

    fun validate(user: User, value: String, fieldName: String) {
        if(value.isEmpty()){
            throw IllegalArgumentException(
                    "Can't save user ${user.id}: empty $fieldName"
                    )
        }
    }

    validate(user, user.name, "Name")
    validate(user, user.address, "Address")
}

fun main(){
    saveUser(User(1, "", ""))
}