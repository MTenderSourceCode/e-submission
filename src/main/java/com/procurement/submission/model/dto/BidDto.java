package com.procurement.submission.model.dto;

import com.procurement.submission.model.dto.response.RequirementResponseDto;
import java.util.List;
import javax.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class BidDto {
    @NotNull
    private String id;

    private String date;

    private String status;

    private List<OrganizationReferenceDto> tenderers;

    private List<DocumentDto> documents;

    private List<String> relatedLots;


//    private ValueDto value;
//    private List<RequirementResponseDto> requirementResponses;

    @Getter
    public static class OrganizationReferenceDto {
        private String name;
        private Integer id;
        private IdentifierDto identifier;
        private AddressDto address;
        private List<IdentifierDto> additionalIdentifiers;
        private ContactPointDto contactPoint;

        public class IdentifierDto {
            private String scheme;
            private String id;
            private String legalName;
            private String uri;
        }

        public class AddressDto {
            private String streetAddress;
            private String locality;
            private String region;
            private String postalCode;
            private String countryName;
        }

        public class ContactPointDto {
            private String name;
            private String email;
            private String telephone;
            private String faxNumber;
            private String url;
            private List<String> languages;
        }
    }

    public class DocumentDto {
        private String id;
        private String documentType;
        private String title;
        private String description;
        private String url;
        private String datePublished;
        private String dateModified;
        private String format;
        private String language;
        private List<String> relatedLots;
    }
}