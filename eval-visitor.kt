// ./eval-visitor.kt

/** ExprBaseVisitor is the base class generated by ANTLR */
class EvalVisitor() : ExprBaseVisitor<Int>() {
  var memory: HashMap<String, Int> = HashMap<String, Int>()

/** These are the functions generated by each label as we defined in our grammar.
 *  Whe only need to override the functions we are interested in.
 */
  override fun visitAssign(ctx: ExprParser.AssignContext ): Int {
      val id = ctx.ID().getText()  
      val value = visit(ctx.expr())   
      memory.put(id, value)
      return value
  }

  /** expr NEWLINE */
  override fun visitPrintExpr(ctx: ExprParser.PrintExprContext): Int {
      val value = visit(ctx.expr()) 
      println(value)         
      return 0                          
  }

  /** INT */
  override fun visitInt(ctx: ExprParser.IntContext): Int {
      return ctx.INT().getText().toInt()
  }

  /** ID */
  override fun visitId(ctx: ExprParser.IdContext): Int {
      val id = ctx.ID().getText()
      val value = memory.get(id)

      return if ( value != null ) { value } else { 0 }
  }

  /** expr op=('*'|'/') expr */
  override fun visitMulDiv(ctx: ExprParser.MulDivContext): Int {
      val left = visit(ctx.expr(0))
      val right = visit(ctx.expr(1)) 

      if ( ctx.op.getType() == ExprParser.MUL ) {
        return left * right
      }
      return left / right 
  }

  /** expr op=('+'|'-') expr */
  override fun visitAddSub(ctx: ExprParser.AddSubContext): Int {
      val left = visit(ctx.expr(0))  
      val right = visit(ctx.expr(1)) 

      if ( ctx.op.getType() == ExprParser.ADD ) {
        return left + right
      }
      return left - right 
  }

  /** '(' expr ')' */
  override fun visitParens(ctx: ExprParser.ParensContext): Int {
      return visit(ctx.expr()) 
  }

  /** 'clear' */
  override fun visitClear(ctx: ExprParser.ClearContext): Int {
      memory.clear()
      return 0
  }
}