import java.io.File
import java.lang.Exception

/**
 * read lines (with spaces!) from file
 */
fun readFile(filename: String): List<List<String>> {
    var ans = mutableListOf<List<String>>()
    val rawInput = File(filename).readLines().filter{it != ""}
    rawInput.forEach { ans.add(it.split(' ').filter { it.isNotEmpty() }) }
    return ans
}


fun readTree(filename: String): node {
    val rawInput = readFile(filename)
    println(rawInput)
    val n = rawInput.size
    val types = MutableList(n) {OPER.number}
    val sons = MutableList(n) {Pair(-1, -1)}
    val numbers = MutableList(n) {0}
    val pred = MutableList(n) {false}
    val nodes = MutableList<node?>(n) {null}
    for (nodeNum in rawInput.indices) {
        try {
            types[nodeNum] = getType(rawInput[nodeNum][0])
        } catch (e: Exception) {
            println("Incorrect line $nodeNum")
            println(e)
            throw Exception("Incorrect file!")
        }
        if(types[nodeNum] == OPER.number) {
            numbers[nodeNum] = rawInput[nodeNum][1].toInt()
            continue
        }
        if(rawInput[nodeNum].size != 3 ||
            rawInput[nodeNum][1].toIntOrNull() == null || rawInput[nodeNum][1].toInt() < 0 || rawInput[nodeNum][1].toInt() > n ||
            rawInput[nodeNum][2].toIntOrNull() == null || rawInput[nodeNum][2].toInt() < 0 || rawInput[nodeNum][2].toInt() > n) {
            println("Incorrect line $nodeNum")
            throw Exception("Incorrect file!")
        }
        val (lson, rson) = rawInput[nodeNum].drop(1).map {it.toInt() - 1}
        sons[nodeNum] = Pair(lson, rson)
        if(pred[lson]) {
            println("vertex $lson has more than 1 parent")
            throw Exception("Incorrect file!")
        }
        if(pred[rson]) {
            println("vertex $lson has more than 1 parent")
            throw Exception("Incorrect file!")
        }
        if(lson <= nodeNum || rson <= nodeNum) {
            println("incorrect line $nodeNum")
            throw Exception("Incorrect file!")
        }
        pred[lson] = true
        pred[rson] = true
    }
    for (nodeNum in (n - 1) downTo 0) {
        if(types[nodeNum] == OPER.number) {
            nodes[nodeNum] = node(null, null, OPER.number, numbers[nodeNum])
        }
        else {
            nodes[nodeNum] = node(nodes[sons[nodeNum].first], nodes[sons[nodeNum].second], types[nodeNum])
        }
    }
    return nodes[0]!!
}