package state

import tokenizer.tokens.BracketToken
import tokenizer.tokens.Token
import tokenizer.TokenType
import tokenizer.Tokenizer

class BracketState(tokenizer: Tokenizer) : State(tokenizer) {
    override fun getToken(): Token {
        val c = tokenizer.current()
        tokenizer.next()
        return BracketToken(parseType(c, tokenizer.getIndex()))
    }

    private fun parseType(c: Char, index: Int): TokenType {
        return (when (c) {
            '(' -> TokenType.LEFT
            ')' -> TokenType.RIGHT
            else -> throw IllegalArgumentException("Unexpected character '$c' found at '$index'")
        })
    }
}