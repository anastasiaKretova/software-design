package state

import tokenizer.tokens.NumberToken
import tokenizer.tokens.Token
import tokenizer.Tokenizer

class NumberState(tokenizer: Tokenizer) : State(tokenizer) {
    override fun getToken(): Token {
        var c = tokenizer.current()
        val builder = StringBuilder()
        while (Character.isDigit(c)) {
            builder.append(c)
            c = tokenizer.next()
        }
        return NumberToken(builder.toString().toLong())
    }
}