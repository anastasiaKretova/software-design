package state

import tokenizer.tokens.Token
import tokenizer.Tokenizer

abstract class State(protected val tokenizer: Tokenizer) {
    abstract fun getToken(): Token
}