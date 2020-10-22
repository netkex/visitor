@file: JvmName("main")

fun main(args: Array<String>) {
    val node: node
    try {
        node = readTree(args[0])
    } catch (e: Exception) {
        println("Reading file error!")
        return
    }
    try {
        println(node.fullLine())
        println(node.Expand())
    } catch (e: Exception) {
        println("Running program error!")
    }
}