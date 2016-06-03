/*
 * Copyright (C) 2014 - 2016 by haui - all rights reserved
 */
package com.github.uscexp.splshell.parser;

import com.github.fge.grappa.parsers.BaseParser;
import com.github.fge.grappa.rules.Rule;
import com.github.uscexp.grappa.extension.annotations.AstCommand;

/**
 * Generated {@link BaseParser} implementation form 'Spl.peg' PEG input file.
 *
 * @author  PegParserGenerator
 */
public class SplParser extends BaseParser<String> {

	public Rule S() {
        return zeroOrMore(firstOf(multiLineComment(), ch(' '), ch('\t'), EOL(), singleLineComment()));
	}

	public Rule EOL() {
		return firstOf(string("\r\n"), ch('\n'), ch('\r'));
	}

	public Rule multiLineComment() {
        return sequence(string("/*"), zeroOrMore(sequence(testNot(string("*/")), ANY)), string("*/"));
	}

	public Rule singleLineComment() {
		return sequence(string("//"), zeroOrMore(sequence(testNot(EOL()), ANY)), firstOf(EOL(), EOI));
	}

	public Rule INCLUDE() {
		return sequence(string("include"), S());
	}

	public Rule WRITE() {
		return sequence(string("write"), S());
	}

	public Rule WHILE() {
		return sequence(string("while"), S());
	}

	public Rule TRUE() {
		return sequence(string("true"), S());
	}

	public Rule SWITCH() {
		return sequence(string("switch"), S());
	}

	public Rule RETURN() {
		return sequence(string("return"), S());
	}

	public Rule READ() {
		return sequence(string("read"), S());
	}

	public Rule JSH() {
		return sequence(string("jsh"), S());
	}

	public Rule IMPORT() {
		return sequence(string("import"), S());
	}

	public Rule IF() {
		return sequence(string("if"), S());
	}

	public Rule FOR() {
		return sequence(string("for"), S());
	}

	public Rule FALSE() {
		return sequence(string("false"), S());
	}

	public Rule ELSE() {
		return sequence(string("else"), S());
	}

	public Rule DO() {
		return sequence(string("do"), S());
	}

	public Rule CASE() {
		return sequence(string("case"), S());
	}

	public Rule BREAK() {
		return sequence(string("break"), S());
	}

	public Rule CONTINUE() {
		return sequence(string("continue"), S());
	}

	public Rule VOID() {
		return sequence(string("void"), S());
	}

	public Rule FILEWRITER() {
		return sequence(string("filewriter"), S());
	}

	public Rule FILEREADER() {
		return sequence(string("filereader"), S());
	}

	public Rule STRING() {
		return sequence(string("string"), S());
	}

	public Rule SHORT() {
		return sequence(string("short"), S());
	}

	public Rule LONG() {
		return sequence(string("long"), S());
	}

	public Rule INT() {
		return sequence(string("int"), S());
	}

	public Rule FLOAT() {
		return sequence(string("float"), S());
	}

	public Rule DOUBLE() {
		return sequence(string("double"), S());
	}

	public Rule CHAR() {
		return sequence(string("char"), S());
	}

	public Rule BYTE() {
		return sequence(string("byte"), S());
	}

	public Rule BOOL() {
		return sequence(string("boolean"), S());
	}

	@AstCommand
	public Rule stringLiteral() {
		return sequence(doubleQuote(), str(), doubleQuote(), S());
	}

	public Rule str() {
		return zeroOrMore(sequence(testNot(doubleQuote()), character()));
	}

	@AstCommand
	public Rule charLiteral() {
		return sequence(quote(), character(), quote(), S());
	}

	public Rule SQUARECLOSE() {
		return sequence(ch(']'), S());
	}

	public Rule SQUAREOPEN() {
		return sequence(ch('['), S());
	}

	@AstCommand
	public Rule CURLYCLOSE() {
		return sequence(ch('}'), S());
	}

	@AstCommand
	public Rule CURLYOPEN() {
		return sequence(ch('{'), S());
	}

