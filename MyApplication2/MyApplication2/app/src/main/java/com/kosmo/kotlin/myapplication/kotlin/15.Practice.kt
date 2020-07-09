package com.kosmo.kotlin.myapplication.kotlin

fun main(array: Array<String>) {

    val a = mutableListOf<Int>(1, 2, 3)
    a.add(4)
    println(a)
    a.add(0, 100)
    println(a)
    a.set(0, 200)
    println(a)
    a.removeAt(2)
    println(a)

    val b = mutableSetOf<Int>(1, 2, 3, 4)
    println()
    b.add(2)
    println(b)
    b.remove(2)
    println(b)
    b.remove(100)

    val c = mutableMapOf<String, Int>("one" to 1)
    c.put("two", 2)
    println()
    println(c)
    c.replace("two", 3)
    println(c)
    println(c.values)
    println(c.keys)
    c.clear()
    println(c)


}