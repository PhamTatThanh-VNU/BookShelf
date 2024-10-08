package com.example.bookshelf.ui.utils

// Acknowledge: Google
fun String.removeHtmlTags(): String {
    return this.replace(Regex("<.*?>"), "")
        .replace("&nbsp;", " ")
        .replace("&amp;", "&")
        .replace("&lt;", "<")
        .replace("&gt;", ">")
        .replace("&quot;", "\"")
        .replace("&#39;", "'")
}
