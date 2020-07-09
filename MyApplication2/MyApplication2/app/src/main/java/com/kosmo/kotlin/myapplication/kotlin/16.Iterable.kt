package com.kosmo.kotlin.myapplication.kotlin

fun main(array: Array<String>) {

    val a = mutableListOf<Int>(1, 2, 3, 4, 5, 6, 7, 8, 9)
    //반복하는 방법(1)
    for (item in a) {
        if (item == 5) {
            println("item is Five")
        } else {
            println("item is not Five")
        }
    }
    println()

    // 반복하는 방법 (2)
    for ((ind, item) in a.withIndex()) {
        println("index : " + ind + " value : " + item)
    }
    println()
    // 반복하는 방법 (3) -> lambda 사용
    a.forEach {
        println(it)
    }
    println()
    // 반복하는 방법 (4)
    a.forEach { item ->
        println(item)
    }
    println()
    // 반복하는 방법 (5)
    a.forEachIndexed { index, item -> println("index : " + index + " value : " + item) }

    // 반복하는 방법 (6)
    println(a.size)
    for (i in 0 until a.size) {
        //0부터 a.size - 1
        //0 부터 8 까지
        println(i)
    }
    println()
    //반복하는 방법 (7)
    for (i in 0 until a.size step (2)) {
        println(i)
    }
    println()
    //반복하는 방법(8)
    for (i in a.size - 1 downTo (0)) {
        println(i) //역순, 8부터 0까지 반복
    }
    //반복하는 방법(9)
    println()
    for (i in a.size - 1 downTo (0) step (2)) {
        println(i)
    }

    //반복하는 방법(10)
    println()
    for (i in 0..a.size) {
        //.. -> 마지막을 포함
        println(i)
    }

    //반복하는 방법(11)
    println()
    var a1 = 0
    var b = 4

    while (a1 < b) {
        a1++ //while 문을 정지시키기 위한 코드
        println("b")
    }
    println()
    //반복하는 방법(12)
    do{
        println("hello")
    } while(a1 > b)

}