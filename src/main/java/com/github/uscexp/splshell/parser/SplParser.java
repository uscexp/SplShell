/*
 * Copyright (C) 2014 - 2026 by haui - all rights reserved
 */
package com.github.uscexp.splshell.parser;

import com.github.uscexp.parboiled.extension.annotations.AstCommand;
import org.parboiled.Rule;
import org.parboiled.parser.BaseParser;

/**
 * Generated {@link BaseParser} implementation form 'Spl.peg' PEG input file.
 *
 * @author  PegParserGenerator
 */
public class SplParser extends BaseParser<String> {

	public Rule S() {
        return ZeroOrMore(FirstOf(multiLineComment(), Ch(' '), Ch('\t'), EOL(), singleLineComment()));
	}

	public Rule EOL() {
		return FirstOf(String("\r\n"), Ch('\n'), Ch('\r'));
	}

	public Rule multiLineComment() {
        return Sequence(String("/*"), ZeroOrMore(Sequence(TestNot(String("*/")), ANY)), String("*/"));
	}

	public Rule singleLineComment() {
		return Sequence(String("//"), ZeroOrMore(Sequence(TestNot(EOL()), ANY)), FirstOf(EOL(), EOI));
	}

	public Rule INCLUDE() {
		return Sequence(String("include"), S());
	}

	public Rule WRITE() {
		return Sequence(String("write"), S());
	}

	public Rule WHILE() {
		return Sequence(String("while"), S());
	}

	public Rule TRUE() {
		return Sequence(String("true"), S());
	}

	public Rule SWITCH() {
		return Sequence(String("switch"), S());
	}

	public Rule RETURN() {
		return Sequence(String("return"), S());
	}

	public Rule READ() {
		return Sequence(String("read"), S());
	}

	public Rule JSH() {
		return Sequence(String("jsh"), S());
	}

	public Rule IMPORT() {
		return Sequence(String("import"), S());
	}

	public Rule IF() {
		return Sequence(String("if"), S());
	}

	public Rule FOR() {
		return Sequence(String("for"), S());
	}

	public Rule FALSE() {
		return Sequence(String("false"), S());
	}

	public Rule ELSE() {
		return Sequence(String("else"), S());
	}

	public Rule DO() {
		return Sequence(String("do"), S());
	}

	public Rule CASE() {
		return Sequence(String("case"), S());
	}

	public Rule BREAK() {
		return Sequence(String("break"), S());
	}

	public Rule CONTINUE() {
		return Sequence(String("continue"), S());
	}

	public Rule VOID() {
		return Sequence(String("void"), S());
	}

	public Rule FILEWRITER() {
		return Sequence(String("filewriter"), S());
	}

	public Rule FILEREADER() {
		return Sequence(String("filereader"), S());
	}

	public Rule STRING() {
		return Sequence(String("string"), S());
	}

	public Rule SHORT() {
		return Sequence(String("short"), S());
	}

	public Rule LONG() {
		return Sequence(String("long"), S());
	}

	public Rule INT() {
		return Sequence(String("int"), S());
	}

	public Rule FLOAT() {
		return Sequence(String("float"), S());
	}

	public Rule DOUBLE() {
		return Sequence(String("double"), S());
	}

	public Rule CHAR() {
		return Sequence(String("char"), S());
	}

	public Rule BYTE() {
		return Sequence(String("byte"), S());
	}

	public Rule BOOL() {
		return Sequence(String("boolean"), S());
	}

	@AstCommand
	public Rule stringLiteral() {
		return Sequence(doubleQuote(), str(), doubleQuote(), S());
	}

	public Rule str() {
		return ZeroOrMore(Sequence(TestNot(doubleQuote()), character()));
	}

	@AstCommand
	public Rule charLiteral() {
		return Sequence(quote(), character(), quote(), S());
	}

	public Rule SQUARECLOSE() {
		return Sequence(Ch(']'), S());
	}

	public Rule SQUAREOPEN() {
		return Sequence(Ch('['), S());
	}

	@AstCommand
	public Rule CURLYCLOSE() {
		return Sequence(Ch('}'), S());
	}

	@AstCommand
	public Rule CURLYOPEN() {
		return Sequence(Ch('{'), S());
	}

