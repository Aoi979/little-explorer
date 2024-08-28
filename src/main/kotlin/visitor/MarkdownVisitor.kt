package icu.aoikajitsu.visitor

import icu.aoikajitsu.*

interface MarkdownVisitor {
    fun visit(node: DocumentNode)
    fun visit(node: ParagraphNode)
    fun visit(node: HeadingNode)
    fun visit(node: BlockquoteNode)
    fun visit(node: UnorderedListNode)
    fun visit(node: OrderedListNode)
    fun visit(node: ListItemNode)
    fun visit(node: CodeBlockNode)
    fun visit(node: HorizontalRuleNode)
    fun visit(node: TableNode)
    fun visit(node: TextNode)
    fun visit(node: StrongNode)
    fun visit(node: EmphasisNode)
    fun visit(node: StrikethroughNode)
    fun visit(node: CodeNode)
    fun visit(node: LinkNode)
    fun visit(node: ImageNode)
    fun visit(node: AutolinkNode)
    fun visit(node: InlineHtmlNode)
    fun visit(node: MathNode)
    fun visit(node: TaskListItemNode)
    fun visit(node: FootnoteNode)
}