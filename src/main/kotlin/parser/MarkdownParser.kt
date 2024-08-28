package icu.aoikajitsu.parser

import icu.aoikajitsu.BlockNode
import icu.aoikajitsu.DocumentNode
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*

class MarkdownParser {
    private val blockStrategies = listOf(HeadingParsingStrategy(), ParagraphParsingStrategy())
    private val inlineStrategies: List<InlineNodeParsingStrategy> = listOf(TextParsingStrategy())

    @OptIn(ExperimentalCoroutinesApi::class)
    suspend fun parse(markdown: String): DocumentNode {

        val documentNode = DocumentNode()

        val lines = markdown.lines()

        val paragraphs = splitIntoParagraphs(lines)

        for (paragraph in paragraphs) {

            lateinit var blockNode: BlockNode

            parseParagraphFlow(paragraph).map {
                it.parse(paragraph)
            }.flatMapConcat {
                blockNode = it
                selectInlineStrategy(it).asFlow()
            }.map {
                it.parse(blockNode)
            }.collect {
                blockNode.children.add(it)
            }
            documentNode.children.add(blockNode)
        }

        return documentNode
    }


    private fun parseParagraphFlow(paragraph: String) = flow {
        emit(selectBlockStrategy(paragraph))
    }

    private fun selectBlockStrategy(text: String): BlockNodeParsingStrategy {
        return blockStrategies.first { it.matches(text) }
    }

    private fun selectInlineStrategy(blockNode: BlockNode): List<InlineNodeParsingStrategy> {
        return inlineStrategies.filter {
            it.matches(blockNode)
        }
    }

    private fun splitIntoParagraphs(lines: List<String>): List<String> {
        val paragraphs = mutableListOf<String>()
        val currentParagraph = StringBuilder()
        var insideCodeBlock = false
        var insideList = false
        var insideBlockquote = false

        for (line in lines) {
            when {
                line.startsWith("```") -> {
                    insideCodeBlock = !insideCodeBlock
                    currentParagraph.append(line).append("\n")
                }

                line.startsWith("- ") || line.startsWith("* ") || line.startsWith("+ ") -> {
                    if (!insideList) {
                        if (currentParagraph.isNotEmpty()) {
                            paragraphs.add(currentParagraph.toString().trim())
                            currentParagraph.setLength(0)
                        }
                        insideList = true
                    }
                    currentParagraph.append(line).append("\n")
                }

                line.startsWith("> ") -> {
                    if (!insideBlockquote) {
                        if (currentParagraph.isNotEmpty()) {
                            paragraphs.add(currentParagraph.toString().trim())
                            currentParagraph.setLength(0)
                        }
                        insideBlockquote = true
                    }
                    currentParagraph.append(line).append("\n")
                }

                line.isBlank() -> {
                    if (insideCodeBlock || insideList || insideBlockquote) {
                        currentParagraph.append(line).append("\n")
                    } else {
                        if (currentParagraph.isNotEmpty()) {
                            paragraphs.add(currentParagraph.toString().trim())
                            currentParagraph.setLength(0)
                        }
                    }
                }

                else -> {
                    if (insideList || insideBlockquote) {
                        insideList = false
                        insideBlockquote = false
                    }
                    currentParagraph.append(line).append("\n")
                }
            }
        }

        if (currentParagraph.isNotEmpty()) {
            paragraphs.add(currentParagraph.toString().trim())
        }

        return paragraphs
    }

}