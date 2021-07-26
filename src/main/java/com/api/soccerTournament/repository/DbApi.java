package com.api.soccerTournament.repository;

import com.api.soccerTournament.model.Entity;
import com.api.soccerTournament.model.response.Const;
import com.api.soccerTournament.model.response.Response;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.Optional;

@Repository
class DbApi {
    @Value("${mySqlUrl}")
    private String mySqlUrl;

    @Value("${mySqlDb}")
    private String mySqlDb;

    @Value("${mySqlUsername}")
    private String mySqlUsername;

    @Value("${mySqlPassword}")
    private String mySqlPassword;

    private static HikariDataSource dataSource = null;

    private void initialize() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(mySqlUrl);
        config.setUsername(mySqlUsername);
        config.setPassword(mySqlPassword);
        config.setDriverClassName("com.mysql.jdbc.Driver");
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

        dataSource = new HikariDataSource(config);
    }

    Connection getConnection() throws SQLException {
        if (dataSource == null) {
            initialize();
        }

        return dataSource.getConnection();
    }

    Response readByColumn(String colName, String tableName, Class<? extends Entity> cls, Object colValue) {
        StringBuilder filterStrBuilder = new StringBuilder();
        filterStrBuilder.append(colName).append("=?");
        ArrayList<Object> parameters = new ArrayList<>() {{
            add(colValue);
        }};
        Response response = readByFilters(tableName, cls, filterStrBuilder.toString(), parameters);
        return response;
    }

    Response readTable(String tableName, Class<? extends Entity> cls) {
        Response response = readByFilters(tableName, cls, null, null);
        return response;
    }

    Response readByFilters(String tableName, Class<? extends Entity> cls, String filters, ArrayList<Object> parameters) {
        Response response;
        try {
            ArrayList<String> columnNames = getColNames(tableName);
            StringBuilder sqlStrBuilder = new StringBuilder();
            sqlStrBuilder.append("select ");
            for (int i = 0; i < columnNames.size(); i++) {
                sqlStrBuilder.append(columnNames.get(i)).append(",");
            }
            sqlStrBuilder.deleteCharAt(sqlStrBuilder.length() - 1);
            sqlStrBuilder.append(" from ").append(tableName);
            if ((filters != null) && (filters.trim().length() > 0)) {
                sqlStrBuilder.append(" where ").append(filters);
            }

            response = read(sqlStrBuilder.toString(), parameters, cls);
        } catch (Exception ex) {
            response = new Response(Const.statusCodeFail, ex.getMessage());
        }

        return response;
    }

    Response read(String sql, Class<? extends Entity> cls) {
        Response response = read(sql, null, cls);
        return response;
    }

    Response read(String sql, ArrayList<Object> parameters, Class<? extends Entity> cls) {
        ArrayList<Entity> entities = new ArrayList<>();

        Response response;
        try {
            Connection connection = getConnection();
            PreparedStatement prepareStatement = connection.prepareStatement(sql);
            if (parameters != null) {
                for (int i = 0; i < parameters.size(); i++) {
                    prepareStatement.setObject(i + 1, parameters.get(i));
                }
            }

            ResultSet rs = prepareStatement.executeQuery();
            ResultSetMetaData resultSetMetaData = prepareStatement.getMetaData();

            String colName;
            while (rs.next()) {
                Entity entity = cls.newInstance();
                for (int i = 1; i <= resultSetMetaData.getColumnCount(); i++) {
                    colName = resultSetMetaData.getColumnName(i);
                    Field field = cls.getField(colName);
                    field.set(entity, rs.getObject(colName));
                }
                entities.add(entity);
            }

            prepareStatement.close();
            response = new Response(Const.statusCodeSucceed, entities);
        } catch (Exception ex) {
            response = new Response(Const.statusCodeFail, ex.getMessage());
        }

        return response;
    }

    private ArrayList<String> getColNames(String tableName) throws SQLException {
        ArrayList<String> colNames = new ArrayList<>();
        StringBuilder sqlStrBuilder = new StringBuilder();
        sqlStrBuilder.append("select column_name from information_schema.columns\n");
        sqlStrBuilder.append("where table_schema=? and table_name=?");
        String sql = sqlStrBuilder.toString();

        Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setObject(1, mySqlDb);
        preparedStatement.setObject(2, tableName);

        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next()) {
            colNames.add(rs.getString(1));
        }
        return colNames;
    }

    Response write(Optional<? extends Entity> optionalEntity, String tableName) {
        Response response;

        try {
            Connection connection = getConnection();
            response = write(connection, optionalEntity, tableName);
        } catch (Exception ex) {
            response = new Response(Const.statusCodeFail, ex.getMessage());
        }

        return response;
    }

    Response write(Connection connection, Optional<? extends Entity> optionalEntity, String tableName) {
        Response response;
        try {
            Entity entity = optionalEntity.get();
            Class cls = optionalEntity.get().getClass();

            StringBuilder sqlStrBuilder = new StringBuilder();
            sqlStrBuilder.append("insert into ").append(tableName).append("(");

            ArrayList<String> colNames = getColNames(tableName);

            StringBuilder paramBuilder = new StringBuilder();
            paramBuilder.append("(");
            for (int i = 0; i < colNames.size(); i++) {
                if ((colNames.get(i).equalsIgnoreCase("id")) && (entity.id == null)) {
                    continue;
                }
                sqlStrBuilder.append(colNames.get(i)).append(",");
                paramBuilder.append("?,");
            }
            sqlStrBuilder.deleteCharAt(sqlStrBuilder.length() - 1).append(")").append("values");
            sqlStrBuilder.append(paramBuilder).deleteCharAt(sqlStrBuilder.length() - 1);
            sqlStrBuilder.append(")");
            String sql = sqlStrBuilder.toString();
            PreparedStatement prepareStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            Field field;
            int paramCnt = 0;
            for (int i = 0; i < colNames.size(); i++) {
                if ((colNames.get(i).equalsIgnoreCase("id")) && (entity.id == null)) {
                    continue;
                }
                field = cls.getField(colNames.get(i));
                Object value = field.get(entity);
                prepareStatement.setObject(++paramCnt, value);
            }

            prepareStatement.execute();

            response = new Response(Const.statusCodeSucceed);
            ResultSet res = prepareStatement.getGeneratedKeys();
            while (res.next()) {
                Integer id = res.getInt(1);
                response.entities.add(new Entity(id));
            }
        } catch (Exception ex) {
            response = new Response(Const.statusCodeFail, ex.getMessage());
        }

        return response;
    }

    Response delete(Integer id, String tableName) {
        Response response;
        try {
            Connection connection = getConnection();
            response = delete(connection, id, tableName);
        } catch (Exception ex) {
            response = new Response(Const.statusCodeFail, ex.getMessage());
        }

        return response;
    }

    Response delete(Connection connection, Integer id, String tableName) {
        StringBuilder sqlStrBuilder = new StringBuilder();
        sqlStrBuilder.append("delete from ").append(tableName).append(" where id=?");
        ArrayList<Object> parameters = new ArrayList<>() {{
            add(id);
        }};

        Response response = executeNonQuery(connection, sqlStrBuilder.toString(), parameters);
        return response;
    }

    Response executeNonQuery(String sql, ArrayList<Object> parameters) {
        Response response;
        try {
            Connection connection = getConnection();
            response = executeNonQuery(connection, sql, parameters);
        } catch (Exception ex) {
            response = new Response(Const.statusCodeFail, ex.getMessage());
        }

        return response;
    }

    Response executeNonQuery(Connection connection, String sql, ArrayList<Object> parameters) {
        Response response;

        try {
            PreparedStatement prepareStatement = connection.prepareStatement(sql);

            if (parameters != null) {
                for (int i = 0; i < parameters.size(); i++) {
                    prepareStatement.setObject(i + 1, parameters.get(i));
                }
            }

            prepareStatement.execute();
            response = new Response(Const.statusCodeSucceed);
        } catch (Exception ex) {
            response = new Response(Const.statusCodeFail, ex.getMessage());
        }

        return response;
    }
}
