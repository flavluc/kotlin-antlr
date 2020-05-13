// ./calc.kt

package calc

import org.antlr.v4.runtime.*
import org.antlr.v4.runtime.tree.ParseTree

import java.io.FileInputStream
import java.io.InputStream

import ExprLexer
import ExprParser
import EvalVisitor

fun main(args: Array<String>){

  val inputStream = if (args.size > 0) { FileInputStream(args[0]) } else { System.`in` }

  val input = CharStreams.fromStream(inputStream)
  val lexer = ExprLexer(input)
  val tokens = CommonTokenStream(lexer)
  val parser = ExprParser(tokens)
  val tree = parser.prog()  // `prog` is the entry point we defined in our grammar

  val eval = EvalVisitor()
  eval.visit(tree)
} 