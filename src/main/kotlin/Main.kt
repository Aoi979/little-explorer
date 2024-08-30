package icu.aoikajitsu

import icu.aoikajitsu.parser.MarkdownParser
import icu.aoikajitsu.visitor.MarkdownVisitor

suspend fun visitMarkdownText(markdownNode: String, visitor: MarkdownVisitor){
    val markdownParser = MarkdownParser()
    val node = markdownParser.parse(markdownNode)
    node.accept(visitor)
}





suspend fun main() {
    val visitor = object: MarkdownVisitor {
        override fun visit(node: DocumentNode) {
            TODO("Not yet implemented")
        }

        override fun visit(node: ParagraphNode) {
            TODO("Not yet implemented")
        }

        override fun visit(node: HeadingNode) {
            TODO("Not yet implemented")
        }

        override fun visit(node: BlockquoteNode) {
            TODO("Not yet implemented")
        }

        override fun visit(node: UnorderedListNode) {
            TODO("Not yet implemented")
        }

        override fun visit(node: OrderedListNode) {
            TODO("Not yet implemented")
        }

        override fun visit(node: ListItemNode) {
            TODO("Not yet implemented")
        }

        override fun visit(node: CodeBlockNode) {
            TODO("Not yet implemented")
        }

        override fun visit(node: HorizontalRuleNode) {
            TODO("Not yet implemented")
        }

        override fun visit(node: TableNode) {
            TODO("Not yet implemented")
        }

        override fun visit(node: TextNode) {
            TODO("Not yet implemented")
        }

        override fun visit(node: StrongNode) {
            TODO("Not yet implemented")
        }

        override fun visit(node: EmphasisNode) {
            TODO("Not yet implemented")
        }

        override fun visit(node: StrikethroughNode) {
            TODO("Not yet implemented")
        }

        override fun visit(node: CodeNode) {
            TODO("Not yet implemented")
        }

        override fun visit(node: LinkNode) {
            TODO("Not yet implemented")
        }

        override fun visit(node: ImageNode) {
            TODO("Not yet implemented")
        }

        override fun visit(node: AutolinkNode) {
            TODO("Not yet implemented")
        }

        override fun visit(node: InlineHtmlNode) {
            TODO("Not yet implemented")
        }

        override fun visit(node: MathNode) {
            TODO("Not yet implemented")
        }

        override fun visit(node: TaskListItemNode) {
            TODO("Not yet implemented")
        }

        override fun visit(node: FootnoteNode) {
            TODO("Not yet implemented")
        }

    }
    val markdownText: String = "#hello world\n"
    visitMarkdownText(markdownText,visitor)
}