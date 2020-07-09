package com.kosmo.kotlin.myapplication.kotlin

// 01. Variable = 변수
//

// 변수 선언하는 방법
// var/val 변수명 = 값(넣고 싶은 것)
// variable/value
// 이 둘의 차이 : var = 내 마음대로 넣을 수 있는 변수, val = 한 번 넣으면 바꿀 수 없는 변수.

var num = 10
var hello = "hello"
var point = 3.4

val fix = 20

fun main(args:Array<String>){

    println(num)
    println(hello)
    println(point)
    println(fix)

    num = 100
    hello = "Good bye"
    point = 10.4


    println(num)
    println(hello)
    println(point)
    println(fix)

}