	public Rule CLOSE() {
		return Sequence(Ch(')'), S());
	}

	public Rule OPEN() {
		return Sequence(Ch('('), S());
	}

	public Rule COMMA() {
		return Sequence(Ch(','), S());
	}

	public Rule COLON() {
		return Sequence(Ch(':'), S());
	}

	public Rule SEMICOLON() {
		return Sequence(Ch(';'), S());
	}

	public Rule backQuote() {
		return Ch('`');
	}

	public Rule doubleQuote() {
		return Ch('\"');
	}

	public Rule quote() {
		return Ch('\'');
	}

	public Rule backSlash() {
		return Ch('\\');
	}

	public Rule character() {
		return
			FirstOf(Sequence(backSlash(),
					FirstOf(quote(), doubleQuote(), backQuote(), backSlash(), FirstOf(Ch('n'), Ch('r'), Ch('t')),
						Sequence(CharRange('0', '2'), CharRange('0', '7'), CharRange('0', '7')),
						Sequence(CharRange('0', '7'), Optional(CharRange('0', '7'))))), Sequence(TestNot(backSlash()), ANY));
	}

	public Rule exponent() {
		return Sequence(FirstOf(Ch('e'), Ch('E')), OneOrMore(FirstOf(CharRange('+', ']'), Ch('?'), Ch(' '), Ch('['), CharRange('0', '9'))));
	}

	@AstCommand
	public Rule floatingPointLiteral() {
		return
			FirstOf(Sequence(OneOrMore(CharRange('0', '9')), Ch('.'), ZeroOrMore(CharRange('0', '9')), Optional(exponent()),
					Optional(FirstOf(Ch('f'), Ch('F'), Ch('d'), Ch('D')))),
				Sequence(Ch('.'), OneOrMore(CharRange('0', '9')), Optional(exponent()),
					Optional(FirstOf(Ch('f'), Ch('F'), Ch('d'), Ch('D')))),
				Sequence(OneOrMore(CharRange('0', '9')), exponent(), Optional(FirstOf(Ch('f'), Ch('F'), Ch('d'), Ch('D')))),
				Sequence(OneOrMore(CharRange('0', '9')), Optional(exponent()), FirstOf(Ch('f'), Ch('F'), Ch('d'), Ch('D'))));
	}

	public Rule octalLiteral() {
		return Sequence(Ch('0'), ZeroOrMore(CharRange('0', '7')));
	}

	public Rule hexLiteral() {
		return
			Sequence(Ch('0'), FirstOf(Ch('x'), Ch('X')), OneOrMore(FirstOf(CharRange('0', '9'), CharRange('a', 'f'), CharRange('A', 'F'))));
	}

	public Rule decimalLiteral() {
		return Sequence(CharRange('1', '9'), ZeroOrMore(CharRange('0', '9')));
	}

	@AstCommand
	public Rule integerLiteral() {
		return
			FirstOf(Sequence(decimalLiteral(), Optional(FirstOf(Ch('l'), Ch('L')))),
				Sequence(hexLiteral(), Optional(FirstOf(Ch('l'), Ch('L')))), Sequence(octalLiteral(), Optional(FirstOf(Ch('l'), Ch('L')))));
	}

	public Rule identStart() {
		return FirstOf(CharRange('a', 'z'), CharRange('A', 'Z'), Ch('_'));
	}

	public Rule identCont() {
		return FirstOf(identStart(), CharRange('0', '9'));
	}

	@AstCommand
	public Rule IDENTIFIER() {
        return Sequence(identStart(), ZeroOrMore(identCont()), S());
	}

	@AstCommand
	public Rule writeStatement() {
		return Sequence(WRITE(), expression(), SEMICOLON());
	}

	@AstCommand
	public Rule readStatement() {
		return Sequence(READ(), IDENTIFIER(), SEMICOLON());
	}

	public Rule iOStatement() {
		return FirstOf(readStatement(), writeStatement());
	}

	@AstCommand
	public Rule jshStatement() {
		return Sequence(JSH(), stringLiteral(), SEMICOLON());
	}

	@AstCommand
	public Rule includeStatement() {
		return Sequence(INCLUDE(), stringLiteral(), SEMICOLON());
	}

