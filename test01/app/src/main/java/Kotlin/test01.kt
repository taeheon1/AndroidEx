package Kotlin

fun main(array: Array<String>) {
    first()
    println(second(30))
    println(third(30))
    gugudan()
}

fun first() {
    val list1 = MutableList(9, { 0 })
    val list2 = MutableList(9, { true })

    for (i in 0..8) {
        list1[i] = i + 1
    }
    println(list1)

    list1.forEachIndexed { index, value ->
        if (value % 2 == 0) {
            list2[index] = true
        } else {
            list2[index] = false
        }
    }
    println(list2)
}

fun second(score: Int): String {

    when (score) {
        in 80..90 -> {
            return "A"
        }
        in 70..79 -> {
            return "B"
        }
        in 60..69 -> {
            return "C"
        }
        else -> {
            return "F"
        }
    }
}

fun third(number:Int): Int {
    var a = number/10
    var b = number%10

    return a+b
}

fun gugudan() {
    for(x in 2..9){
        for(y in 1..9){
            println("$x X $y = ${x*y}")
        }
    }
}