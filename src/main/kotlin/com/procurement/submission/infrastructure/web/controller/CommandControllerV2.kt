package com.procurement.submission.infrastructure.web.controller

import com.procurement.submission.application.service.Logger
import com.procurement.submission.application.service.Transform
import com.procurement.submission.domain.fail.Fail
import com.procurement.submission.infrastructure.api.ApiVersion
import com.procurement.submission.infrastructure.api.CommandId
import com.procurement.submission.infrastructure.api.tryGetId
import com.procurement.submission.infrastructure.api.tryGetNode
import com.procurement.submission.infrastructure.api.tryGetVersion
import com.procurement.submission.infrastructure.api.v2.ApiResponseV2
import com.procurement.submission.infrastructure.api.v2.ApiResponseV2Generator.generateResponseOnFailure
import com.procurement.submission.infrastructure.service.CommandServiceV2
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/command2")
class CommandControllerV2(
    private val command2Service: CommandServiceV2,
    private val transform: Transform,
    private val logger: Logger
) {

    @PostMapping
    fun command(@RequestBody requestBody: String): ResponseEntity<ApiResponseV2> {
        if (logger.isDebugEnabled)
            logger.debug("RECEIVED COMMAND: '$requestBody'.")

        val node = requestBody.tryGetNode(transform)
            .onFailure {
                return generateResponseEntityOnFailure(fail = it.reason, version = ApiVersion.NaN, id = CommandId.NaN)
            }

        val version = node.tryGetVersion()
            .onFailure {
                val id = node.tryGetId().getOrElse(CommandId.NaN)
                return generateResponseEntityOnFailure(fail = it.reason, version = ApiVersion.NaN, id = id)
            }

        val id = node.tryGetId()
            .onFailure {
                return generateResponseEntityOnFailure(fail = it.reason, version = version, id = CommandId.NaN)
            }

        val response =
            command2Service.execute(node)
                .also { response ->
                    if (logger.isDebugEnabled)
                        logger.debug("RESPONSE (id: '${id}'): '${transform.trySerialization(response)}'.")
                }

        return ResponseEntity(response, HttpStatus.OK)
    }

    private fun generateResponseEntityOnFailure(
        fail: Fail,
        version: ApiVersion,
        id: CommandId
    ): ResponseEntity<ApiResponseV2> {
        val response =
            generateResponseOnFailure(fail = fail, id = id, version = version, logger = logger)
        return ResponseEntity(response, HttpStatus.OK)
    }
}
