package icu.aoikajitsu.parser

import icu.aoikajitsu.*



interface BlockNodeParsingStrategy {
    val weight: Int
    fun parse(text: String): BlockNode
    fun matches(text: String): Boolean
}

interface InlineNodeParsingStrategy {
    val weight: Int
    fun parse(node: BlockNode): InlineNode
    fun matches(node: BlockNode): Boolean
}

class HeadingParsingStrategy : BlockNodeParsingStrategy {
    override val weight: Int = 1

    override fun matches(text: String): Boolean = text.startsWith("#")

    override fun parse(text: String): BlockNode {
        val level = text.takeWhile { it == '#' }.length
        val content = text.drop(level).trim()
        return HeadingNode(content,level)
    }
}

class ParagraphParsingStrategy : BlockNodeParsingStrategy {
    override val weight = 0

    override fun matches(text: String): Boolean = true

    override fun parse(text: String): BlockNode {
        return ParagraphNode(text)
    }
}

class TextParsingStrategy : InlineNodeParsingStrategy {
    override fun matches(node: BlockNode): Boolean = true

    override val weight: Int = 0

    override fun parse(node: BlockNode): InlineNode {
        if (node.content!= null) {
            return TextNode(node.content!!)
        } else {
            throw NullPointerException("Error: TextNode parse")
        }
    }
}