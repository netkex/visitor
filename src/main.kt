@file: JvmName("main")

fun main(args: Array<String>) {
    for (file in args) {
        val node: node
        try {
            node = readTree(file)
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
}