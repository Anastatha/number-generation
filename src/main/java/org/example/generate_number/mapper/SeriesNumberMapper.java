package org.example.generate_number.mapper;

import org.example.generate_number.entity.SeriesNumber;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SeriesNumberMapper implements ResultSetMapper<SeriesNumber> {
    @Override
    public SeriesNumber map(ResultSet resultSet) throws SQLException {
        return new SeriesNumber(
                resultSet.getInt("id"),
                resultSet.getString("org_code"),
                resultSet.getString("year"),
                resultSet.getInt("counter")
        );
    }
}