	public Rule CLOSE() {
		return sequence(ch(')'), S());
	}

	public Rule OPEN() {
		return sequence(ch('('), S());
	}

	public Rule COMMA() {
		return sequence(ch(','), S());
	}

	public Rule COLON() {
		return sequence(ch(':'), S());
	}

	public Rule SEMICOLON() {
		return sequence(ch(';'), S());
	}

	public Rule backQuote() {
		return ch('`');
	}

	public Rule doubleQuote() {
		return ch('\"');
	}

	public Rule quote() {
		return ch('\'');
	}

	public Rule backSlash() {
		return ch('\\');
	}

	public Rule character() {
		return
			firstOf(sequence(backSlash(),
					firstOf(quote(), doubleQuote(), backQuote(), backSlash(), firstOf(ch('n'), ch('r'), ch('t')),
						sequence(charRange('0', '2'), charRange('0', '7'), charRange('0', '7')),
						sequence(charRange('0', '7'), optional(charRange('0', '7'))))), sequence(testNot(backSlash()), ANY));
	}

	public Rule exponent() {
		return sequence(firstOf(ch('e'), ch('E')), oneOrMore(firstOf(charRange('+', ']'), ch('?'), ch(' '), ch('['), charRange('0', '9'))));
	}

	@AstCommand
	public Rule floatingPointLiteral() {
		return
			firstOf(sequence(oneOrMore(charRange('0', '9')), ch('.'), zeroOrMore(charRange('0', '9')), optional(exponent()),
					optional(firstOf(ch('f'), ch('F'), ch('d'), ch('D')))),
				sequence(ch('.'), oneOrMore(charRange('0', '9')), optional(exponent()),
					optional(firstOf(ch('f'), ch('F'), ch('d'), ch('D')))),
				sequence(oneOrMore(charRange('0', '9')), exponent(), optional(firstOf(ch('f'), ch('F'), ch('d'), ch('D')))),
				sequence(oneOrMore(charRange('0', '9')), optional(exponent()), firstOf(ch('f'), ch('F'), ch('d'), ch('D'))));
	}

	public Rule octalLiteral() {
		return sequence(ch('0'), zeroOrMore(charRange('0', '7')));
	}

	public Rule hexLiteral() {
		return
			sequence(ch('0'), firstOf(ch('x'), ch('X')), oneOrMore(firstOf(charRange('0', '9'), charRange('a', 'f'), charRange('A', 'F'))));
	}

	public Rule decimalLiteral() {
		return sequence(charRange('1', '9'), zeroOrMore(charRange('0', '9')));
	}

	@AstCommand
	public Rule integerLiteral() {
		return
			firstOf(sequence(decimalLiteral(), optional(firstOf(ch('l'), ch('L')))),
				sequence(hexLiteral(), optional(firstOf(ch('l'), ch('L')))), sequence(octalLiteral(), optional(firstOf(ch('l'), ch('L')))));
	}

	public Rule identStart() {
		return firstOf(charRange('a', 'z'), charRange('A', 'Z'), ch('_'));
	}

	public Rule identCont() {
		return firstOf(identStart(), charRange('0', '9'));
	}

	@AstCommand
	public Rule IDENTIFIER() {
        return sequence(identStart(), zeroOrMore(identCont()), S());
	}

	@AstCommand
	public Rule writeStatement() {
		return sequence(WRITE(), expression(), SEMICOLON());
	}

	@AstCommand
	public Rule readStatement() {
		return sequence(READ(), IDENTIFIER(), SEMICOLON());
	}

	public Rule iOStatement() {
		return firstOf(readStatement(), writeStatement());
	}

	@AstCommand
	public Rule jshStatement() {
		return sequence(JSH(), stringLiteral(), SEMICOLON());
	}

	@AstCommand
	public Rule includeStatement() {
		return sequence(INCLUDE(), stringLiteral(), SEMICOLON());
	}

	@AstCommand
	public Rule returnStatement() {
		return sequence(RETURN(), optional(expression()), SEMICOLON());
	}

