// Generated from /home/mihaiveliq/Teme/CPL/Tema2/src/cool/lexer/CoolLexer.g4 by ANTLR 4.8

    package cool.lexer;

import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class CoolLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.8", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		ERROR=1, CLASS=2, INHERITS=3, NEW=4, LET=5, IN=6, IF=7, THEN=8, ELSE=9, 
		FI=10, WHILE=11, LOOP=12, POOL=13, CASE=14, OF=15, ARROW=16, ESAC=17, 
		ISVOID=18, NOT=19, DOT=20, AT=21, LPAREN=22, RPAREN=23, LBRACE=24, RBRACE=25, 
		SEMI_COL=26, COL=27, COMMA=28, TRUE=29, FALSE=30, STRING=31, ID=32, TYPE=33, 
		INT=34, ASSIGN=35, PLUS=36, MINUS=37, MULT=38, DIV=39, LT=40, LE=41, EQUAL=42, 
		NEG=43, ONE_LINE_COMMENT=44, MULTI_LINE_COMMENT=45, OPEN_COMM=46, CLOSE_COMM=47, 
		WS=48;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"A", "C", "D", "E", "F", "H", "I", "L", "N", "O", "P", "R", "S", "T", 
			"U", "V", "W", "CLASS", "INHERITS", "NEW", "LET", "IN", "IF", "THEN", 
			"ELSE", "FI", "WHILE", "LOOP", "POOL", "CASE", "OF", "ARROW", "ESAC", 
			"ISVOID", "NOT", "DOT", "AT", "LPAREN", "RPAREN", "LBRACE", "RBRACE", 
			"SEMI_COL", "COL", "COMMA", "TRUE", "FALSE", "STRING", "ID", "TYPE", 
			"DIGIT", "INT", "ASSIGN", "PLUS", "MINUS", "MULT", "DIV", "LT", "LE", 
			"EQUAL", "NEG", "NEW_LINE", "ONE_LINE_COMMENT", "MULTI_LINE_COMMENT", 
			"OPEN_COMM", "CLOSE_COMM", "WS", "ERROR"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, "'=>'", null, null, null, "'.'", "'@'", "'('", 
			"')'", "'{'", "'}'", "';'", "':'", "','", null, null, null, null, null, 
			null, "'<-'", "'+'", "'-'", "'*'", "'/'", "'<'", "'<='", "'='", "'~'", 
			null, null, "'(*'", "'*)'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "ERROR", "CLASS", "INHERITS", "NEW", "LET", "IN", "IF", "THEN", 
			"ELSE", "FI", "WHILE", "LOOP", "POOL", "CASE", "OF", "ARROW", "ESAC", 
			"ISVOID", "NOT", "DOT", "AT", "LPAREN", "RPAREN", "LBRACE", "RBRACE", 
			"SEMI_COL", "COL", "COMMA", "TRUE", "FALSE", "STRING", "ID", "TYPE", 
			"INT", "ASSIGN", "PLUS", "MINUS", "MULT", "DIV", "LT", "LE", "EQUAL", 
			"NEG", "ONE_LINE_COMMENT", "MULTI_LINE_COMMENT", "OPEN_COMM", "CLOSE_COMM", 
			"WS"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}


	    private void raiseError(String msg) {
	        setText(msg);
	        setType(ERROR);
	    }


	public CoolLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "CoolLexer.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getChannelNames() { return channelNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	@Override
	public void action(RuleContext _localctx, int ruleIndex, int actionIndex) {
		switch (ruleIndex) {
		case 46:
			STRING_action((RuleContext)_localctx, actionIndex);
			break;
		case 62:
			MULTI_LINE_COMMENT_action((RuleContext)_localctx, actionIndex);
			break;
		case 64:
			CLOSE_COMM_action((RuleContext)_localctx, actionIndex);
			break;
		case 66:
			ERROR_action((RuleContext)_localctx, actionIndex);
			break;
		}
	}
	private void STRING_action(RuleContext _localctx, int actionIndex) {
		switch (actionIndex) {
		case 0:

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
				
			break;
		case 1:
			 raiseError("EOF in string constant"); 
			break;
		case 2:
			 raiseError("Unterminated string constant"); 
			break;
		}
	}
	private void MULTI_LINE_COMMENT_action(RuleContext _localctx, int actionIndex) {
		switch (actionIndex) {
		case 3:
			 skip(); 
			break;
		case 4:
			 raiseError("EOF in comment"); 
			break;
		}
	}
	private void CLOSE_COMM_action(RuleContext _localctx, int actionIndex) {
		switch (actionIndex) {
		case 5:
			 raiseError("Unmatched *)"); 
			break;
		}
	}
	private void ERROR_action(RuleContext _localctx, int actionIndex) {
		switch (actionIndex) {
		case 6:
			 raiseError("Invalid character: " + getText()); 
			break;
		}
	}

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2\62\u0192\b\1\4\2"+
		"\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4"+
		"\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22"+
		"\t\22\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31"+
		"\t\31\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t"+
		" \4!\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t"+
		"+\4,\t,\4-\t-\4.\t.\4/\t/\4\60\t\60\4\61\t\61\4\62\t\62\4\63\t\63\4\64"+
		"\t\64\4\65\t\65\4\66\t\66\4\67\t\67\48\t8\49\t9\4:\t:\4;\t;\4<\t<\4=\t"+
		"=\4>\t>\4?\t?\4@\t@\4A\tA\4B\tB\4C\tC\4D\tD\3\2\3\2\3\3\3\3\3\4\3\4\3"+
		"\5\3\5\3\6\3\6\3\7\3\7\3\b\3\b\3\t\3\t\3\n\3\n\3\13\3\13\3\f\3\f\3\r\3"+
		"\r\3\16\3\16\3\17\3\17\3\20\3\20\3\21\3\21\3\22\3\22\3\23\3\23\3\23\3"+
		"\23\3\23\3\23\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\25\3\25\3"+
		"\25\3\25\3\26\3\26\3\26\3\26\3\27\3\27\3\27\3\30\3\30\3\30\3\31\3\31\3"+
		"\31\3\31\3\31\3\32\3\32\3\32\3\32\3\32\3\33\3\33\3\33\3\34\3\34\3\34\3"+
		"\34\3\34\3\34\3\35\3\35\3\35\3\35\3\35\3\36\3\36\3\36\3\36\3\36\3\37\3"+
		"\37\3\37\3\37\3\37\3 \3 \3 \3!\3!\3!\3\"\3\"\3\"\3\"\3\"\3#\3#\3#\3#\3"+
		"#\3#\3#\3$\3$\3$\3$\3%\3%\3&\3&\3\'\3\'\3(\3(\3)\3)\3*\3*\3+\3+\3,\3,"+
		"\3-\3-\3.\3.\3.\3.\3.\3/\3/\3/\3/\3/\3/\3\60\3\60\3\60\3\60\3\60\3\60"+
		"\7\60\u0124\n\60\f\60\16\60\u0127\13\60\3\60\3\60\3\60\3\60\3\60\3\60"+
		"\3\60\5\60\u0130\n\60\3\61\3\61\7\61\u0134\n\61\f\61\16\61\u0137\13\61"+
		"\3\62\3\62\7\62\u013b\n\62\f\62\16\62\u013e\13\62\3\63\3\63\3\64\6\64"+
		"\u0143\n\64\r\64\16\64\u0144\3\65\3\65\3\65\3\66\3\66\3\67\3\67\38\38"+
		"\39\39\3:\3:\3;\3;\3;\3<\3<\3=\3=\3>\5>\u015c\n>\3>\3>\3?\3?\3?\3?\7?"+
		"\u0164\n?\f?\16?\u0167\13?\3?\3?\5?\u016b\n?\3?\3?\3@\3@\3@\3@\3@\7@\u0174"+
		"\n@\f@\16@\u0177\13@\3@\3@\3@\3@\3@\3@\5@\u017f\n@\3A\3A\3A\3B\3B\3B\3"+
		"B\3B\3C\6C\u018a\nC\rC\16C\u018b\3C\3C\3D\3D\3D\5\u0125\u0165\u0175\2"+
		"E\3\2\5\2\7\2\t\2\13\2\r\2\17\2\21\2\23\2\25\2\27\2\31\2\33\2\35\2\37"+
		"\2!\2#\2%\4\'\5)\6+\7-\b/\t\61\n\63\13\65\f\67\r9\16;\17=\20?\21A\22C"+
		"\23E\24G\25I\26K\27M\30O\31Q\32S\33U\34W\35Y\36[\37] _!a\"c#e\2g$i%k&"+
		"m\'o(q)s*u+w,y-{\2}.\177/\u0081\60\u0083\61\u0085\62\u0087\3\3\2\30\4"+
		"\2CCcc\4\2EEee\4\2FFff\4\2GGgg\4\2HHhh\4\2JJjj\4\2KKkk\4\2NNnn\4\2PPp"+
		"p\4\2QQqq\4\2RRrr\4\2TTtt\4\2UUuu\4\2VVvv\4\2WWww\4\2XXxx\4\2YYyy\3\2"+
		"c|\6\2\62;C\\aac|\3\2C\\\3\2\62;\5\2\13\f\16\17\"\"\2\u018d\2%\3\2\2\2"+
		"\2\'\3\2\2\2\2)\3\2\2\2\2+\3\2\2\2\2-\3\2\2\2\2/\3\2\2\2\2\61\3\2\2\2"+
		"\2\63\3\2\2\2\2\65\3\2\2\2\2\67\3\2\2\2\29\3\2\2\2\2;\3\2\2\2\2=\3\2\2"+
		"\2\2?\3\2\2\2\2A\3\2\2\2\2C\3\2\2\2\2E\3\2\2\2\2G\3\2\2\2\2I\3\2\2\2\2"+
		"K\3\2\2\2\2M\3\2\2\2\2O\3\2\2\2\2Q\3\2\2\2\2S\3\2\2\2\2U\3\2\2\2\2W\3"+
		"\2\2\2\2Y\3\2\2\2\2[\3\2\2\2\2]\3\2\2\2\2_\3\2\2\2\2a\3\2\2\2\2c\3\2\2"+
		"\2\2g\3\2\2\2\2i\3\2\2\2\2k\3\2\2\2\2m\3\2\2\2\2o\3\2\2\2\2q\3\2\2\2\2"+
		"s\3\2\2\2\2u\3\2\2\2\2w\3\2\2\2\2y\3\2\2\2\2}\3\2\2\2\2\177\3\2\2\2\2"+
		"\u0081\3\2\2\2\2\u0083\3\2\2\2\2\u0085\3\2\2\2\2\u0087\3\2\2\2\3\u0089"+
		"\3\2\2\2\5\u008b\3\2\2\2\7\u008d\3\2\2\2\t\u008f\3\2\2\2\13\u0091\3\2"+
		"\2\2\r\u0093\3\2\2\2\17\u0095\3\2\2\2\21\u0097\3\2\2\2\23\u0099\3\2\2"+
		"\2\25\u009b\3\2\2\2\27\u009d\3\2\2\2\31\u009f\3\2\2\2\33\u00a1\3\2\2\2"+
		"\35\u00a3\3\2\2\2\37\u00a5\3\2\2\2!\u00a7\3\2\2\2#\u00a9\3\2\2\2%\u00ab"+
		"\3\2\2\2\'\u00b1\3\2\2\2)\u00ba\3\2\2\2+\u00be\3\2\2\2-\u00c2\3\2\2\2"+
		"/\u00c5\3\2\2\2\61\u00c8\3\2\2\2\63\u00cd\3\2\2\2\65\u00d2\3\2\2\2\67"+
		"\u00d5\3\2\2\29\u00db\3\2\2\2;\u00e0\3\2\2\2=\u00e5\3\2\2\2?\u00ea\3\2"+
		"\2\2A\u00ed\3\2\2\2C\u00f0\3\2\2\2E\u00f5\3\2\2\2G\u00fc\3\2\2\2I\u0100"+
		"\3\2\2\2K\u0102\3\2\2\2M\u0104\3\2\2\2O\u0106\3\2\2\2Q\u0108\3\2\2\2S"+
		"\u010a\3\2\2\2U\u010c\3\2\2\2W\u010e\3\2\2\2Y\u0110\3\2\2\2[\u0112\3\2"+
		"\2\2]\u0117\3\2\2\2_\u011d\3\2\2\2a\u0131\3\2\2\2c\u0138\3\2\2\2e\u013f"+
		"\3\2\2\2g\u0142\3\2\2\2i\u0146\3\2\2\2k\u0149\3\2\2\2m\u014b\3\2\2\2o"+
		"\u014d\3\2\2\2q\u014f\3\2\2\2s\u0151\3\2\2\2u\u0153\3\2\2\2w\u0156\3\2"+
		"\2\2y\u0158\3\2\2\2{\u015b\3\2\2\2}\u015f\3\2\2\2\177\u016e\3\2\2\2\u0081"+
		"\u0180\3\2\2\2\u0083\u0183\3\2\2\2\u0085\u0189\3\2\2\2\u0087\u018f\3\2"+
		"\2\2\u0089\u008a\t\2\2\2\u008a\4\3\2\2\2\u008b\u008c\t\3\2\2\u008c\6\3"+
		"\2\2\2\u008d\u008e\t\4\2\2\u008e\b\3\2\2\2\u008f\u0090\t\5\2\2\u0090\n"+
		"\3\2\2\2\u0091\u0092\t\6\2\2\u0092\f\3\2\2\2\u0093\u0094\t\7\2\2\u0094"+
		"\16\3\2\2\2\u0095\u0096\t\b\2\2\u0096\20\3\2\2\2\u0097\u0098\t\t\2\2\u0098"+
		"\22\3\2\2\2\u0099\u009a\t\n\2\2\u009a\24\3\2\2\2\u009b\u009c\t\13\2\2"+
		"\u009c\26\3\2\2\2\u009d\u009e\t\f\2\2\u009e\30\3\2\2\2\u009f\u00a0\t\r"+
		"\2\2\u00a0\32\3\2\2\2\u00a1\u00a2\t\16\2\2\u00a2\34\3\2\2\2\u00a3\u00a4"+
		"\t\17\2\2\u00a4\36\3\2\2\2\u00a5\u00a6\t\20\2\2\u00a6 \3\2\2\2\u00a7\u00a8"+
		"\t\21\2\2\u00a8\"\3\2\2\2\u00a9\u00aa\t\22\2\2\u00aa$\3\2\2\2\u00ab\u00ac"+
		"\5\5\3\2\u00ac\u00ad\5\21\t\2\u00ad\u00ae\5\3\2\2\u00ae\u00af\5\33\16"+
		"\2\u00af\u00b0\5\33\16\2\u00b0&\3\2\2\2\u00b1\u00b2\5\17\b\2\u00b2\u00b3"+
		"\5\23\n\2\u00b3\u00b4\5\r\7\2\u00b4\u00b5\5\t\5\2\u00b5\u00b6\5\31\r\2"+
		"\u00b6\u00b7\5\17\b\2\u00b7\u00b8\5\35\17\2\u00b8\u00b9\5\33\16\2\u00b9"+
		"(\3\2\2\2\u00ba\u00bb\5\23\n\2\u00bb\u00bc\5\t\5\2\u00bc\u00bd\5#\22\2"+
		"\u00bd*\3\2\2\2\u00be\u00bf\5\21\t\2\u00bf\u00c0\5\t\5\2\u00c0\u00c1\5"+
		"\35\17\2\u00c1,\3\2\2\2\u00c2\u00c3\5\17\b\2\u00c3\u00c4\5\23\n\2\u00c4"+
		".\3\2\2\2\u00c5\u00c6\5\17\b\2\u00c6\u00c7\5\13\6\2\u00c7\60\3\2\2\2\u00c8"+
		"\u00c9\5\35\17\2\u00c9\u00ca\5\r\7\2\u00ca\u00cb\5\t\5\2\u00cb\u00cc\5"+
		"\23\n\2\u00cc\62\3\2\2\2\u00cd\u00ce\5\t\5\2\u00ce\u00cf\5\21\t\2\u00cf"+
		"\u00d0\5\33\16\2\u00d0\u00d1\5\t\5\2\u00d1\64\3\2\2\2\u00d2\u00d3\5\13"+
		"\6\2\u00d3\u00d4\5\17\b\2\u00d4\66\3\2\2\2\u00d5\u00d6\5#\22\2\u00d6\u00d7"+
		"\5\r\7\2\u00d7\u00d8\5\17\b\2\u00d8\u00d9\5\21\t\2\u00d9\u00da\5\t\5\2"+
		"\u00da8\3\2\2\2\u00db\u00dc\5\21\t\2\u00dc\u00dd\5\25\13\2\u00dd\u00de"+
		"\5\25\13\2\u00de\u00df\5\27\f\2\u00df:\3\2\2\2\u00e0\u00e1\5\27\f\2\u00e1"+
		"\u00e2\5\25\13\2\u00e2\u00e3\5\25\13\2\u00e3\u00e4\5\21\t\2\u00e4<\3\2"+
		"\2\2\u00e5\u00e6\5\5\3\2\u00e6\u00e7\5\3\2\2\u00e7\u00e8\5\33\16\2\u00e8"+
		"\u00e9\5\t\5\2\u00e9>\3\2\2\2\u00ea\u00eb\5\25\13\2\u00eb\u00ec\5\13\6"+
		"\2\u00ec@\3\2\2\2\u00ed\u00ee\7?\2\2\u00ee\u00ef\7@\2\2\u00efB\3\2\2\2"+
		"\u00f0\u00f1\5\t\5\2\u00f1\u00f2\5\33\16\2\u00f2\u00f3\5\3\2\2\u00f3\u00f4"+
		"\5\5\3\2\u00f4D\3\2\2\2\u00f5\u00f6\5\17\b\2\u00f6\u00f7\5\33\16\2\u00f7"+
		"\u00f8\5!\21\2\u00f8\u00f9\5\25\13\2\u00f9\u00fa\5\17\b\2\u00fa\u00fb"+
		"\5\7\4\2\u00fbF\3\2\2\2\u00fc\u00fd\5\23\n\2\u00fd\u00fe\5\25\13\2\u00fe"+
		"\u00ff\5\35\17\2\u00ffH\3\2\2\2\u0100\u0101\7\60\2\2\u0101J\3\2\2\2\u0102"+
		"\u0103\7B\2\2\u0103L\3\2\2\2\u0104\u0105\7*\2\2\u0105N\3\2\2\2\u0106\u0107"+
		"\7+\2\2\u0107P\3\2\2\2\u0108\u0109\7}\2\2\u0109R\3\2\2\2\u010a\u010b\7"+
		"\177\2\2\u010bT\3\2\2\2\u010c\u010d\7=\2\2\u010dV\3\2\2\2\u010e\u010f"+
		"\7<\2\2\u010fX\3\2\2\2\u0110\u0111\7.\2\2\u0111Z\3\2\2\2\u0112\u0113\7"+
		"v\2\2\u0113\u0114\5\31\r\2\u0114\u0115\5\37\20\2\u0115\u0116\5\t\5\2\u0116"+
		"\\\3\2\2\2\u0117\u0118\7h\2\2\u0118\u0119\5\3\2\2\u0119\u011a\5\21\t\2"+
		"\u011a\u011b\5\33\16\2\u011b\u011c\5\t\5\2\u011c^\3\2\2\2\u011d\u0125"+
		"\7$\2\2\u011e\u011f\7^\2\2\u011f\u0124\7$\2\2\u0120\u0121\7^\2\2\u0121"+
		"\u0124\5{>\2\u0122\u0124\13\2\2\2\u0123\u011e\3\2\2\2\u0123\u0120\3\2"+
		"\2\2\u0123\u0122\3\2\2\2\u0124\u0127\3\2\2\2\u0125\u0126\3\2\2\2\u0125"+
		"\u0123\3\2\2\2\u0126\u012f\3\2\2\2\u0127\u0125\3\2\2\2\u0128\u0129\7$"+
		"\2\2\u0129\u0130\b\60\2\2\u012a\u012b\7\2\2\3\u012b\u0130\b\60\3\2\u012c"+
		"\u012d\5{>\2\u012d\u012e\b\60\4\2\u012e\u0130\3\2\2\2\u012f\u0128\3\2"+
		"\2\2\u012f\u012a\3\2\2\2\u012f\u012c\3\2\2\2\u0130`\3\2\2\2\u0131\u0135"+
		"\t\23\2\2\u0132\u0134\t\24\2\2\u0133\u0132\3\2\2\2\u0134\u0137\3\2\2\2"+
		"\u0135\u0133\3\2\2\2\u0135\u0136\3\2\2\2\u0136b\3\2\2\2\u0137\u0135\3"+
		"\2\2\2\u0138\u013c\t\25\2\2\u0139\u013b\t\24\2\2\u013a\u0139\3\2\2\2\u013b"+
		"\u013e\3\2\2\2\u013c\u013a\3\2\2\2\u013c\u013d\3\2\2\2\u013dd\3\2\2\2"+
		"\u013e\u013c\3\2\2\2\u013f\u0140\t\26\2\2\u0140f\3\2\2\2\u0141\u0143\5"+
		"e\63\2\u0142\u0141\3\2\2\2\u0143\u0144\3\2\2\2\u0144\u0142\3\2\2\2\u0144"+
		"\u0145\3\2\2\2\u0145h\3\2\2\2\u0146\u0147\7>\2\2\u0147\u0148\7/\2\2\u0148"+
		"j\3\2\2\2\u0149\u014a\7-\2\2\u014al\3\2\2\2\u014b\u014c\7/\2\2\u014cn"+
		"\3\2\2\2\u014d\u014e\7,\2\2\u014ep\3\2\2\2\u014f\u0150\7\61\2\2\u0150"+
		"r\3\2\2\2\u0151\u0152\7>\2\2\u0152t\3\2\2\2\u0153\u0154\7>\2\2\u0154\u0155"+
		"\7?\2\2\u0155v\3\2\2\2\u0156\u0157\7?\2\2\u0157x\3\2\2\2\u0158\u0159\7"+
		"\u0080\2\2\u0159z\3\2\2\2\u015a\u015c\7\17\2\2\u015b\u015a\3\2\2\2\u015b"+
		"\u015c\3\2\2\2\u015c\u015d\3\2\2\2\u015d\u015e\7\f\2\2\u015e|\3\2\2\2"+
		"\u015f\u0160\7/\2\2\u0160\u0161\7/\2\2\u0161\u0165\3\2\2\2\u0162\u0164"+
		"\13\2\2\2\u0163\u0162\3\2\2\2\u0164\u0167\3\2\2\2\u0165\u0166\3\2\2\2"+
		"\u0165\u0163\3\2\2\2\u0166\u016a\3\2\2\2\u0167\u0165\3\2\2\2\u0168\u016b"+
		"\5{>\2\u0169\u016b\7\2\2\3\u016a\u0168\3\2\2\2\u016a\u0169\3\2\2\2\u016b"+
		"\u016c\3\2\2\2\u016c\u016d\b?\5\2\u016d~\3\2\2\2\u016e\u016f\7*\2\2\u016f"+
		"\u0170\7,\2\2\u0170\u0175\3\2\2\2\u0171\u0174\5\177@\2\u0172\u0174\13"+
		"\2\2\2\u0173\u0171\3\2\2\2\u0173\u0172\3\2\2\2\u0174\u0177\3\2\2\2\u0175"+
		"\u0176\3\2\2\2\u0175\u0173\3\2\2\2\u0176\u017e\3\2\2\2\u0177\u0175\3\2"+
		"\2\2\u0178\u0179\7,\2\2\u0179\u017a\7+\2\2\u017a\u017b\3\2\2\2\u017b\u017f"+
		"\b@\6\2\u017c\u017d\7\2\2\3\u017d\u017f\b@\7\2\u017e\u0178\3\2\2\2\u017e"+
		"\u017c\3\2\2\2\u017f\u0080\3\2\2\2\u0180\u0181\7*\2\2\u0181\u0182\7,\2"+
		"\2\u0182\u0082\3\2\2\2\u0183\u0184\7,\2\2\u0184\u0185\7+\2\2\u0185\u0186"+
		"\3\2\2\2\u0186\u0187\bB\b\2\u0187\u0084\3\2\2\2\u0188\u018a\t\27\2\2\u0189"+
		"\u0188\3\2\2\2\u018a\u018b\3\2\2\2\u018b\u0189\3\2\2\2\u018b\u018c\3\2"+
		"\2\2\u018c\u018d\3\2\2\2\u018d\u018e\bC\5\2\u018e\u0086\3\2\2\2\u018f"+
		"\u0190\13\2\2\2\u0190\u0191\bD\t\2\u0191\u0088\3\2\2\2\20\2\u0123\u0125"+
		"\u012f\u0135\u013c\u0144\u015b\u0165\u016a\u0173\u0175\u017e\u018b\n\3"+
		"\60\2\3\60\3\3\60\4\b\2\2\3@\5\3@\6\3B\7\3D\b";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}