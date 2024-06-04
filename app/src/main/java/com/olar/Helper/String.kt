package com.catalyist.helper

fun String.Increment(): String {
    return (this.toInt() + 1).toString()
}
fun String.Decriment(): String {
    return (this.toInt() - 1).toString()
}