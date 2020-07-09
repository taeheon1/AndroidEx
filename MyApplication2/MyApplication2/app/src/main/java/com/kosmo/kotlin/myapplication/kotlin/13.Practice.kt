package com.kosmo.kotlin.myapplication.kotlin

fun main(array: Array<String>) {
    val array = arrayOf(1, 2, 3)

    //get, set
    val number = array.get(0)
    println(number)
    //val number1 = array.get(100) //index에 주의해야 한다.

    array.set(0, 100)
    val number2 = array[0]
    println(number2)

    //Array의 Bounds 는 처음 만들때 결정 된다.

    //Array이를 만드는 방법(3)
    val a1 = intArrayOf(1, 2, 3)
//    val a2 = intArrayOf("hello")
    val c1 = charArrayOf('b', 'c')
//    val c2 = charArrayOf("b","c")  char -> '',String -> ""

    // Array를 만드는 방법(4)
    var a5 = Array(10, { 0 }) // lambda({})를 활용한 방법
    var a6 = Array(5,{1;2;3;4;5})


}