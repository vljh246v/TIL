package com.jaehyun.kotlin.study.study.ch03

class User(val id: Int, val name: String, val address: String)

fun User.validateVBeforeSave(){

    fun validate(value: String, fieldName: String) {
        if(value.isEmpty()){
            throw IllegalArgumentException(
                    "Can't save user ${id}: empty $fieldName"
                    )
        }
    }

    validate(name, "Name")
    validate(address, "Address")
}

fun saveUser(user: User) {
    user.validateVBeforeSave()
}

fun main(){
    saveUser(User(1, "", ""))
}