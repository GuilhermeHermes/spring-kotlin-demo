package com.guilhermehermes.demo

import java.util.*


fun String.toSlug(): String = lowercase(Locale.getDefault())
    .replace("/n", " ")
    .replace("[^a-z\\d\\s]".toRegex(), "")
    .split(" ")
    .joinToString("-")
    .replace("-+".toRegex(), "-")