	@AstCommand
	public Rule continueStatement() {
		return sequence(CONTINUE(), SEMICOLON());
	}

	@AstCommand
	public Rule breakStatement() {
		return sequence(BREAK(), SEMICOLON());
	}

	@AstCommand
	public Rule forUpdate() {
		return statementExpressionList();
	}

	public Rule statementExpressionList() {
		return sequence(statementExpression(), zeroOrMore(sequence(COMMA(), statementExpression())));
	}

	@AstCommand
	public Rule forInit() {
		return firstOf(sequence(test(sequence(type(), IDENTIFIER())), varDeclaration()), statementExpressionList());
	}

	@AstCommand
	public Rule forStatement() {
		return
			sequence(FOR(), OPEN(), optional(forInit()), SEMICOLON(), optional(expression()), SEMICOLON(), optional(forUpdate()), CLOSE(),
				statement());
	}

	@AstCommand
	public Rule switchLabel() {
		return firstOf(sequence(CASE(), expression(), COLON()), sequence(string("default"), COLON()));
	}

	@AstCommand
	public Rule switchStatement() {
		return
			sequence(SWITCH(), OPEN(), expression(), CLOSE(), CURLYOPEN(),
				zeroOrMore(sequence(switchLabel(), zeroOrMore(blockStatement()))), CURLYCLOSE());
	}

	@AstCommand
	public Rule doWhileStatement() {
		return sequence(DO(), statement(), WHILE(), OPEN(), expression(), CLOSE(), SEMICOLON());
	}

	@AstCommand
	public Rule whileStatement() {
		return sequence(WHILE(), OPEN(), expression(), CLOSE(), statement());
	}

	@AstCommand
	public Rule ifStatement() {
		return sequence(IF(), OPEN(), expression(), CLOSE(), statement(), optional(sequence(ELSE(), statement())));
	}

	@AstCommand
	public Rule methodCallStatement() {
		return sequence(IDENTIFIER(), OPEN(), optional(sequence(expression(), zeroOrMore(sequence(COMMA(), expression())))), CLOSE());
	}

	@AstCommand
	public Rule statementExpression() {
		return
			firstOf(sequence(test(string("++")), preIncrementExpression()), sequence(test(string("--")), preDecrementExpression()),
				sequence(test(sequence(primaryExpression(), string("++"))), postIncrementExpression()),
				sequence(test(sequence(primaryExpression(), string("--"))), postDecrementExpression()), assignment());
	}

	@AstCommand
	public Rule blockStatement() {
		return firstOf(sequence(test(sequence(type(), IDENTIFIER())), varDeclaration(), SEMICOLON()), statement());
	}

	@AstCommand
	public Rule block() {
		return sequence(CURLYOPEN(), zeroOrMore(blockStatement()), CURLYCLOSE());
	}

	@AstCommand
	public Rule labeledStatement() {
		return sequence(IDENTIFIER(), COLON(), statement());
	}

	public Rule statement() {
		return
			sequence(firstOf(ch(';'), sequence(test(sequence(IDENTIFIER(), COLON())), labeledStatement()),
					sequence(test(sequence(IDENTIFIER(), OPEN())), methodCallStatement(), SEMICOLON()), block(),
					sequence(statementExpression(), SEMICOLON()), ifStatement(), switchStatement(), forStatement(), whileStatement(),
					doWhileStatement(), breakStatement(), continueStatement(), returnStatement(), iOStatement(), jshStatement()), S());
	}

	@AstCommand
	public Rule booleanLiteral() {
		return firstOf(TRUE(), FALSE());
	}

	public Rule literal() {
		return firstOf(booleanLiteral(), charLiteral(), integerLiteral(), floatingPointLiteral(), stringLiteral());
	}

	@AstCommand
	public Rule arrayId() {
		return sequence(IDENTIFIER(), zeroOrMore(sequence(SQUAREOPEN(), firstOf(id(), integerLiteral()), SQUARECLOSE())));
	}

