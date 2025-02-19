package org.example.generate_number.repository;

import org.example.generate_number.entity.SeriesNumber;
import org.example.generate_number.mapper.SeriesNumberMapper;
import org.example.generate_number.utils.DataSourceProxy;
import org.example.generate_number.utils.params.IntegerParam;
import org.example.generate_number.utils.params.QueryParam;
import org.example.generate_number.utils.params.StringParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Component
public class Series_numberRepositore {
    private final DataSourceProxy dataSourceProxy;

    @Autowired
    public Series_numberRepositore(DataSourceProxy dataSourceProxy) {
        this.dataSourceProxy = dataSourceProxy;
    }

    public SeriesNumber create(SeriesNumber seriesNumber) throws SQLException {
        String sql = """
        INSERT INTO public.series_number(org_code, year) 
        VALUES (?, ?) RETURNING id, org_code, year, counter;
    """;

        QueryParam[] queryParams = new QueryParam[]{
                new StringParam(seriesNumber.getOrg_code()),
                new StringParam(seriesNumber.getYear())
        };

        List<SeriesNumber> result = dataSourceProxy.executeSelect(sql, new SeriesNumberMapper(), queryParams);

        return result.isEmpty() ? null : result.get(0);
    }


    public Optional<SeriesNumber> findByOrgCodeAndYear(String orgCode, String year) throws SQLException {
        String sql = "SELECT id, org_code, year, counter FROM public.series_number WHERE org_code = ? AND year = ?";

        QueryParam[] queryParams = new QueryParam[]{
                new StringParam(orgCode),
                new StringParam(year)
        };

        List<SeriesNumber> result = dataSourceProxy.executeSelect(sql, new SeriesNumberMapper(), queryParams);

        return result.isEmpty() ? Optional.empty() : Optional.of(result.get(0));
    }

    public SeriesNumber updateCounter(SeriesNumber seriesNumber) throws SQLException {
        String sql = """
                UPDATE series_number
                SET counter = counter + 1
                WHERE id = ?
                RETURNING id, org_code, year, counter;
                """;
        List<SeriesNumber> result = dataSourceProxy.executeSelect(
                sql,
                new SeriesNumberMapper(),
                new IntegerParam(seriesNumber.getId())
        );
        return result.get(0);
    }

}
