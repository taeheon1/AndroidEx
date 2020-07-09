package com.kosmo.kotlin.myapplication.kotlin

fun main(array: Array<String>) {
    val a = mutableListOf<Int>()
    val b = mutableListOf<Boolean>()
    for (i in 1..9) {
        a.add(i)
    }
    println(a)

    for (i in a) {
        if (i % 2 == 0) {
            b.add(true)
        } else {
            b.add(false)
        }
    }
    println(b)
    score(88)
    sum(123)
    println()
    for(i in 2..9){
        for(j in 1..9){
            println("$i * $j = " + i*j)
        }
    }

}

fun score(score: Int) {
    when (score) {
        in 80..90 -> println("A")
        in 70..79 -> println("B")
        in 60..69 -> println("C")
        else -> println("F")
    }
}

fun sum(num: Int) {
    val number: String = num.toString()
    var sum = 0
    for (i in 0..number.length - 1) {
        val a = number[i].toString()
        sum += a.toInt()
    }
    println(sum)


}
