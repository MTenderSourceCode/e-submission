package com.procurement.submission.application.params

import com.procurement.submission.domain.extension.tryParseLocalDateTime
import com.procurement.submission.domain.fail.error.DataErrors
import com.procurement.submission.domain.functional.Result
import com.procurement.submission.domain.functional.asFailure
import com.procurement.submission.domain.functional.asSuccess
import com.procurement.submission.domain.model.Cpid
import com.procurement.submission.domain.model.Ocid
import com.procurement.submission.domain.model.Owner
import com.procurement.submission.domain.model.enums.EnumElementProvider
import com.procurement.submission.domain.model.enums.EnumElementProvider.Companion.keysAsStrings
import com.procurement.submission.domain.model.enums.QualificationStatusDetails
import com.procurement.submission.domain.model.qualification.QualificationId
import com.procurement.submission.domain.model.submission.SubmissionId
import com.procurement.submission.domain.model.tryOwner
import java.time.LocalDateTime

fun parseCpid(value: String): Result<Cpid, DataErrors.Validation.DataMismatchToPattern> =
    Cpid.tryCreateOrNull(value = value)
        ?.asSuccess()
        ?: Result.failure(
            DataErrors.Validation.DataMismatchToPattern(
                name = "cpid",
                pattern = Cpid.pattern,
                actualValue = value
            )
        )

fun parseOcid(value: String): Result<Ocid, DataErrors.Validation.DataMismatchToPattern> =
    Ocid.tryCreateOrNull(value = value)
        ?.asSuccess()
        ?: Result.failure(
            DataErrors.Validation.DataMismatchToPattern(
                name = "ocid",
                pattern = Ocid.pattern,
                actualValue = value
            )
        )

fun parseQualificationStatusDetails(
    value: String, allowedEnums: List<QualificationStatusDetails>, attributeName: String
): Result<QualificationStatusDetails, DataErrors> =
    parseEnum(value = value, allowedEnums = allowedEnums, attributeName = attributeName, target = QualificationStatusDetails)

private fun <T> parseEnum(
    value: String, allowedEnums: Collection<T>, attributeName: String, target: EnumElementProvider<T>
): Result<T, DataErrors.Validation.UnknownValue> where T : Enum<T>,
                                                       T : EnumElementProvider.Key {
    val allowed = allowedEnums.toSet()
    return target.orNull(value)
        ?.takeIf { it in allowed }
        ?.asSuccess()
        ?: Result.failure(
            DataErrors.Validation.UnknownValue(
                name = attributeName,
                expectedValues = allowed.keysAsStrings(),
                actualValue = value
            )
        )
}

fun parseDate(value: String, attributeName: String): Result<LocalDateTime, DataErrors.Validation.DataFormatMismatch> =
    value.tryParseLocalDateTime()
        .doReturn { pattern ->
            return Result.failure(
                DataErrors.Validation.DataFormatMismatch(
                    name = attributeName,
                    actualValue = value,
                    expectedFormat = pattern
                )
            )
        }.asSuccess()

fun parseOwner(value: String): Result<Owner, DataErrors.Validation.DataFormatMismatch> =
    value.tryOwner()
        .doReturn {
            return Result.failure(
                DataErrors.Validation.DataFormatMismatch(
                    name = "owner",
                    actualValue = value,
                    expectedFormat = "uuid"
                )
            )
        }
        .asSuccess()

fun parseSubmissionId(
    value: String, attributeName: String
): Result<SubmissionId, DataErrors.Validation.DataMismatchToPattern> {
    val id = SubmissionId.tryCreateOrNull(value)
        ?: return DataErrors.Validation.DataMismatchToPattern(
            name = attributeName,
            pattern = SubmissionId.pattern,
            actualValue = value
        ).asFailure()

    return id.asSuccess()
}

fun parseQualificationId(
    value: String, attributeName: String
): Result<QualificationId, DataErrors.Validation.DataMismatchToPattern> {
    val id = QualificationId.tryCreateOrNull(value)
        ?: return DataErrors.Validation.DataMismatchToPattern(
            name = attributeName,
            pattern = QualificationId.pattern,
            actualValue = value
        ).asFailure()

    return id.asSuccess()
}