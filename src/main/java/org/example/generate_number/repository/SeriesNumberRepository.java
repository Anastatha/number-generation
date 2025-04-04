package org.example.generate_number.repository;

import org.example.generate_number.entity.SeriesNumber;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class SeriesNumberRepository {
    private final JdbcClient jdbcClient;

    public SeriesNumberRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public SeriesNumber create(SeriesNumber seriesNumber) {
        String sql = """
                    INSERT INTO public.series_number(org_code, year) 
                    VALUES (:orgCode, :year) 
                    RETURNING id, org_code, year, counter;
                """;

        return jdbcClient.sql(sql)
                .param("orgCode", seriesNumber.getOrg_code())
                .param("year", seriesNumber.getYear())
                .query(SeriesNumber.class)
                .single();
    }

    public SeriesNumber insertOrReplace(SeriesNumber seriesNumber) {
        String sql = """
                    INSERT INTO public.series_number(org_code, year)
                    VALUES (:orgCode, :year)
                    ON CONFLICT (org_code, year) DO UPDATE SET counter = public.series_number.counter + 1
                    RETURNING id, org_code, year, counter;
                """;

        return jdbcClient.sql(sql)
                .param("orgCode", seriesNumber.getOrg_code())
                .param("year", seriesNumber.getYear())
                .query(SeriesNumber.class)
                .single();
    }

    public Optional<SeriesNumber> findByOrgCodeAndYear(String orgCode, String year) {
        String sql = """
                    SELECT id, org_code, year, counter 
                    FROM public.series_number 
                    WHERE org_code = :orgCode AND year = :year;
                """;

        return jdbcClient.sql(sql)
                .param("orgCode", orgCode)
                .param("year", year)
                .query(SeriesNumber.class)
                .optional();
    }

    public SeriesNumber updateCounter(Long id) {
        String sql = """
                    UPDATE series_number
                    SET counter = counter + 1
                    WHERE id = :id
                    RETURNING id, org_code, year, counter;
                """;

        return jdbcClient.sql(sql)
                .param("id", id)
                .query(SeriesNumber.class)
                .single();
    }
}