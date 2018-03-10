package com.procurement.submission.model.dto.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import javax.validation.constraints.NotEmpty;
import lombok.Getter;

@Getter
public class UnsuccessfulLotsDto {
    @JsonProperty("unsuccessfulLots")
    private List<LotDto> lots;

    @JsonCreator
    public UnsuccessfulLotsDto(@JsonProperty("unsuccessfulLots") @NotEmpty final List<LotDto> lots) {
        this.lots = lots;
    }
}
