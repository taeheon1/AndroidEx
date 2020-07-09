package com.kosmo.kotlin.myapplication.kotlin

var a = 1 + 2 + 3 + 4 + 5 //연산의 결과 값을 변수에 넣어 줄 수 있다.
var b = "1"
var c = b.toInt()
var d = b.toFloat()

var e = "John"
var f = "My name is $e Nice to meet you"

//Null - 존재하지 않는다
//var abc : Int = null
var abc1 : Int? = null
var abc2 : Double? = null

var g = a + 3

fun main(array:Array<String>){
    println(a)
    println(b)
    println(c)
    println(d)
    println(e)
    println(f)
    println(abc1)
    println(g)

}

