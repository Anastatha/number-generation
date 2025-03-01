package org.example.generate_number.service;

import org.example.generate_number.dto.GenerateSeriesNumberRequest;
import org.example.generate_number.dto.GenerateSeriesNumberResponse;
import org.example.generate_number.entity.SeriesNumber;
import org.example.generate_number.repository.SeriesNumberRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;

@Service
public class GenerateNumberService {
    private final SeriesNumberRepository seriesNumberRepository;

    @Value("${orgCode.okato.length}")
    private int okatoLength;

    public GenerateNumberService(SeriesNumberRepository seriesNumberRepository) {
        this.seriesNumberRepository = seriesNumberRepository;
    }

    public GenerateSeriesNumberResponse generateNumber(GenerateSeriesNumberRequest request){
        SeriesNumber seriesNumber = seriesNumberRepository.insertOrReplace(new SeriesNumber(request.orgCode(), request.year()));

        String number = generateSeriesNumber(seriesNumber);
        return new GenerateSeriesNumberResponse(number);
    }

    private String generateSeriesNumber(SeriesNumber seriesNumber) {
        String orgCode = seriesNumber.getOrg_code();

        int startIndex = Math.max(0, orgCode.length() - okatoLength);
        String orgCodePart = orgCode.substring(startIndex);

        String counterStr = String.valueOf(seriesNumber.getCounter());
        int zeroCount = Math.max(0, 10 - orgCodePart.length() - counterStr.length());

        return orgCodePart + "0".repeat(zeroCount) + counterStr;
    }
}
