package org.example.generate_number.utils;

import org.example.generate_number.error.DatabaseOperationException;
import org.example.generate_number.mapper.ResultSetMapper;
import org.example.generate_number.utils.params.QueryParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Array;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class DataSourceProxy {
    private final DataSource dataSource;

    @Autowired
    public DataSourceProxy(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public <T> List<T> executeSelect(String sql, ResultSetMapper<T> resultSetMapper, QueryParam... params) {
        try {
            PreparedStatement preparedStatement = dataSource.getConnection().prepareStatement(sql);
            setParams(preparedStatement, params);
            preparedStatement.execute();

            return mapResultSet(preparedStatement.getResultSet(), resultSetMapper);
        } catch (SQLException e) {
            throw new DatabaseOperationException(e.getMessage(), e);
        }
    }

    public void executeDelete(String sql, QueryParam... params) {
        try (PreparedStatement preparedStatement = dataSource.getConnection().prepareStatement(sql)) {
            setParams(preparedStatement, params);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new DatabaseOperationException("Ошибка при выполнении DELETE запроса", e);
        }
    }

    private void setParams(PreparedStatement preparedStatement, QueryParam... params) throws SQLException {
        for(int i = 0; i < params.length; i++){
            params[i].setQueryParamValueToStatement(preparedStatement, i+1);
        }
    }

    private <T> List<T> mapResultSet(ResultSet resultSet, ResultSetMapper<T> resultSetMapper) throws SQLException {
        List<T> list = new ArrayList<>();

        while (resultSet.next()){
            list.add(resultSetMapper.map(resultSet));
        }

        return list;
    }

    public Array createArrayOf(String type, Object[] objects){
        try {
            return dataSource.getConnection().createArrayOf(type, objects);
        } catch (SQLException e) {
            throw new DatabaseOperationException("Ошибка при создании массива SQL ", e);
        }

    }
}