package icu.aoikajitsu.parser

import icu.aoikajitsu.*



interface BlockNodeParsingStrategy {
    fun parse(text: String): BlockNode
    fun matches(text: String): Boolean
}

interface InlineNodeParsingStrategy {
    fun parse(node: BlockNode): InlineNode
    fun matches(node: BlockNode): Boolean
}

class HeadingParsingStrategy : BlockNodeParsingStrategy {
    override fun matches(text: String): Boolean = text.startsWith("#")

    override fun parse(text: String): BlockNode {
        val level = text.takeWhile { it == '#' }.length
        val content = text.drop(level).trim()
        return HeadingNode(content,level)
    }
}

class ParagraphParsingStrategy : BlockNodeParsingStrategy {
    override fun matches(text: String): Boolean = true
    override fun parse(text: String): BlockNode {
        return ParagraphNode(text)
    }
}

class TextParsingStrategy : InlineNodeParsingStrategy {
    override fun matches(node: BlockNode): Boolean = true

    override fun parse(node: BlockNode): InlineNode {
        return TextNode(node.content)
    }
}