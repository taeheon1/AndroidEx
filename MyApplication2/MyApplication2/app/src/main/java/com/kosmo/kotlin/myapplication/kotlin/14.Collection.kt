package com.kosmo.kotlin.myapplication.kotlin

//14. Collection
// -> list,set,map


// List

fun main(args: Array<String>){

    //Immutable Collections -> 변경할 수 없는 collection
    //List
    val numberList = listOf<Int>(1,2,3,3,3)
    println(numberList)
    val a =numberList.get(0)
    println(a)
    println(numberList[1])

    //Set, 집합과 같은 개념, 집합은 동일 값을 허락 안함 -> 중복을 허용하지 않는다.
    //집합의 특징 : 순서가 없다
    val numberSet = setOf<Int>(1,2,3,3,3)
    println()
    numberSet.forEach{
        println(it)
    }

    //Map -> Key, Value 방식으로 관리한다
    val numberMap = mapOf<String,Int>("one" to 1,"two" to 2)
    println()
    println(numberMap.get("one"))

    //Mutable Collection -> 변경 가능
    val mnumberList = mutableListOf<Int>(1,2,3)
    mnumberList.add(3,4)
    println()
    println(mnumberList)

    val mNumberSet = mutableSetOf<Int>(1,2,3,4,4,4,4)
    mNumberSet.add(10)
    println()
    println(mNumberSet)

    val mNumberMap = mutableMapOf<String,Int>("one" to 1)
    mNumberMap.put("two" , 2)
    println()
    println(mNumberMap)


}