	@AstCommand
	public Rule returnStatement() {
		return Sequence(RETURN(), Optional(expression()), SEMICOLON());
	}

	@AstCommand
	public Rule continueStatement() {
		return Sequence(CONTINUE(), SEMICOLON());
	}

	@AstCommand
	public Rule breakStatement() {
		return Sequence(BREAK(), SEMICOLON());
	}

	@AstCommand
	public Rule forUpdate() {
		return statementExpressionList();
	}

	public Rule statementExpressionList() {
		return Sequence(statementExpression(), ZeroOrMore(Sequence(COMMA(), statementExpression())));
	}

	@AstCommand
	public Rule forInit() {
		return FirstOf(Sequence(Test(Sequence(type(), IDENTIFIER())), varDeclaration()), statementExpressionList());
	}

	@AstCommand
	public Rule forStatement() {
		return
			Sequence(FOR(), OPEN(), Optional(forInit()), SEMICOLON(), Optional(expression()), SEMICOLON(), Optional(forUpdate()), CLOSE(),
				statement());
	}

	@AstCommand
	public Rule switchLabel() {
		return FirstOf(Sequence(CASE(), expression(), COLON()), Sequence(String("default"), COLON()));
	}

	@AstCommand
	public Rule switchStatement() {
		return
			Sequence(SWITCH(), OPEN(), expression(), CLOSE(), CURLYOPEN(),
				ZeroOrMore(Sequence(switchLabel(), ZeroOrMore(blockStatement()))), CURLYCLOSE());
	}

	@AstCommand
	public Rule doWhileStatement() {
		return Sequence(DO(), statement(), WHILE(), OPEN(), expression(), CLOSE(), SEMICOLON());
	}

	@AstCommand
	public Rule whileStatement() {
		return Sequence(WHILE(), OPEN(), expression(), CLOSE(), statement());
	}

	@AstCommand
	public Rule ifStatement() {
		return Sequence(IF(), OPEN(), expression(), CLOSE(), statement(), Optional(Sequence(ELSE(), statement())));
	}

	@AstCommand
	public Rule methodCallStatement() {
		return Sequence(IDENTIFIER(), OPEN(), Optional(Sequence(expression(), ZeroOrMore(Sequence(COMMA(), expression())))), CLOSE());
	}

	@AstCommand
	public Rule statementExpression() {
		return
			FirstOf(Sequence(Test(String("++")), preIncrementExpression()), Sequence(Test(String("--")), preDecrementExpression()),
				Sequence(Test(Sequence(primaryExpression(), String("++"))), postIncrementExpression()),
				Sequence(Test(Sequence(primaryExpression(), String("--"))), postDecrementExpression()), assignment());
	}

	@AstCommand
	public Rule blockStatement() {
		return FirstOf(Sequence(Test(Sequence(type(), IDENTIFIER())), varDeclaration(), SEMICOLON()), statement());
	}

	@AstCommand
	public Rule block() {
		return Sequence(CURLYOPEN(), ZeroOrMore(blockStatement()), CURLYCLOSE());
	}

	@AstCommand
	public Rule labeledStatement() {
		return Sequence(IDENTIFIER(), COLON(), statement());
	}

	public Rule statement() {
		return
			Sequence(FirstOf(Ch(';'), Sequence(Test(Sequence(IDENTIFIER(), COLON())), labeledStatement()),
					Sequence(Test(Sequence(IDENTIFIER(), OPEN())), methodCallStatement(), SEMICOLON()), block(),
					Sequence(statementExpression(), SEMICOLON()), ifStatement(), switchStatement(), forStatement(), whileStatement(),
					doWhileStatement(), breakStatement(), continueStatement(), returnStatement(), iOStatement(), jshStatement()), S());
	}

	@AstCommand
	public Rule booleanLiteral() {
		return FirstOf(TRUE(), FALSE());
	}

	public Rule literal() {
		return FirstOf(booleanLiteral(), charLiteral(), integerLiteral(), floatingPointLiteral(), stringLiteral());
	}

	@AstCommand
	public Rule arrayId() {
		return Sequence(IDENTIFIER(), ZeroOrMore(Sequence(SQUAREOPEN(), FirstOf(id(), integerLiteral()), SQUARECLOSE())));
	}

