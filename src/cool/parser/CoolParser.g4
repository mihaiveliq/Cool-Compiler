parser grammar CoolParser;

options {
    tokenVocab = CoolLexer;
}

@header{
    package cool.parser;
}

program
    : (classes+=programClass SEMI_COL)*
    | EOF
    ;

programClass
    : CLASS name=TYPE (INHERITS inheritType=TYPE)? LBRACE (features+=feature SEMI_COL)* RBRACE
    ;

feature
    : name=ID LPAREN (formals+=formal (COMMA formals+=formal)*)?
        RPAREN COL type=TYPE LBRACE body=expr RBRACE                # method
    | name=ID COL type=TYPE (ASSIGN init=expr)?                     # attribute
    ;

formal
    : name=ID COL type=TYPE
    ;

expr
    : caller=expr (AT dispType=TYPE)? DOT name=ID LPAREN (args+=expr (COMMA args+=expr)*)? RPAREN   # methodCall
    | name=ID LPAREN (args+=expr (COMMA args+=expr)*)? RPAREN                                       # classMethodCall
    | IF cond=expr THEN leftBr=expr ELSE rightBr=expr FI                                            # if
    | WHILE cond=expr LOOP body=expr POOL                                                           # while
    | LBRACE (instructions+=expr SEMI_COL)+ RBRACE                                                  # block
    | LET vars+=local (COMMA vars+=local)* IN body=expr                                             # let
    | CASE var=expr OF (branches+=caseBranch)+ ESAC                                                 # case
    | NEW type=TYPE                                                                                 # new
    | NEG e=expr                                                                                    # negSign
    | ISVOID e=expr                                                                                 # isvoid
    | left=expr op=(MULT | DIV) right=expr                                                          # multDiv
    | left=expr op=(PLUS | MINUS) right=expr                                                        # plusMinus
    | left=expr op=(EQUAL | LT | LE) right=expr                                                     # relational
    | NOT e=expr                                                                                    # boolNot
    | LPAREN e=expr RPAREN                                                                          # paren
    | ID                                                                                            # id
    | INT                                                                                           # coolInt
    | STRING                                                                                        # coolString
    | TRUE                                                                                          # coolTrue
    | FALSE                                                                                         # coolFalse
    | name=ID ASSIGN e=expr                                                                         # assign
    ;

local
    : name=ID COL type=TYPE (ASSIGN init=expr)?
    ;

caseBranch
    : name=ID COL type=TYPE ARROW assignment=expr SEMI_COL
    ;
