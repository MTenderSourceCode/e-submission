package com.procurement.submission.application.service

import com.procurement.submission.application.exception.ErrorException
import com.procurement.submission.application.exception.ErrorType
import com.procurement.submission.domain.extension.tryToLong
import com.procurement.submission.domain.fail.Fail
import com.procurement.submission.domain.fail.error.ValidationError
import com.procurement.submission.domain.functional.Result
import com.procurement.submission.domain.functional.asFailure
import com.procurement.submission.domain.functional.asSuccess
import com.procurement.submission.domain.model.enums.OperationType
import com.procurement.submission.domain.model.enums.ProcurementMethod
import com.procurement.submission.infrastructure.dao.RulesDao
import org.springframework.stereotype.Service
import java.time.Duration

@Service
class RulesService(private val rulesDao: RulesDao) {

    fun getInterval(country: String, pmd: String): Long {
        return rulesDao.getValue(
            country, pmd,
            PARAMETER_INTERVAL
        )?.toLongOrNull()
            ?: throw ErrorException(ErrorType.INTERVAL_RULES_NOT_FOUND)
    }

    fun getUnsuspendInterval(country: String, pmd: String): Long {
        return rulesDao.getValue(
            country, pmd,
            PARAMETER_UNSUSPEND_INTERVAL
        )?.toLongOrNull()
            ?: throw ErrorException(ErrorType.INTERVAL_RULES_NOT_FOUND)
    }

    fun getIntervalBefore(country: String, pmd: String): Long {
        return rulesDao.getValue(
            country, pmd,
            PARAMETER_INTERVAL_BEFORE
        )?.toLongOrNull()
            ?: throw ErrorException(ErrorType.INTERVAL_RULES_NOT_FOUND)
    }

    fun getRulesMinBids(country: String, pmd: String): Int {
        return rulesDao.getValue(
            country, pmd,
            PARAMETER_MIN_BIDS
        )?.toIntOrNull()
            ?: throw ErrorException(ErrorType.BIDS_RULES_NOT_FOUND)
    }

    fun getTenderPeriodMinimumDuration(
        country: String,
        pmd: ProcurementMethod,
        operationType: OperationType
    ): Result<Duration, Fail> {
        val value = rulesDao
            .tryGetValue(country, pmd, MINIMUM_PERIOD_DURATION_PARAMETER, operationType)
            .orForwardFail { fail -> return fail }
            ?: return ValidationError.EntityNotFound.TenderPeriodRule(
                country = country,
                pmd = pmd,
                parameter = MINIMUM_PERIOD_DURATION_PARAMETER,
                operationType = operationType
            ).asFailure()

        return value
            .tryToLong()
            .doReturn { incident ->
                return Fail.Incident.Database.Parsing(VALUE_COLUMN, value, incident.exception)
                    .asFailure()
            }
            .let { Duration.ofSeconds(it) }
            .asSuccess()
    }

    companion object {
        private const val PARAMETER_MIN_BIDS = "minBids"
        private const val PARAMETER_INTERVAL = "interval"
        private const val PARAMETER_UNSUSPEND_INTERVAL = "unsuspend_interval"
        private const val PARAMETER_INTERVAL_BEFORE = "interval_before"
        private const val MINIMUM_PERIOD_DURATION_PARAMETER = "minTenderPeriodDuration"

        private const val VALUE_COLUMN = "value"
    }
}