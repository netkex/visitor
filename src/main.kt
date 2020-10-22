@file: JvmName("main")

fun main(args: Array<String>) {
    val node = readTree(args[0])
    println(node.fullLine())
}