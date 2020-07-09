package com.kosmo.kotlin.myapplication.kotlin

fun main(args: Array<String>) {

    val a: Int? = null
    val b: Int = 10
    val c: Int = 100

    if (a == null) {
        println("a is null")
    } else {
        println("a is not null")
    }

    //엘비스 연산자 (?:) - 코틀린 특징 문법, 자주 활용, null에 대응하는 문법
    //number가 null 이면 number2에 10을 대입, number가 null이 아니면 number2에 number값 대입
    val number: Int? = 100
    val number2 = number ?: 10
    println(number2)

    val num1: Int = 10
    val num2: Int = 20

    val max = if (num1 > num2) {
        num1 // 10
    } else if (num1 == num2) {
        num2 // 20
    } else{
        100
    }

}