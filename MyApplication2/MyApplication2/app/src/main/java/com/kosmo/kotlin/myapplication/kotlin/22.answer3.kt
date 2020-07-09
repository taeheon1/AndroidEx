package com.kosmo.kotlin.myapplication.kotlin

fun main(args: Array<String>) {

    val tv = TV1(listOf<String>("k", "m", "s"))
    tv.switch()
    println(tv.onOrOff)
    tv.switch()
    println(tv.onOrOff)

    tv.channelUp()
    println(tv.checkCurrentChannel())
    tv.channelUp()
    println(tv.checkCurrentChannel())
    tv.channelUp()
    println(tv.checkCurrentChannel())
    tv.channelUp()
    println(tv.checkCurrentChannel())

}

class TV1(val channels: List<String>) {
    var onOrOff: Boolean = false // True -> on , false =>off
    var currentChannelNumber = 0
        set(value) {
            println(value)
            field = value
            if(field > 2) field = 0
            if(field < 0) field = 2

        }
        get() {
            println("호출되었습니다")
            return field
        }
    // 값의 할당
    // a = b
    fun switch() {
        onOrOff = !onOrOff
    }

    fun checkCurrentChannel(): String {
        return channels[currentChannelNumber]
    }

    fun channelUp() {
        currentChannelNumber = currentChannelNumber + 1
//        channels.forEachIndexed { index, value ->
//            if (currentChannelNumber == index) {
//                currentChannelNumber = currentChannelNumber + 1
//                return
//            }
//        }
    }

    fun channelDown() {
        currentChannelNumber = currentChannelNumber - 1
//        channels.forEachIndexed { index, value ->
//           if (currentChannelNumber == index) {
//                currentChannelNumber = currentChannelNumber - 1
//                return
//            }
//        }
    }
}