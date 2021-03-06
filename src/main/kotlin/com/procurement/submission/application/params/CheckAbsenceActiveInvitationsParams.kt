package com.procurement.submission.application.params

import com.procurement.submission.domain.fail.error.DataErrors
import com.procurement.submission.domain.model.Cpid
import com.procurement.submission.lib.functional.Result
import com.procurement.submission.lib.functional.asSuccess

class CheckAbsenceActiveInvitationsParams private constructor(val cpid: Cpid) {

    companion object {
        fun tryCreate(cpid: String): Result<CheckAbsenceActiveInvitationsParams, DataErrors> {
            val cpidParsed = parseCpid(value = cpid)
                .onFailure { return it }

            return CheckAbsenceActiveInvitationsParams(cpidParsed)
                .asSuccess()
        }
    }
}