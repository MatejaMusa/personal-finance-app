package com.matejamusa.personal_finance.rowmapper;

import com.matejamusa.personal_finance.model.GraphPoint;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;


public class GraphPointRowMapper implements RowMapper<GraphPoint> {
    @Override
    public GraphPoint mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return GraphPoint.builder()
                .name(resultSet.getString("name"))
                .percentage(resultSet.getFloat("percentage"))
                .build();
    }
}