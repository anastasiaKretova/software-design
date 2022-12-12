package state

import tokenizer.tokens.OperationToken
import tokenizer.tokens.Token
import tokenizer.TokenType
import tokenizer.Tokenizer

class OperationState(tokenizer: Tokenizer) : State(tokenizer) {
    override fun getToken(): Token {
        val c = tokenizer.current()
        tokenizer.next()
        return OperationToken(parseType(c))
    }

    private fun parseType(c: Char): TokenType {
        return (when (c) {
            '+' -> TokenType.PLUS
            '-' -> TokenType.MINUS
            '*' -> TokenType.MUL
            '/' -> TokenType.DIV
            else -> throw IllegalArgumentException("Unsupported operation '$c' found")
        })
    }
}