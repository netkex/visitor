import java.lang.StringBuilder

enum class OPER {
    multiply, sum, number
}

fun getType(tp: String): OPER {
    return when(tp) {
        "+" -> OPER.sum
        "*" -> OPER.multiply
        "n" -> OPER.number
        else -> throw Exception("Incorrect type!")
    }
}

data class node(var lnode: node? = null, var rnode: node? = null, val type: OPER = OPER.number, val number: Int = 0) {
    fun printVisitor(): String {
        if(type == OPER.number) {
            if(number < 0)
                return "($number)"
            else
                return "$number"
        }
        if(type == OPER.sum) {
            return "${lnode!!.printVisitor()} + ${rnode!!.printVisitor()}"
        }
        val lside: String
        val rside: String
        if(lnode!!.type == OPER.sum)
            lside = "(${lnode!!.printVisitor()})"
        else
            lside = lnode!!.printVisitor()
        if(rnode!!.type == OPER.sum)
            rside = "(${rnode!!.printVisitor()})"
        else
            rside = rnode!!.printVisitor()
        return "$lside * $rside"
    }

    fun Expand(): String {
        if(type == OPER.number) {
            if(number < 0)
                return "($number)"
            else
                return "$number"
        }
        if(type == OPER.sum)
            return "${lnode!!.Expand()} + ${rnode!!.Expand()}"
        val numsLeft = lnode!!.Expand().split(" + ")
        val numsRight = rnode!!.Expand().split(" + ")
        val ans = StringBuilder("")
        numsLeft.forEach { lnum -> numsRight.forEach { rnum -> ans.append("$lnum * $rnum + ") } }
        return ans.toString().dropLast(3)
    }

    fun calculate(): Int {
        if(type == OPER.number)
            return number
        if(type == OPER.sum)
            return lnode!!.calculate() + rnode!!.calculate()
        return lnode!!.calculate() * rnode!!.calculate()
    }

    fun fullLine(): String {
        return "${printVisitor()} = ${calculate()}"
    }
}