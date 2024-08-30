package icu.aoikajitsu

fun document(block: DocumentNode.()->Unit){
    val documentNode = DocumentNode()
    documentNode.block()
}

fun DocumentNode.heading(level: Int, block:BlockNode.()->Unit) {
    val headingNode = HeadingNode(level = level)
    headingNode.block()
    children.add(headingNode)
}

fun BlockNode.text(text: String){
    children.add(TextNode(text))
}


