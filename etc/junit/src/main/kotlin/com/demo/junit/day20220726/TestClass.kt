package com.demo.junit.day20220726

class TestClass {
    fun sortTree(name: String): Tree {
        val deciduous = listOf("Beech", "Birch", "Ash", "Whitebeam", "Hornbeam", "Hazel & Willow")
        val evergreen = listOf("Cedar", "Holly", "Laurel", "Olive", "Pine")

        return if(deciduous.contains(name)) {
            Deciduous(name)
        } else if(evergreen.contains(name)) {
            Evergreen(name)
        } else {
            throw RuntimeException("Tree could not be classified")
        }
    }
}


open class Tree(name: String){}

class Deciduous(name: String): Tree(name) {}
class Evergreen(name: String): Tree(name) {}