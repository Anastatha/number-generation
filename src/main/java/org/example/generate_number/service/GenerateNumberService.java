package org.example.generate_number.service;

import org.example.generate_number.dto.GenerateSeriesNumberRequest;
import org.example.generate_number.dto.GenerateSeriesNumberResponse;
import org.example.generate_number.entity.SeriesNumber;
import org.example.generate_number.repository.SeriesNumberRepositore;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;

import java.util.Optional;

@Service
public class GenerateNumberService {
    private final SeriesNumberRepositore seriesNumberRepository;

    @Value("${orgCode.okato.length}")
    private int okatoLength;

    public GenerateNumberService(SeriesNumberRepositore seriesNumberRepository) {
        this.seriesNumberRepository = seriesNumberRepository;
    }

    public GenerateSeriesNumberResponse generateNumber(GenerateSeriesNumberRequest request){
//        Optional<SeriesNumber> existingSeriesNumber = seriesNumberRepository.findByOrgCodeAndYear(request.orgCode(), request.year());
//
//        SeriesNumber seriesNumber;
//        if (existingSeriesNumber.isPresent()) {
//            seriesNumber = seriesNumberRepository.updateCounter(existingSeriesNumber.get());
//        } else {
//            seriesNumber = seriesNumberRepository.create(new SeriesNumber(request.orgCode(), request.year()));
//        }
//
//        String number = generateSeriesNumber(seriesNumber);
//        return new GenerateSeriesNumberResponse(number);

        SeriesNumber seriesNumber = seriesNumberRepository.inserOrReplace(new SeriesNumber(request.orgCode(), request.year()));

        String number = generateSeriesNumber(seriesNumber);
        return new GenerateSeriesNumberResponse(number);
    }

//public GenerateSeriesNumberResponse generateNumber(GenerateSeriesNumberRequest request) throws SQLException {
//    SeriesNumber seriesNumber = seriesNumberRepository
//            .findByOrgCodeAndYear(request.orgCode(), request.year())
//            .map(existing -> sneaky(() -> seriesNumberRepository.updateCounter(existing)))
//            .orElseGet(() -> sneaky(() -> seriesNumberRepository.create(new SeriesNumber(request.orgCode(), request.year()))));
//
//    String number = generateSeriesNumber(seriesNumber);
//    return new GenerateSeriesNumberResponse(number);
//}
//
//    private static <T> T sneaky(SneakySupplier<T> supplier) {
//        try {
//            return supplier.get();
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    @FunctionalInterface
//    private interface SneakySupplier<T> {
//        T get() throws Exception;
//    }


    private String generateSeriesNumber(SeriesNumber seriesNumber) {
        String orgCode = seriesNumber.getOrg_code();

        int startIndex = Math.max(0, orgCode.length() - okatoLength);
        String orgCodePart = orgCode.substring(startIndex);

        String counterStr = String.valueOf(seriesNumber.getCounter());
        int zeroCount = Math.max(0, 10 - orgCodePart.length() - counterStr.length());

        return orgCodePart + "0".repeat(zeroCount) + counterStr;
    }
}
