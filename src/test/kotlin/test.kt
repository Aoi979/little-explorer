import icu.aoikajitsu.parser.MarkdownParser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.test.Test


class Test {
    @Test
    fun test(){
       val  text = """### hello
           你好
       """.trimMargin()
        val markdownParser = MarkdownParser()
        val scope = CoroutineScope(EmptyCoroutineContext)
        scope.launch {
            markdownParser.parse(text)
        }
    }
}
