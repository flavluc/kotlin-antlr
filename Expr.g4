/** Grammars always start with a grammar header. */
grammar Expr;

/** A rule called `prog` which will be our entry point
 *  This rule means that a program is a sequence of one or more `stat`s 
 */
prog:   stat+ ;

/** A `stat` rule may be each of the four alternatives (represented by `|`)
 *  The `#` in front of each alternative is a label, we need it so ANTLR generates a visitor function for each alternative (else it would generate one for the whole rule)
 */
stat:   ID '=' expr NEWLINE         # assign
    |   CLEAR NEWLINE               # clear
    |   expr NEWLINE                # printExpr
    |   NEWLINE                     # blank
    ;

/** The `expr` rule to recognize arithmetic expressions */
expr:   expr op=('*'|'/') expr      # MulDiv
    |   expr op=('+'|'-') expr      # AddSub
    |   INT                         # int
    |   ID                          # id
    |   '(' expr ')'                # parens
    ;

/** We also need to define some token names for the operator literals.
 *  That way we can reference token names as Java/Kotlin constants in the visitor.
 *  The same applies for the `op=` in the `expr` rule
 */
MUL :   '*' ;            // assigns token name to '*' used above in grammar
DIV :   '/' ;
ADD :   '+' ;
SUB :   '-' ;
CLEAR:  'clear' ;        // match clear keyword
ID  :   [a-zA-Z]+ ;      // match identifiers
INT :   [0-9]+ ;         // match integers
NEWLINE:'\r'? '\n' ;     // return newlines to parser (is end-statement signal)
WS  :   [ \t]+ -> skip ; // toss out whitespace