	@AstCommand
	public Rule id() {
		return IDENTIFIER();
	}

	@AstCommand
	public Rule primaryExpression() {
		return
			Sequence(FirstOf(Sequence(Test(Sequence(IDENTIFIER(), SQUAREOPEN())), arrayId()), literal(),
					Sequence(Test(Sequence(IDENTIFIER(), OPEN())), methodCallStatement()), Sequence(OPEN(), expression(), CLOSE()), id()),
				S());
	}


	@AstCommand
	public Rule postDecrementExpression() {
		return Sequence(primaryExpression(), String("--"));
	}

	@AstCommand
	public Rule postIncrementExpression() {
		return Sequence(primaryExpression(), String("++"));
	}

	@AstCommand
	public Rule preDecrementExpression() {
		return Sequence(String("--"), primaryExpression());
	}

	@AstCommand
	public Rule preIncrementExpression() {
		return Sequence(String("++"), primaryExpression());
	}

	@AstCommand
    public Rule unaryLiteral() {
        return Sequence(FirstOf(Ch('+'), Ch('-'), Ch('~'), Ch('!')), S());
    }

	@AstCommand
	public Rule unaryExpression() {
		return
			FirstOf(Sequence(unaryLiteral(), unaryExpression()), Sequence(Test(String("++")), preIncrementExpression()),
				Sequence(Test(String("--")), preDecrementExpression()),
				Sequence(Test(Sequence(primaryExpression(), String("++"))), postIncrementExpression()),
				Sequence(Test(Sequence(primaryExpression(), String("--"))), postDecrementExpression()), primaryExpression());
	}

	@AstCommand
    public Rule multiplicativeLiteral() {
        return Sequence(FirstOf(Ch('*'), Ch('/'), Ch('%')), S());
    }

	@AstCommand
	public Rule multiplicativeExpression() {
        return Sequence(unaryExpression(), ZeroOrMore(Sequence(multiplicativeLiteral(), unaryExpression())));
	}

	@AstCommand
    public Rule additiveLiteral() {
        return Sequence(FirstOf(Ch('+'), Ch('-')), S());
    }

	@AstCommand
	public Rule additiveExpression() {
        return Sequence(multiplicativeExpression(), ZeroOrMore(Sequence(additiveLiteral(), multiplicativeExpression())));
	}

	@AstCommand
    public Rule relationalLiteral() {
        return Sequence(FirstOf(String("<="), String(">="), Ch('<'), Ch('>')), S());
    }

	@AstCommand
	public Rule relationalExpression() {
        return Sequence(additiveExpression(), ZeroOrMore(Sequence(relationalLiteral(), additiveExpression())));
	}

	@AstCommand
    public Rule equalityLiteral() {
        return Sequence(FirstOf(String("=="), String("!=")), S());
    }

	@AstCommand
    public Rule equalityExpression() {
        return Sequence(relationalExpression(), ZeroOrMore(Sequence(equalityLiteral(), relationalExpression())));
	}

	@AstCommand
	public Rule andExpression() {
        return Sequence(equalityExpression(), ZeroOrMore(Sequence(Ch('&'), S(), equalityExpression())));
	}

	@AstCommand
	public Rule exclusiveOrExpression() {
        return Sequence(andExpression(), ZeroOrMore(Sequence(Ch('^'), S(), andExpression())));
	}

	@AstCommand
	public Rule inclusiveOrExpression() {
        return Sequence(exclusiveOrExpression(), ZeroOrMore(Sequence(Ch('|'), S(), exclusiveOrExpression())));
	}

	@AstCommand
	public Rule conditionalAndExpression() {
        return Sequence(inclusiveOrExpression(), ZeroOrMore(Sequence(String("&&"), S(), inclusiveOrExpression())));
	}

	@AstCommand
	public Rule conditionalOrExpression() {
        return Sequence(conditionalAndExpression(), ZeroOrMore(Sequence(String("||"), S(), conditionalAndExpression())));
	}

	@AstCommand
	public Rule assignmentLiteral() {
		return
			Sequence(FirstOf(String("+="), String("-="), String("*="), String("/="), String("&="), String("%="), String("|="), String("^="),
					Ch('=')), S());
	}

