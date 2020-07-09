package com.kosmo.kotlin.myapplication.kotlin

// 11. 제어 흐름 실습
fun main(args: Array<String>) {
    val value: Int? = null

    when (value) {
        null -> println("value is null")
        else -> println("value is not null")
    }

    val value2: Boolean? = null
    // when 구문은 조건으로 갖는 값의 모든 경우에 대응 해주는 것이 좋다
    when (value2) {
        true -> println("")
        false -> println("")
        null -> println("null")

    }
    // 값을 리턴하는 when 구문일 경우 반드시 값을 리턴해야 한다
    val value3 = when(value2){
        true -> 1
        false -> 2
        null -> 0
    }

    // whend의 다양한 조건식
    val value4 : Int = 10
    when(value4) {
        is Int -> println("value4 is Int")
        else -> println("values4 is not Int")
    }

    val value5 : Int = 70
    when(value5) {
        in 60..70 -> {
            println("value is in 60..70")
        }
        in 70..80 -> {
            println("value is in 70..80")
        }
        in 80..90 -> {
            println("value is in 80..90")
        }

    }

}