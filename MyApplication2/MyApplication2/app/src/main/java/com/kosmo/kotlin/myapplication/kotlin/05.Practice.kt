package com.kosmo.kotlin.myapplication.kotlin

fun plus(first: Int, second: Int, third: Int): Int {
    return first + second + third

}

fun minusThree(first: Int, second: Int, third: Int) = first - second - third

fun multipleThree(first: Int = 1, second: Int = 1, third: Int = 1): Int {
    return first * second * third
}

// 내부 함수
// - 함수 안에 있는 함수
fun showMyPlus(first: Int, second: Int): Int {
    println(first)
    println(second)
    fun plus(first: Int, second: Int): Int {
        return first + second
    }
    return plus(first, second)
}


fun main(array: Array<String>) {
    val result = plus(1, 2, 3)
    println(result)
    val result2 = minusThree(10, 1, 1)
    println(result2)
    val result3 = multipleThree(2, 2, 2)
    println(result3)
    val result4 = multipleThree()
    println(result4)

    val result5 = showMyPlus(4, 5)
    println(result5)
}