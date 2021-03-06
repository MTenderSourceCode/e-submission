package com.procurement.submission.domain.fail.error

import com.procurement.submission.domain.fail.Fail
import com.procurement.submission.domain.model.Cpid
import com.procurement.submission.domain.model.Ocid
import com.procurement.submission.domain.model.bid.BidId
import com.procurement.submission.domain.model.enums.InvitationStatus
import com.procurement.submission.domain.model.enums.OperationType
import com.procurement.submission.domain.model.enums.ProcurementMethod
import com.procurement.submission.domain.model.invitation.InvitationId
import com.procurement.submission.domain.model.item.ItemId
import com.procurement.submission.domain.model.submission.SubmissionId
import java.time.Duration

sealed class ValidationError(
    numberError: String,
    override val description: String,
    val entityId: String? = null
) : Fail.Error("VR.COM-") {
    override val code: String = prefix + numberError

    class MissingSubmission(
        submissionIds: Collection<SubmissionId>
    ) : ValidationError(
        numberError = "13.1.2",
        description = "Missing submission(s) by id(s) '${submissionIds.joinToString()}'."
    )

    class ActiveInvitationsFound(
        invitations: Collection<InvitationId>
    ) : ValidationError(
        numberError = "13.2.1",
        description = "Active invitations was found: invitation(s) by id(s): '${invitations.joinToString()}'."
    )

    sealed class EntityNotFound(description: String) : ValidationError("17", description) {

        class TenderPeriodRule(
            country: String,
            pmd: ProcurementMethod,
            parameter: String,
            operationType: OperationType
        ) : EntityNotFound("Tender period rule '$parameter' not found by country '$country', pmd '${pmd.name}', operationType '$operationType'.")

        class ReturnInvitationsRule(
            country: String,
            pmd: ProcurementMethod,
            parameter: String,
            operationType: OperationType?
        ) : EntityNotFound("Invitations rule '$parameter' not found by country '$country', pmd '${pmd.name}', operationType '$operationType'.")
    }

    class TenderPeriodDurationError(expectedDuration: Duration): ValidationError(
        numberError = "13.4.2",
        description = "Actual tender period duration is less than '${expectedDuration.toDays()}' days."
    )

    class PendingInvitationsNotFoundOnPublishInvitations(cpid: Cpid) :
        ValidationError(
            numberError = "13.3.1",
            description = "Invitations in status '${InvitationStatus.PENDING}' was not found by cpid = '$cpid'"
        )

    class TenderPeriodNotFound(cpid: Cpid, ocid: Ocid) :
        ValidationError(
            numberError = "13.6.1",
            description = "Tender period by cpid '$cpid' and ocid '$ocid' not found."
        )

    class ReceivedDatePrecedesStoredStartDate :
        ValidationError(
            numberError = "13.6.2",
            description = "Received date must be after stored start date."
        )

    class ReceivedDateIsAfterStoredEndDate :
        ValidationError(
            numberError = "13.6.3",
            description = "Received date must precede stored end date."
        )

    class MissingBidValue(bidId: BidId) :
        ValidationError(
            numberError = "13.7.1",
            description = "Value of bid details '$bidId' is missing.",
            entityId = bidId.toString()
        )

    class InvalidBidAmount(bidId: BidId) :
        ValidationError(
            numberError = "13.7.2",
            description = "Value of bid '$bidId' must contain amount greater than zero.",
            entityId = bidId.toString()
        )

    class InvalidBidCurrency(bidId: BidId) :
        ValidationError(
            numberError = "13.7.3",
            description = "Bid '$bidId' currency must be identical to tender currency.",
            entityId = bidId.toString()
        )

    class DuplicateTenderers(tendererId: String) :
        ValidationError(
            numberError = "13.7.4",
            description = "Tenderer '$tendererId' contains duplicate.",
            entityId = tendererId
        )

    class DuplicatePersonBusinessFunctions(personId: String, businessFunctionId: String) :
        ValidationError(
            numberError = "13.7.5",
            description = "Person '$personId' contains duplicate business function '$businessFunctionId'."
        )

    class DuplicatePersonDocuments(personId: String, documentId: String) :
        ValidationError(
            numberError = "13.7.6",
            description = "Person '$personId' contains duplicate business function document '$documentId'."
        )

    class DuplicateDocuments(bidId: BidId, documentId: String) :
        ValidationError(
            numberError = "13.7.7",
            description = "Bid '$bidId' contains duplicate document '$documentId'."
        )

    class InvalidRelatedLots :
        ValidationError(
            numberError = "13.7.8",
            description = "Related lots in bids' documents must equal related lots in bids."
        )

    class MissingItems :
        ValidationError(
            numberError = "13.7.9",
            description = "Bid items are missing."
        )

    class DuplicateItems(bidId: BidId, intemId: ItemId) :
        ValidationError(
            numberError = "13.7.10",
            description = "Bid '$bidId' contains duplicate item '$intemId'."
        )

    class InvalidItems :
        ValidationError(
            numberError = "13.7.11",
            description = "Bid items must equal tender items."
        )

    class InvalidItemAmount(itemId: ItemId) :
        ValidationError(
            numberError = "13.7.12",
            description = "Value of item '$itemId' must contain amount greater than zero.",
            entityId = itemId.toString()
        )

    class InvalidItemCurrency(itemId: ItemId) :
        ValidationError(
            numberError = "13.7.13",
            description = "Item '$itemId' currency must be identical to tender currency.",
            entityId = itemId.toString()
        )

    class InvalidUnits :
        ValidationError(
            numberError = "13.7.14",
            description = "Bid unit ids must equal tender unit ids."
        )

    class ActiveInvitationNotFound :
        ValidationError(
            numberError = "13.7.15",
            description = "Active invitation by received group of tenderers not found."
        )
}