enum class OPER {
    multiply, sum, number
}

fun getType(tp: String): OPER {
    when(tp) {
        "+" -> return OPER.sum
        "*" -> return OPER.multiply
        "n" -> return OPER.number
        else -> throw Exception("Incorrect type!")
    }
}

data class node(var lnode: node? = null, var rnode: node? = null, val type: OPER = OPER.number, val number: Int = 0) {
    fun printVisitor(): String {
        if(type == OPER.number)
            return number.toString()
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