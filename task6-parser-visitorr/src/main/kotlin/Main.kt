import tokenizer.tokens.Token
import tokenizer.Tokenizer
import visitor.CalcVisitor
import visitor.RPNVisitor
import visitor.PrintTokensVisitor
import java.io.BufferedReader
import java.io.InputStreamReader
import java.nio.file.Files
import java.nio.file.Paths

fun main(args: Array<String>) {
    BufferedReader(InputStreamReader(System.`in`)).use { fin ->
//    Files.newBufferedReader(Paths.get("test.txt")).use { fin ->
        while (true) {
            val input = fin.readLine() ?: break
            val tokens: List<Token> = Tokenizer.parse(input)
            val RPNTokens = RPNVisitor.toRPN(tokens)

            println("tokens    : " + PrintTokensVisitor.print(tokens))
            println("RPN       : " + PrintTokensVisitor.print(RPNTokens))
            println("calculated: " + CalcVisitor.calculate(RPNTokens))
            println()
        }
    }
}
