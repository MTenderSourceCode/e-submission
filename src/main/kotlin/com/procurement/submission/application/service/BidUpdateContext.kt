package com.procurement.submission.application.service

import java.time.LocalDateTime
import java.util.*

data class BidUpdateContext(
    val id: String,
    val cpid: String,
    val owner: String,
    val stage: String,
    val token: UUID,
    val startDate: LocalDateTime
)