	@AstCommand
    public Rule assignment() {
        return Sequence(primaryExpression(), assignmentLiteral(), variableInitializer());
    }

	@AstCommand
	public Rule arrayInitializer() {
		return
			Sequence(CURLYOPEN(), Optional(Sequence(variableInitializer(), ZeroOrMore(Sequence(COMMA(), variableInitializer())))),
				CURLYCLOSE());
	}

	@AstCommand
	public Rule variableInitializer() {
		return FirstOf(arrayInitializer(), expression());
	}

	public Rule expression() {
		return
			Sequence(FirstOf(conditionalOrExpression(), Sequence(Test(Sequence(IDENTIFIER(), OPEN())), methodCallStatement()),
					Sequence(Test(String("++")), preIncrementExpression()), Sequence(Test(String("--")), preDecrementExpression()),
					Sequence(Test(Sequence(primaryExpression(), String("++"))), postIncrementExpression()),
					Sequence(Test(Sequence(primaryExpression(), String("--"))), postDecrementExpression()), assignment()), S());
	}

	@AstCommand
	public Rule resultType() {
		return FirstOf(VOID(), type());
	}

	@AstCommand
	public Rule type() {
		return FirstOf(BOOL(), BYTE(), CHAR(), DOUBLE(), FLOAT(), INT(), LONG(), SHORT(), STRING(), FILEREADER(), FILEWRITER());
	}

	@AstCommand
	public Rule mappedBlock() {
		return Sequence(CURLYOPEN(), ZeroOrMore(blockStatement()), CURLYCLOSE());
	}

	@AstCommand
	public Rule mappedMethodDeclaration() {
		return mappedBlock();
	}

	@AstCommand
	public Rule mainDeclaratorId() {
		return Sequence(String("args"), ZeroOrMore(Sequence(SQUAREOPEN(), SQUARECLOSE())));
	}

	@AstCommand
	public Rule mainParameter() {
		return Sequence(OPEN(), STRING(), mainDeclaratorId(), CLOSE());
	}

	@AstCommand
	public Rule mainMethodDeclarator() {
		return Sequence(String("main"), mainParameter());
	}

	@AstCommand
	public Rule mainMethodDeclaration() {
		return Sequence(INT(), mainMethodDeclarator(), block());
	}

	@AstCommand
	public Rule formalParameter() {
		return Sequence(type(), variableDeclaratorId());
	}

	@AstCommand
	public Rule formalParameters() {
		return Sequence(OPEN(), Optional(Sequence(formalParameter(), ZeroOrMore(Sequence(COMMA(), formalParameter())))), CLOSE());
	}

	@AstCommand
	public Rule methodDeclarator() {
		return Sequence(IDENTIFIER(), formalParameters());
	}

	@AstCommand
	public Rule methodDeclaration() {
		return Sequence(resultType(), methodDeclarator(), block());
	}

	@AstCommand
	public Rule variableDeclaratorId() {
		return Sequence(IDENTIFIER(), ZeroOrMore(Sequence(SQUAREOPEN(), SQUARECLOSE())));
	}

	@AstCommand
	public Rule variableDeclarator() {
        return Sequence(variableDeclaratorId(), Optional(Sequence(assignmentLiteral(), variableInitializer())));
	}

	@AstCommand
	public Rule varDeclaration() {
		return Sequence(type(), variableDeclarator(), ZeroOrMore(Sequence(COMMA(), variableDeclarator())));
	}

	@AstCommand
	public Rule compilationUnit() {
		return
			Sequence(S(), ZeroOrMore(Sequence(Test(INCLUDE()), includeStatement())),
				ZeroOrMore(
					Sequence(TestNot(Sequence(INT(), String("main"), OPEN())), Test(Sequence(resultType(), IDENTIFIER(), OPEN())),
						methodDeclaration())), ZeroOrMore(Sequence(Test(Sequence(type(), IDENTIFIER())), varDeclaration(), SEMICOLON())),
				Optional(
					FirstOf(Sequence(Test(Sequence(INT(), String("main"), OPEN())), mainMethodDeclaration()), ZeroOrMore(statement()))),
				EOI);
	}
}