	@AstCommand
	public Rule id() {
		return IDENTIFIER();
	}

	@AstCommand
	public Rule primaryExpression() {
		return
			sequence(firstOf(sequence(test(sequence(IDENTIFIER(), SQUAREOPEN())), arrayId()), literal(),
					sequence(test(sequence(IDENTIFIER(), OPEN())), methodCallStatement()), sequence(OPEN(), expression(), CLOSE()), id()),
				S());
	}


	@AstCommand
	public Rule postDecrementExpression() {
		return sequence(primaryExpression(), string("--"));
	}

	@AstCommand
	public Rule postIncrementExpression() {
		return sequence(primaryExpression(), string("++"));
	}

	@AstCommand
	public Rule preDecrementExpression() {
		return sequence(string("--"), primaryExpression());
	}

	@AstCommand
	public Rule preIncrementExpression() {
		return sequence(string("++"), primaryExpression());
	}

	@AstCommand
    public Rule unaryLiteral() {
        return sequence(firstOf(ch('+'), ch('-'), ch('~'), ch('!')), S());
    }

	@AstCommand
	public Rule unaryExpression() {
		return
			firstOf(sequence(unaryLiteral(), unaryExpression()), sequence(test(string("++")), preIncrementExpression()),
				sequence(test(string("--")), preDecrementExpression()),
				sequence(test(sequence(primaryExpression(), string("++"))), postIncrementExpression()),
				sequence(test(sequence(primaryExpression(), string("--"))), postDecrementExpression()), primaryExpression());
	}

	@AstCommand
    public Rule multiplicativeLiteral() {
        return sequence(firstOf(ch('*'), ch('/'), ch('%')), S());
    }

	@AstCommand
	public Rule multiplicativeExpression() {
        return sequence(unaryExpression(), zeroOrMore(sequence(multiplicativeLiteral(), unaryExpression())));
	}

	@AstCommand
    public Rule additiveLiteral() {
        return sequence(firstOf(ch('+'), ch('-')), S());
    }

	@AstCommand
	public Rule additiveExpression() {
        return sequence(multiplicativeExpression(), zeroOrMore(sequence(additiveLiteral(), multiplicativeExpression())));
	}

	@AstCommand
    public Rule relationalLiteral() {
        return sequence(firstOf(string("<="), string(">="), ch('<'), ch('>')), S());
    }

	@AstCommand
	public Rule relationalExpression() {
        return sequence(additiveExpression(), zeroOrMore(sequence(relationalLiteral(), additiveExpression())));
	}

	@AstCommand
    public Rule equalityLiteral() {
        return sequence(firstOf(string("=="), string("!=")), S());
    }

	@AstCommand
    public Rule equalityExpression() {
        return sequence(relationalExpression(), zeroOrMore(sequence(equalityLiteral(), relationalExpression())));
	}

	@AstCommand
	public Rule andExpression() {
        return sequence(equalityExpression(), zeroOrMore(sequence(ch('&'), S(), equalityExpression())));
	}

	@AstCommand
	public Rule exclusiveOrExpression() {
        return sequence(andExpression(), zeroOrMore(sequence(ch('^'), S(), andExpression())));
	}

	@AstCommand
	public Rule inclusiveOrExpression() {
        return sequence(exclusiveOrExpression(), zeroOrMore(sequence(ch('|'), S(), exclusiveOrExpression())));
	}

	@AstCommand
	public Rule conditionalAndExpression() {
        return sequence(inclusiveOrExpression(), zeroOrMore(sequence(string("&&"), S(), inclusiveOrExpression())));
	}

	@AstCommand
	public Rule conditionalOrExpression() {
        return sequence(conditionalAndExpression(), zeroOrMore(sequence(string("||"), S(), conditionalAndExpression())));
	}

	@AstCommand
	public Rule assignmentLiteral() {
		return
			sequence(firstOf(string("+="), string("-="), string("*="), string("/="), string("&="), string("%="), string("|="), string("^="),
					ch('=')), S());
	}

