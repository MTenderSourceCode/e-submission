package com.procurement.submission.model.dto.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.procurement.submission.model.ocds.Value;
import java.time.LocalDateTime;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonPropertyOrder({
    "ocid",
    "bid"
})
public class BidsSelectionResponse {

    @NotNull
    @JsonProperty("ocid")
    private String ocid;

    @Valid
    @NotEmpty
    @JsonProperty("bids")
    private List<Bid> bids;

    @JsonCreator
    public BidsSelectionResponse(@JsonProperty("ocid") @NotNull final String ocid,
                                 @JsonProperty("bids") @Valid @NotNull final List<Bid> bids) {
        this.ocid = ocid;
        this.bids = bids;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @JsonPropertyOrder({
        "id",
        "relatedLots",
        "createDate",
        "pendingDate",
        "value",
        "tenderers"
    })
    public static class Bid {
        private String id;
        private List<String> relatedLots;
        private LocalDateTime createDate;
        private LocalDateTime pendingDate;
        private Value value;
        private List<OrganizationReferenceRs> tenderers;

        @JsonCreator
        public Bid(@JsonProperty("id") @NotNull final String id,
                   @JsonProperty("relatedLots") @NotNull final List<String> relatedLots,
                   @JsonProperty("createDate") @NotNull final LocalDateTime createDate,
                   @JsonProperty("pendingDate") @NotNull final LocalDateTime pendingDate,
                   @JsonProperty("value") @Valid final Value value,
                   @JsonProperty("tenderers") @NotEmpty @Valid final List<OrganizationReferenceRs> tenderers) {
            this.id = id;
            this.relatedLots = relatedLots;
            this.createDate = createDate;
            this.pendingDate = pendingDate;
            this.value = value;
            this.tenderers = tenderers;
        }
    }

/*    @Getter
    @JsonPropertyOrder({
        "id",
        "name"
    })
    public static class OrganizationReference {
        private String id;
        private String name;

        @JsonCreator
        public OrganizationReference(@JsonProperty("id") @NotNull final String id,
                                     @JsonProperty("name") @NotNull final String name) {
            this.id = id;
            this.name = name;
        }
    }*/
}
