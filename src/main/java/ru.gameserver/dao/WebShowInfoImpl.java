package ru.gameserver.dao;

import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.HashMap;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class WebShowInfoImpl implements WebShowInfo {

    private final JdbcTemplate jdbcTemplate;
    private final DataSource dataSource;
    @Autowired
    public WebShowInfoImpl(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.dataSource = dataSource;
    }

    @Override
    public List<Map<String, Object>> getTags() {
        try {
            return jdbcTemplate.queryForList(
                "select t.tag_id as id,t.tag_typ as tags,t.tag_val as descr from hackatone.hacka.tags2 t");
        } catch (Exception e) {
            return null;
        }
    }


    @Override
    public List<Map<String, String>> getScen(Integer[] t) {

        String callSQL = "select * from hackatone.hacka.getScenByTagsId(?);";
        if(t.length==0)return null;

        try (Connection con = dataSource.getConnection();
            PreparedStatement preparedStatement = con.prepareStatement(callSQL)) {


            Array ar4 = con.createArrayOf("int4", t);

            preparedStatement.setArray(1, ar4);
            ResultSet rs = preparedStatement.executeQuery();

            ResultSetMetaData rsmd = rs.getMetaData();
            List<String> columns = new ArrayList<String>(rsmd.getColumnCount());
            for(int i = 1; i <= rsmd.getColumnCount(); i++){
                columns.add(rsmd.getColumnName(i));
            }
            List<Map<String,String>> data = new ArrayList<Map<String,String>>();
            while(rs.next()){
                Map<String,String> row = new HashMap<String, String>(columns.size());
                for(String col : columns) {
                    row.put(col, rs.getString(col));
                }
                data.add(row);
            }
            return data;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<Map<String, Object>> getStepsInScen(int id) {
        try {

            return jdbcTemplate.queryForList(
                "select s.id,s.no,s.button,s.descr,s.typ from hackatone.hacka.scenario_step s where s.id="
                    + id+" order BY s.no");
        } catch (Exception e) {
            return null;
        }
    }

}