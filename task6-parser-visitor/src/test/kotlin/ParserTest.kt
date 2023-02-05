import org.testng.Assert.*
import org.testng.annotations.Test

import tokenizer.tokens.Token
import tokenizer.Tokenizer
import visitor.CalcVisitor
import visitor.RPNVisitor
import visitor.PrintTokensVisitor

class ParserTest {
    @Test
    fun emptyTest() {
        val input = ""
        val tokens: List<Token> = Tokenizer.parse(input)
        val RPNTokens = RPNVisitor.toRPN(tokens)

        assertEquals("", PrintTokensVisitor.print(tokens))
        assertEquals("", PrintTokensVisitor.print(RPNTokens))
        assertEquals(0, CalcVisitor.calculate(RPNTokens))
    }

    @Test
    fun number() {
        val input = "00120"
        val tokens: List<Token> = Tokenizer.parse(input)
        val RPNTokens = RPNVisitor.toRPN(tokens)

        assertEquals("NUMBER(120) ", PrintTokensVisitor.print(tokens))
        assertEquals("NUMBER(120) ", PrintTokensVisitor.print(RPNTokens))
        assertEquals(120, CalcVisitor.calculate(RPNTokens))
    }

    @Test
    fun plus() {
        val input = "1 + 2"
        val tokens: List<Token> = Tokenizer.parse(input)
        val RPNTokens = RPNVisitor.toRPN(tokens)

        assertEquals("NUMBER(1) PLUS NUMBER(2) ", PrintTokensVisitor.print(tokens))
        assertEquals("NUMBER(1) NUMBER(2) PLUS ", PrintTokensVisitor.print(RPNTokens))
        assertEquals(3, CalcVisitor.calculate(RPNTokens))
    }

    @Test
    fun minus() {
        val input = "2 - 1"
        val tokens: List<Token> = Tokenizer.parse(input)
        val RPNTokens = RPNVisitor.toRPN(tokens)

        assertEquals("NUMBER(2) MINUS NUMBER(1) ", PrintTokensVisitor.print(tokens))
        assertEquals("NUMBER(2) NUMBER(1) MINUS ", PrintTokensVisitor.print(RPNTokens))
        assertEquals(1, CalcVisitor.calculate(RPNTokens))
    }

    @Test
    fun mul() {
        val input = "3 * 2"
        val tokens: List<Token> = Tokenizer.parse(input)
        val RPNTokens = RPNVisitor.toRPN(tokens)

        assertEquals("NUMBER(3) MUL NUMBER(2) ", PrintTokensVisitor.print(tokens))
        assertEquals("NUMBER(3) NUMBER(2) MUL ", PrintTokensVisitor.print(RPNTokens))
        assertEquals(6, CalcVisitor.calculate(RPNTokens))
    }

    @Test
    fun div() {
        val input = "8 / 4"
        val tokens: List<Token> = Tokenizer.parse(input)
        val RPNTokens = RPNVisitor.toRPN(tokens)

        assertEquals("NUMBER(8) DIV NUMBER(4) ", PrintTokensVisitor.print(tokens))
        assertEquals("NUMBER(8) NUMBER(4) DIV ", PrintTokensVisitor.print(RPNTokens))
        assertEquals(2, CalcVisitor.calculate(RPNTokens))
    }

    @Test
    fun expression() {
        val input = "(23 + 10) * 5 - 3 * (32 + 5) * (10 - 4 * 5) + 8 / 2"
        val tokens: List<Token> = Tokenizer.parse(input)
        val RPNTokens = RPNVisitor.toRPN(tokens)

        assertEquals("LEFT NUMBER(23) PLUS NUMBER(10) RIGHT MUL NUMBER(5) MINUS NUMBER(3) MUL LEFT NUMBER(32) PLUS NUMBER(5) RIGHT MUL LEFT NUMBER(10) MINUS NUMBER(4) MUL NUMBER(5) RIGHT PLUS NUMBER(8) DIV NUMBER(2) ", PrintTokensVisitor.print(tokens))
        assertEquals("NUMBER(23) NUMBER(10) PLUS NUMBER(5) MUL NUMBER(3) NUMBER(32) NUMBER(5) PLUS MUL NUMBER(10) NUMBER(4) NUMBER(5) MUL MINUS MUL MINUS NUMBER(8) NUMBER(2) DIV PLUS ", PrintTokensVisitor.print(RPNTokens))
        assertEquals(1279, CalcVisitor.calculate(RPNTokens))
    }
}