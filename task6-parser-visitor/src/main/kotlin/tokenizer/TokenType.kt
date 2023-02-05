package tokenizer

enum class TokenType constructor(val priority: Int) {
    LEFT(0),
    RIGHT(0),
    PLUS(1),
    MINUS(1),
    MUL(2),
    DIV(2),
    NUMBER(3);
}
