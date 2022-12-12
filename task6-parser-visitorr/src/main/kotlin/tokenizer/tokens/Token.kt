package tokenizer.tokens

import visitor.TokenVisitor
import tokenizer.TokenType

interface Token {
    fun accept(visitor: TokenVisitor)
    val type: TokenType

    override fun toString(): String
}