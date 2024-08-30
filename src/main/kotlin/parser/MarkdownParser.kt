package icu.aoikajitsu.parser

import icu.aoikajitsu.BlockNode
import icu.aoikajitsu.DocumentNode
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import java.util.PriorityQueue

class MarkdownParser {
    private val blockStrategies: PriorityQueue<BlockNodeParsingStrategy> = PriorityQueue(compareBy { it.weight })
    private val inlineStrategies: PriorityQueue<InlineNodeParsingStrategy> = PriorityQueue(compareBy { it.weight })
    init {
        inlineStrategies.offer(TextParsingStrategy())
        blockStrategies.offer(HeadingParsingStrategy())
        blockStrategies.offer(ParagraphParsingStrategy())
    }
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

    private class State(
        var insideCodeBlock: Boolean = false,
        var insideList: Boolean = false,
        var insideBlockquote: Boolean = false,
    )

    private fun splitIntoParagraphs(lines: List<String>): List<String> {
        val paragraphs = mutableListOf<String>()
        val currentParagraph = StringBuilder()
        val state = State()


        for (line in lines) {
            when {
                line.startsWith("```") -> {
                    state.insideCodeBlock = !state.insideCodeBlock
                    currentParagraph.append(line).append("\n")
                }

                line.startsWith("- ") || line.startsWith("* ") || line.startsWith("+ ") -> {
                    if (!state.insideList) {
                        if (currentParagraph.isNotEmpty()) {
                            paragraphs.add(currentParagraph.toString().trim())
                            currentParagraph.setLength(0)
                        }
                        state.insideList = true
                    }
                    currentParagraph.append(line).append("\n")
                }

                line.startsWith("> ") -> {
                    if (!state.insideBlockquote) {
                        if (currentParagraph.isNotEmpty()) {
                            paragraphs.add(currentParagraph.toString().trim())
                            currentParagraph.setLength(0)
                        }
                        state.insideBlockquote = true
                    }
                    currentParagraph.append(line).append("\n")
                }

                line.isBlank() -> {
                    if (state.insideCodeBlock || state.insideList || state.insideBlockquote) {
                        currentParagraph.append(line).append("\n")
                    } else {
                        if (currentParagraph.isNotEmpty()) {
                            paragraphs.add(currentParagraph.toString().trim())
                            currentParagraph.setLength(0)
                        }
                    }
                }

                else -> {
                    if (state.insideList || state.insideBlockquote) {
                        state.insideList = false
                        state.insideBlockquote = false
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