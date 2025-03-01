package org.example.generate_number.repository;

import org.example.generate_number.entity.SeriesNumber;
import org.example.generate_number.mapper.SeriesNumberMapper;
import org.example.generate_number.utils.DataSourceProxy;
import org.example.generate_number.utils.params.IntegerParam;
import org.example.generate_number.utils.params.QueryParam;
import org.example.generate_number.utils.params.StringParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class SeriesNumberRepositore {
    private final DataSourceProxy dataSourceProxy;

    @Autowired
    public SeriesNumberRepositore(DataSourceProxy dataSourceProxy) {
        this.dataSourceProxy = dataSourceProxy;
    }

    public SeriesNumber create(SeriesNumber seriesNumber) {
        String sql = """
        INSERT INTO public.series_number(org_code, year) 
        VALUES (?, ?) 
        RETURNING id, org_code, year, counter;
    """;

        QueryParam[] queryParams = new QueryParam[]{
                new StringParam(seriesNumber.getOrg_code()),
                new StringParam(seriesNumber.getYear())
        };

        List<SeriesNumber> result = dataSourceProxy.executeSelect(sql, new SeriesNumberMapper(), queryParams);

        return result.isEmpty() ? null : result.getFirst();
    }

    public  SeriesNumber inserOrReplace( SeriesNumber seriesNumber) {
        String sql = """
                INSERT INTO public.series_number(org_code, year)
                VALUES (?, ?)
                ON CONFLICT (org_code, year) DO UPDATE SET counter = public.series_number.counter + 1
                RETURNING id, org_code, year, counter;
                """;

//        ALTER TABLE series_number ADD CONSTRAINT UQ_org_code_year UNIQUE (org_code,year)

        QueryParam[] queryParams = new QueryParam[]{
                new StringParam(seriesNumber.getOrg_code()),
                new StringParam(seriesNumber.getYear())
        };

        List<SeriesNumber> result = dataSourceProxy.executeSelect(sql, new SeriesNumberMapper(), queryParams);
        return result.getFirst();
    }


    public Optional<SeriesNumber> findByOrgCodeAndYear(String orgCode, String year) {
        String sql = "SELECT id, org_code, year, counter FROM public.series_number WHERE org_code = ? AND year = ?";

        QueryParam[] queryParams = new QueryParam[]{
                new StringParam(orgCode),
                new StringParam(year)
        };

        List<SeriesNumber> result = dataSourceProxy.executeSelect(sql, new SeriesNumberMapper(), queryParams);

        return result.isEmpty() ? Optional.empty() : Optional.of(result.getFirst());
    }

    public SeriesNumber updateCounter(SeriesNumber seriesNumber)  {
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
        return result.getFirst();
    }

}
