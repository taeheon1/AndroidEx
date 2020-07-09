package com.kosmo.kotlin.myapplication.kotlin

// 02. 자료형
// 정수형 -> Long > Int > Short > Byte
// 실수형 -> Double > float
// 문자 -> Char (문자 한글자)
// 문자열 -> String
// 논리형 -> True/False : Boolean
var number = 10

//변수 선언 방법(2)
// var/val 변수 : 자료형 = 값
var number1 : Int = 20
var hello1 : String = "Hello"
var point1 : Double = 3.4

// value or variable 선택 기준?? 1. 변하지 않는 값이라면 value
//                               2. 값이 어떻게 될지 모르겠으면 value
//                                  (ex) 코드가 길어져서 변수 찾기 힘들어 질때. 확인하기 힘들 경우
//                                      값을 바꿀때 var로 변경해줌.

fun main(array:Array<String>){
    number = 20
//    number = 20.5

}