import java.io.File
import java.lang.Exception
import java.security.spec.ECField

/**
 * read lines (with spaces!) from file
 */
fun readFile(filename: String): List<List<String>> {
    val ans = mutableListOf<List<String>>()
    val rawInput = File(filename).readLines().filter{it != ""}
    rawInput.forEach { ans.add(it.split(' ').filter { it.isNotEmpty() }) }
    return ans
}

fun checkTypes(input: List<List<String>>) {
    for (nodeNum in input.indices) {
        try {
            getType(input[nodeNum][0])
        } catch (e: Exception) {
            println("Incorrect line $nodeNum")
            println(e)
            throw Exception("Incorrect file!")
        }
    }
}

fun checkSizesAndSons(input: List<List<String>>) {
    val n = input.size
    for (nodeNum in input.indices) {
        val type = getType(input[nodeNum][0])
        if (type == OPER.number) {
            if (input[nodeNum].size < 2 || input[nodeNum][1].toIntOrNull() == null) {
                println("Incorrect line $nodeNum")
                throw Exception("Incorrect file!")
            }
            continue
        }
        if (input[nodeNum].size != 3 ||
            input[nodeNum][1].toIntOrNull() == null || input[nodeNum][1].toInt() < 0 || input[nodeNum][1].toInt() > n ||
            input[nodeNum][2].toIntOrNull() == null || input[nodeNum][2].toInt() < 0 || input[nodeNum][2].toInt() > n
        ) {
            println("Incorrect line $nodeNum")
            throw Exception("Incorrect file!")
        }
    }
}

fun checkTreeStructure(input: List<List<String>>) {
    val n = input.size
    val pred = MutableList(n) {false}
    for (nodeNum in input.indices) {
        val type = getType(input[nodeNum][0])
        if(type == OPER.number)
            continue
        val (lson, rson) = input[nodeNum].drop(1).map {it.toInt() - 1}
        if(lson <= nodeNum || rson <= nodeNum) {
            println("incorrect line $nodeNum")
            throw Exception("Incorrect file!")
        }
        if(pred[lson]) {
            println("vertex $lson has more than 1 parent")
            throw Exception("Incorrect file!")
        }
        if(pred[rson]) {
            println("vertex $lson has more than 1 parent")
            throw Exception("Incorrect file!")
        }
        pred[lson] = true
        pred[rson] = true
    }
}

fun checkInput(input: List<List<String>>) {
    checkTypes(input)
    checkSizesAndSons(input)
    checkTreeStructure(input)
}

fun readTree(filename: String): node {
    val input = readFile(filename)
    val n = input.size
    val types = MutableList(n) {OPER.number}
    val sons = MutableList(n) {Pair(-1, -1)}
    val numbers = MutableList(n) {0}
    val nodes = MutableList<node?>(n) {null}
    checkInput(input)
    for (nodeNum in input.indices) {
        types[nodeNum] = getType(input[nodeNum][0])
        if(types[nodeNum] == OPER.number) {
            numbers[nodeNum] = input[nodeNum][1].toInt()
            continue
        }
        val (lson, rson) = input[nodeNum].drop(1).map {it.toInt() - 1}
        sons[nodeNum] = Pair(lson, rson)
    }
    for (nodeNum in (n - 1) downTo 0) {
        if(types[nodeNum] == OPER.number)
            nodes[nodeNum] = node(null, null, OPER.number, numbers[nodeNum])
        else
            nodes[nodeNum] = node(nodes[sons[nodeNum].first], nodes[sons[nodeNum].second], types[nodeNum])
    }
    return nodes[0]!!
}