	@AstCommand
    public Rule assignment() {
        return sequence(primaryExpression(), assignmentLiteral(), variableInitializer());
    }

	@AstCommand
	public Rule arrayInitializer() {
		return
			sequence(CURLYOPEN(), optional(sequence(variableInitializer(), zeroOrMore(sequence(COMMA(), variableInitializer())))),
				CURLYCLOSE());
	}

	@AstCommand
	public Rule variableInitializer() {
		return firstOf(arrayInitializer(), expression());
	}

	public Rule expression() {
		return
			sequence(firstOf(conditionalOrExpression(), sequence(test(sequence(IDENTIFIER(), OPEN())), methodCallStatement()),
					sequence(test(string("++")), preIncrementExpression()), sequence(test(string("--")), preDecrementExpression()),
					sequence(test(sequence(primaryExpression(), string("++"))), postIncrementExpression()),
					sequence(test(sequence(primaryExpression(), string("--"))), postDecrementExpression()), assignment()), S());
	}

	@AstCommand
	public Rule resultType() {
		return firstOf(VOID(), type());
	}

	@AstCommand
	public Rule type() {
		return firstOf(BOOL(), BYTE(), CHAR(), DOUBLE(), FLOAT(), INT(), LONG(), SHORT(), STRING(), FILEREADER(), FILEWRITER());
	}

	@AstCommand
	public Rule mappedBlock() {
		return sequence(CURLYOPEN(), zeroOrMore(blockStatement()), CURLYCLOSE());
	}

	@AstCommand
	public Rule mappedMethodDeclaration() {
		return mappedBlock();
	}

	@AstCommand
	public Rule mainDeclaratorId() {
		return sequence(string("args"), zeroOrMore(sequence(SQUAREOPEN(), SQUARECLOSE())));
	}

	@AstCommand
	public Rule mainParameter() {
		return sequence(OPEN(), STRING(), mainDeclaratorId(), CLOSE());
	}

	@AstCommand
	public Rule mainMethodDeclarator() {
		return sequence(string("main"), mainParameter());
	}

	@AstCommand
	public Rule mainMethodDeclaration() {
		return sequence(INT(), mainMethodDeclarator(), block());
	}

	@AstCommand
	public Rule formalParameter() {
		return sequence(type(), variableDeclaratorId());
	}

	@AstCommand
	public Rule formalParameters() {
		return sequence(OPEN(), optional(sequence(formalParameter(), zeroOrMore(sequence(COMMA(), formalParameter())))), CLOSE());
	}

	@AstCommand
	public Rule methodDeclarator() {
		return sequence(IDENTIFIER(), formalParameters());
	}

	@AstCommand
	public Rule methodDeclaration() {
		return sequence(resultType(), methodDeclarator(), block());
	}

	@AstCommand
	public Rule variableDeclaratorId() {
		return sequence(IDENTIFIER(), zeroOrMore(sequence(SQUAREOPEN(), SQUARECLOSE())));
	}

	@AstCommand
	public Rule variableDeclarator() {
        return sequence(variableDeclaratorId(), optional(sequence(assignmentLiteral(), variableInitializer())));
	}

	@AstCommand
	public Rule varDeclaration() {
		return sequence(type(), variableDeclarator(), zeroOrMore(sequence(COMMA(), variableDeclarator())));
	}

	@AstCommand
	public Rule compilationUnit() {
		return
			sequence(S(), zeroOrMore(sequence(test(INCLUDE()), includeStatement())),
				zeroOrMore(
					sequence(testNot(sequence(INT(), string("main"), OPEN())), test(sequence(resultType(), IDENTIFIER(), OPEN())),
						methodDeclaration())), zeroOrMore(sequence(test(sequence(type(), IDENTIFIER())), varDeclaration(), SEMICOLON())),
				optional(
					firstOf(sequence(test(sequence(INT(), string("main"), OPEN())), mainMethodDeclaration()), zeroOrMore(statement()))),
				EOI);
	}
}
