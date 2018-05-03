package com.procurement.submission.service;

import com.procurement.submission.model.dto.bpe.ResponseDto;
import com.procurement.submission.model.dto.request.AwardStatusDetails;
import com.procurement.submission.model.dto.request.LotsDto;
import com.procurement.submission.model.dto.request.UnsuccessfulLotsDto;
import com.procurement.submission.model.ocds.Bid;
import java.time.LocalDateTime;

public interface BidService {

    ResponseDto createBid(String cpId,
                          String stage,
                          String owner,
                          Bid bidDto);

    ResponseDto updateBid(String cpId,
                          String stage,
                          String token,
                          String owner,
                          Bid bidDto);

    ResponseDto copyBids(String cpId,
                         String newStage,
                         String previousStage,
                         LocalDateTime startDate,
                         LocalDateTime endDate,
                         LotsDto lotsDto);

    ResponseDto getPendingBids(String cpId,
                               String stage,
                               String country,
                               String pmd);

    ResponseDto updateStatus(String cpId,
                             String stage,
                             String country,
                             String pmd,
                             UnsuccessfulLotsDto unsuccessfulLots);

    ResponseDto updateStatusDetails(String cpId,
                                    String stage,
                                    String bidId,
                                    AwardStatusDetails awardStatusDetails);

    ResponseDto setFinalStatuses(String cpId, String stage, LocalDateTime dateTime);
}
