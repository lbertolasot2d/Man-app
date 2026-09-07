package com.example.man_app.util

import java.util.UUID

actual fun randomUUID(): String = UUID.randomUUID().toString()
