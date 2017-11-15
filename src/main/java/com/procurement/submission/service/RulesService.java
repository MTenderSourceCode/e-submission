package com.procurement.submission.service;

import com.procurement.submission.model.dto.request.PeriodDataDto;
import com.procurement.submission.model.entity.SubmissionPeriodEntity;
import org.springframework.stereotype.Service;

@Service
public interface RulesService {

    Long getInterval(PeriodDataDto data);
}