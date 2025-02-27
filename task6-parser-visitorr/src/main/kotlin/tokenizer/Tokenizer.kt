package tokenizer

import state.*
import tokenizer.tokens.Token
import java.util.*

class Tokenizer private constructor(private val input: String) {
    private var index = 0
    private val tokens: MutableList<Token>
    private var state: State

    private fun parse(): List<Token> {
        while (index < input.length && input[index] != '$') {
            val ch = input[index]
            if (Character.isWhitespace(ch)) {
                index++
                continue
            }
            state = (when {
                Character.isDigit(ch) -> {
                    NumberState(this)
                }

                isBracket(ch) -> {
                    BracketState(this)
                }

                isOperation(ch) -> {
                    OperationState(this)
                }

                else -> RunState(this)
            })
            tokens.add(state.getToken())
        }
        return tokens
    }

    fun current(): Char {
        return if (index < input.length) {
            input[index]
        } else {
            throw IndexOutOfBoundsException("Index out of bound: $index")
        }
    }

    fun getIndex(): Int {
        return index
    }

    operator fun next(): Char {
        index++
        return current()
    }

    private fun isBracket(ch: Char): Boolean {
        return ch == '(' || ch == ')'
    }

    private fun isOperation(ch: Char): Boolean {
        return ch in listOf('+', '-', '*', '/')
    }

    companion object {
        fun parse(s: String): List<Token> {
            var input = s
            input += '$'
            return Tokenizer(input).parse()
        }
    }

    init {
        state = RunState(this)
        tokens = ArrayList()
    }
}