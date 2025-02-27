package visitor

import tokenizer.tokens.BracketToken
import tokenizer.tokens.NumberToken
import tokenizer.tokens.OperationToken
import tokenizer.tokens.Token
import java.lang.StringBuilder

class PrintTokensVisitor : TokenVisitor {
    private val stringBuilder = StringBuilder()

    fun walk(tokens: List<Token>): String {
        tokens.forEach { token -> token.accept(this) }
        return stringBuilder.toString()
    }

    override fun visit(token: NumberToken) {
        add(token)
    }

    override fun visit(token: BracketToken) {
        add(token)
    }

    override fun visit(token: OperationToken) {
        add(token)
    }

    private fun add(token: Token) {
        stringBuilder.append(token.toString()).append(' ')
    }

    companion object {
        fun print(tokens: List<Token>): String {
            return PrintTokensVisitor().walk(tokens)
        }
    }
}