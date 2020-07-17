package com.procurement.submission.application.service

import com.procurement.submission.application.params.CheckAbsenceActiveInvitationsParams
import com.procurement.submission.application.params.DoInvitationsParams
import com.procurement.submission.domain.fail.Fail
import com.procurement.submission.domain.functional.Result
import com.procurement.submission.domain.functional.ValidationResult
import com.procurement.submission.infrastructure.dto.invitation.create.DoInvitationsResult

interface InvitationService {

    fun doInvitations(params: DoInvitationsParams): Result<DoInvitationsResult?, Fail>

    fun checkAbsenceActiveInvitations(params: CheckAbsenceActiveInvitationsParams): ValidationResult<Fail>
}