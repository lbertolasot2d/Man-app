package com.example.man_app.util

import kotlin.js.ExperimentalWasmJsInterop

@OptIn(ExperimentalWasmJsInterop::class)
@JsFun("() => crypto.randomUUID()")
external fun jsRandomUUID(): String

actual fun randomUUID(): String = jsRandomUUID()
