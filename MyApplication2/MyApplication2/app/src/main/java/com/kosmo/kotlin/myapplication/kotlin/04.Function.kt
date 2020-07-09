package com.kosmo.kotlin.myapplication.kotlin

//04.Function - 함수
// input을 넣어주면 output이 나오는 것

// -함수를 선언하는 방법
// fun 함수명 (변수명: 타입) : 반환형 {
//    함수 내용
//    return  반환값
// }
fun plus(first: Int, second: Int): Int {
    println(first)
    println(second)
    val result: Int = first + second
    println(result)
    return result
}

// - 디폴트 값을 갖는 함수 만들기
fun plusFive(first: Int, second: Int = 5): Int {
    var result: Int = first + second
    return result
}

// - 반환 값이 없는 함수 만들기
fun printPlus(first: Int, second: Int): Unit {
    val result: Int = first + second
    println(result)
}

fun printPlus2(first: Int, second: Int) {
    val result: Int = first + second
    println(result)
}

// 간단하게 함수 선언하기
fun plusShort(first: Int, second: Int) = first + second

// 가변 인자를 갖는 함수 선언하기
fun plusMany(vararg numbers: Int) {
    println()
    for (number in numbers) {
        println(number)
    }

}

fun main(array: Array<String>) {
    // 함수를 호출하는 방법
    val result = plus(5, 10)
    // 인수를 명시적으로 전달하는 방법
    val result2 = plus(second = 20, first = 30)
    val result3 = plus(first = 100, second = 200)
    println(result)
    println(result2)
    println(result3)

    println()
    //  디폴트 값을 갖는 함수 호출하기
    val result4 = plusFive(10, 20)
    println(result4)
    val result5 = plusFive(10)
    println(result5)

    println()
    printPlus(10, 20)
    println()
    val result6 = plusShort(50, 50)
    println(result6)
    plusMany(1, 2, 3)
    plusMany(100)
}

