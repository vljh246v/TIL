package com.jaehyun.kotlin.study.study.ch03

class User(val id: Int, val name: String, val address: String)

fun saveUser(user: User){
    if(user.name.isEmpty()){
        throw IllegalArgumentException(
            "Can't save user ${user.id}: empty Name"
        )
    }

    if(user.address.isEmpty()){
        throw IllegalArgumentException(
            "Can't save user ${user.id}: empty Address"
        )
    }
}

fun main(){
    saveUser(User(1, "", ""))
}