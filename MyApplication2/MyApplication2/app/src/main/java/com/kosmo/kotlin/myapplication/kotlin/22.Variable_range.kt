package com.kosmo.kotlin.myapplication.kotlin

// 변수의 접근 범위
// 1. 전역 변수 : 모든 곳에서 접근 가능
// 2. 지역 변수 :

var number100: Int = 10
val test = Test("홍길동")

fun main(array: Array<String>) {
    println(number100)


    test.testFun()
    test.name
    println(number100)

}

class Test(var name: String) {

    fun testFun(){
        var birth : String = "2000/3/1"
        name = "홍길동"
        number100 = 100
        fun testFun2(){
            var gender : String = "male"
        }

    }

}