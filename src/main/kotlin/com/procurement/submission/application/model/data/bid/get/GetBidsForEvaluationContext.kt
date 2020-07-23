package com.procurement.submission.application.model.data.bid.get

import com.procurement.submission.domain.model.enums.ProcurementMethod

data class GetBidsForEvaluationContext(
    val cpid: String,
    val stage: String,
    val pmd: ProcurementMethod,
    val country: String
)