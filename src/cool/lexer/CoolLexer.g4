lexer grammar CoolLexer;

tokens { ERROR }

@header{
    package cool.lexer;
}

@members{
    private void raiseError(String msg) {
        setText(msg);
        setType(ERROR);
    }
}

/**
 *
 * HELPER FRAGMENTS
 *
 */

// uppercase or lowercase letter

fragment A
    : [aA]
    ;

fragment C
    : [cC]
    ;

fragment D
    : [dD]
    ;

fragment E
    : [eE]
    ;

fragment F
    : [fF]
    ;

fragment H
    : [hH]
    ;

fragment I
    : [iI]
    ;

fragment L
    : [lL]
    ;

fragment N
    : [nN]
    ;

fragment O
    : [oO]
    ;

fragment P
    : [pP]
    ;

fragment R
    : [rR]
    ;

fragment S
    : [sS]
    ;

fragment T
    : [tT]
    ;

fragment U
    : [uU]
    ;

fragment V
    : [vV]
    ;

fragment W
    : [wW]
    ;

/**
 *
 * KEYWORDS
 *
 */

// class related

CLASS
    : C L A S S
    ;

INHERITS
    : I N H E R I T S
    ;

NEW
    : N E W
    ;

// let clause

LET
    : L E T
    ;

IN
    : I N
    ;

// if clause

IF
    : I F
    ;

THEN
    : T H E N
    ;

ELSE
    : E L S E
    ;

FI
    : F I
    ;

// while clause

WHILE
    : W H I L E
    ;

LOOP
    : L O O P
    ;

POOL
    : P O O L
    ;

// case clause

CASE
    : C A S E
    ;

OF
    : O F
    ;

ARROW
    : '=>'
    ;

ESAC
    : E S A C
    ;

// isvoid

ISVOID
    : I S V O I D
    ;

// boolean not

NOT
    : N O T
    ;

/**
 *
 * DISPATCH
 *
 */

// dot

DOT
    : '.'
    ;

// at

AT
    : '@'
    ;

/**
 *
 * BLOCKS
 *
 */

// small paren

LPAREN
    : '('
    ;

RPAREN
    : ')'
    ;

// big braces

LBRACE
    : '{'
    ;

RBRACE
    : '}'
    ;

// semi colon

SEMI_COL
    : ';'
    ;

// colon

COL
    : ':'
    ;

// comma

COMMA
    : ','
    ;

/**
 *
 * DATA
 *
 */

// boolean values

TRUE
    : 't' R U E
    ;

FALSE
    : 'f' A L S E
    ;

// string

STRING
    : '"' ('\\"' | '\\' NEW_LINE | .)*?
    (
	'"' {
		String str = getText();

		str = str
			.substring(1, str.length() - 1)
			.replace("\\\r\n", "\r\n")
			.replace("\\\n", "\n")
			.replace("\\n", "\n")
			.replace("\\t", "\t")
			.replaceAll("\\\\(?!\\\\)", "");

		if (str.length() > 1024) {

			raiseError("String constant too long");

		} else if (str.contains("\0")) {

			raiseError("String contains null character");

		} else {

			setText(str);

		}
	}
	| EOF { raiseError("EOF in string constant"); }
	| NEW_LINE { raiseError("Unterminated string constant"); }
	)
	;

// id

ID
    : [a-z] [_0-9A-Za-z]*
    ;

// type

TYPE
    : [A-Z] [_0-9A-Za-z]*
    ;

// integer

fragment DIGIT
    : [0-9]
    ;

INT
    : DIGIT+
    ;

/**
 *
 * OPERATORS
 *
 */

// assignment

ASSIGN
    : '<-'
    ;

// arithmetic

PLUS
    : '+'
    ;

MINUS
    : '-'
    ;

MULT
    : '*'
    ;

DIV
    : '/'
    ;

// relational

LT
    : '<'
    ;

LE
    : '<='
    ;

EQUAL
    : '='
    ;

// sign

NEG
    : '~'
    ;

/**
 *
 * COMMENTS
 *
 */

fragment NEW_LINE
    : '\r'? '\n'
    ;

// one line comment

ONE_LINE_COMMENT
    : '--' .*? (NEW_LINE | EOF) -> skip
    ;

//multi line comment

MULTI_LINE_COMMENT
    : '(*' (MULTI_LINE_COMMENT | .)*?
    (
    '*)' { skip(); }
    | EOF { raiseError("EOF in comment"); }
    )
    ;

OPEN_COMM
    : '(*'
    ;

CLOSE_COMM
    : '*)' { raiseError("Unmatched *)"); }
    ;

/**
 *
 * WHITE SPACES
 *
 */

WS
    :   [ \n\f\r\t]+ -> skip
    ;

/**
 *
 * ERROR
 *
 */

ERROR
    : . { raiseError("Invalid character: " + getText()); }
    ;
