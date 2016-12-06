package kebuu

sealed class Expr {
    class Const(val number: Double) : Expr()
    class Sum(val e1: Expr, val e2: Expr) : Expr()
    object NotANumber : Expr()
}



fun test(expr: Expr): String {
    val visitor = Visitor()

    val visitedResult = when(expr) {
        is Expr.Const -> visitor.visit(expr)
        is Expr.Sum -> visitor.visit(expr)
        is Expr.NotANumber -> visitor.visit(expr)
    }

    return visitedResult
}

class Visitor {
    fun visit(expr: Expr.Const): String {
        return "Expr.Const"
    }

    fun visit(expr: Expr.Sum): String {
        return "Expr.Sum"
    }

    fun visit(expr: Expr.NotANumber): String {
        return "Expr.NotANumber"
    }

}