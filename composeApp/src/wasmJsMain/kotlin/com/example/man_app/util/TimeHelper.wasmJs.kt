package com.example.man_app.util

import kotlin.js.ExperimentalWasmJsInterop

@OptIn(ExperimentalWasmJsInterop::class)
@JsFun("() => Date.now()")
external fun dateNow(): Double

actual fun getNowMillis(): Long = dateNow().toLong()
