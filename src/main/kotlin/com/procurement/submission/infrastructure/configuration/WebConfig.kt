package com.procurement.submission.infrastructure.configuration

import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import springfox.documentation.swagger2.annotations.EnableSwagger2

@Configuration
@EnableSwagger2
@ComponentScan(basePackages = arrayOf("com.procurement.submission.infrastructure.web.controller"))
class WebConfig
