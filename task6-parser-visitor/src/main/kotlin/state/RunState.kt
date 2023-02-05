package state

import tokenizer.tokens.Token
import tokenizer.Tokenizer

class RunState(tokenizer: Tokenizer) : State(tokenizer) {
    override fun getToken(): Token {
        throw UnsupportedOperationException()
    }
}