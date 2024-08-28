package icu.aoikajitsu


sealed class MarkdownNode {
    abstract val type: NodeType
    open var children: MutableList<MarkdownNode> = mutableListOf()
}

abstract class InlineNode: MarkdownNode()

abstract class BlockNode: MarkdownNode(){
    abstract val content: String
}

enum class NodeType {
    DOCUMENT,
    PARAGRAPH,
    HEADING,
    BLOCKQUOTE,
    UNORDERED_LIST,
    ORDERED_LIST,
    LIST_ITEM,
    CODE_BLOCK,
    HORIZONTAL_RULE,
    TABLE,
    TASK_LIST,
    FOOTNOTE,
    TEXT,
    STRONG,
    EMPHASIS,
    STRIKETHROUGH,
    CODE,
    LINK,
    IMAGE,
    AUTOLINK,
    INLINE_HTML,
    MATH
}

//Block-level Nodes
// 文档节点，包含所有其他节点
class DocumentNode() : MarkdownNode() {
    override val type = NodeType.DOCUMENT
}

// 段落节点，包含文本或其他内联节点
class ParagraphNode(override val content: String) : BlockNode() {
    override val type = NodeType.PARAGRAPH
}

// 标题节点，包含标题级别（如 H1, H2 等）
class HeadingNode(override val content: String, val level :Int) : BlockNode() {
    override val type = NodeType.HEADING
}

// 引用块节点，包含引用的内容
class BlockquoteNode(override val content: String) : BlockNode() {
    override val type = NodeType.BLOCKQUOTE
}

// 无序列表节点，包含列表项
class UnorderedListNode(override val content: String) : BlockNode() {
    override val type = NodeType.UNORDERED_LIST
}

// 有序列表节点，包含列表项
class OrderedListNode(override val content: String) : BlockNode() {
    override val type = NodeType.ORDERED_LIST
}

// 列表项节点，属于列表
class ListItemNode(override val content: String) : BlockNode() {
    override val type = NodeType.LIST_ITEM
}

// 代码块节点，包含多行代码
class CodeBlockNode(val language: String?, override val content: String) : BlockNode() {
    override val type = NodeType.CODE_BLOCK
}

// 水平分隔线节点
class HorizontalRuleNode(override val content: String) : BlockNode() {
    override val type = NodeType.HORIZONTAL_RULE
}

// 表格节点，包含表格的行和列
class TableNode(override val content: String) : BlockNode() {
    override val type = NodeType.TABLE
}


//Inline Nodes

// 文本节点，表示纯文本
class TextNode(val content: String) : InlineNode() {
    override val type = NodeType.TEXT
}

// 加粗文本节点
class StrongNode : InlineNode() {
    override val type = NodeType.STRONG
}

// 斜体文本节点
class EmphasisNode : InlineNode() {
    override val type = NodeType.EMPHASIS
}

// 删除线文本节点
class StrikethroughNode : InlineNode() {
    override val type = NodeType.STRIKETHROUGH
}

// 内联代码节点
class CodeNode(val code: String) : InlineNode() {
    override val type = NodeType.CODE
}

// 链接节点，包含链接地址和可选的标题
class LinkNode(val url: String, val title: String?) : InlineNode() {
    override val type = NodeType.LINK
}

// 图片节点，包含图片地址和可选的替代文本
class ImageNode(val url: String, val altText: String?) : InlineNode() {
    override val type = NodeType.IMAGE
}

// 自动链接节点
class AutolinkNode(val url: String) : InlineNode() {
    override val type = NodeType.AUTOLINK
}

// 内联 HTML 节点
class InlineHtmlNode(val html: String) : InlineNode() {
    override val type = NodeType.INLINE_HTML
}

// 数学公式节点
class MathNode(val formula: String) : InlineNode() {
    override val type = NodeType.MATH
}




//Extended Syntax Nodes

// 任务列表项节点
class TaskListItemNode(val isChecked: Boolean) : MarkdownNode() {
    override val type = NodeType.TASK_LIST
}

// 脚注节点
class FootnoteNode(val identifier: String) : MarkdownNode() {
    override val type = NodeType.FOOTNOTE
}


