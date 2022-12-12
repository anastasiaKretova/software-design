package visitor

import tokenizer.tokens.BracketToken
import tokenizer.tokens.NumberToken
import tokenizer.tokens.OperationToken

interface TokenVisitor {
    fun visit(token: NumberToken)
    fun visit(token: BracketToken)
    fun visit(token: OperationToken)
}
