package com.api.soccerTournament.repository;

import com.api.soccerTournament.model.Entity;
import com.api.soccerTournament.model.response.Const;
import com.api.soccerTournament.model.response.Response;
import com.api.soccerTournament.utility.Utility;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Repository
class DbApi {
    @Value("${mySqlHost}")
    private String mySqlHost;

    @Value("${mySqlPort}")
    private String mySqlPort;

    @Value("${mySqlDb}")
    private String mySqlDb;

    @Value("${mySqlUsername}")
    private String mySqlUsername;

    @Value("${mySqlPassword}")
    private String mySqlPassword;

    private static HikariDataSource dataSource = null;
    private static Lock lock = new ReentrantLock();

    private void initialize() {
        HikariConfig config = new HikariConfig();
        StringBuilder urlBuilder = new StringBuilder();
        urlBuilder.append("jdbc:mysql://").append(mySqlHost).append(":").append(mySqlPort);
        urlBuilder.append("/").append(mySqlDb);
        String mySqlUrl = urlBuilder.toString();
        config.setJdbcUrl(mySqlUrl);
        config.setUsername(mySqlUsername);
        config.setPassword(mySqlPassword);
        config.setMaximumPoolSize(50);
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

        dataSource = new HikariDataSource(config);
    }

    Connection getConnection() throws SQLException {
        lock.lock();
        if (dataSource == null) {
            Utility.printStr("initialize...");
            initialize();
        }
        if (dataSource.isClosed()) {
            Utility.printStr("isClosed: initialize...");
            initialize();
        }
        lock.unlock();

        Connection connection = dataSource.getConnection();
        return connection;
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
        try (Connection connection = getConnection()) {
            ArrayList<String> columnNames = getColNames(connection, tableName);
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
        try (Connection connection = getConnection()) {
            Response response = read(connection, sql, parameters, cls);
            return response;
        } catch (Exception ex) {
            return new Response(Const.statusCodeFail, ex.getMessage());
        }
    }

    Response read(Connection connection, String sql, ArrayList<Object> parameters, Class<? extends Entity> cls) {
        ArrayList<Entity> entities = new ArrayList<>();

        Response response;
        try {
            PreparedStatement prepareStatement = connection.prepareStatement(sql);
            if (parameters != null) {
                for (int i = 0; i < parameters.size(); i++) {
                    prepareStatement.setObject(i + 1, parameters.get(i));
                }
            }

            ResultSet rs = prepareStatement.executeQuery();
            ResultSetMetaData resultSetMetaData = prepareStatement.getMetaData();

            String colName;
            Object propertyVal;
            Byte propertyValByte;
            HashMap<String, Field> colFieldMap = new HashMap<>();
            Field field;
            for (int i = 0; i < cls.getFields().length; i++) {
                field = cls.getFields()[i];
                colFieldMap.put(field.getName(), field);
            }
            while (rs.next()) {
                Entity entity = cls.getDeclaredConstructor().newInstance();
                for (int i = 1; i <= resultSetMetaData.getColumnCount(); i++) {
                    colName = resultSetMetaData.getColumnLabel(i);
                    if (!colFieldMap.containsKey(colName)) {
                        continue;
                    }
                    field = colFieldMap.get(colName);

                    propertyVal = rs.getObject(colName);
                    if (field.getType().equals(Byte.class)) {
                        propertyValByte = Byte.valueOf(propertyVal.toString());
                        field.set(entity, propertyValByte);
                    } else {
                        field.set(entity, propertyVal);
                    }
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

    private ArrayList<String> getColNames(Connection connection, String tableName) throws SQLException {
        ArrayList<String> colNames = new ArrayList<>();
        StringBuilder sqlStrBuilder = new StringBuilder();
        sqlStrBuilder.append("select column_name from information_schema.columns\n");
        sqlStrBuilder.append("where table_schema='").append(mySqlDb).append("' and table_name=?");
        String sql = sqlStrBuilder.toString();

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setObject(1, tableName);

        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next()) {
            colNames.add(rs.getString(1));
        }

        return colNames;
    }

    Response write(Optional<? extends Entity> optionalEntity, String tableName) {
        Response response;

        try (Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(false);
            response = write(connection, optionalEntity, tableName);
            connection.commit();
        } catch (Exception ex) {
            response = new Response(Const.statusCodeFail, ex.getMessage());
        }

        return response;
    }

    Response write(Connection connection, Optional<? extends Entity> optionalEntity, String tableName) throws SQLException, NoSuchFieldException, IllegalAccessException {
        Response response;
        Entity entity = optionalEntity.get();
        Class cls = optionalEntity.get().getClass();

        StringBuilder sqlStrBuilder = new StringBuilder();
        sqlStrBuilder.append("insert into ").append(tableName).append("(");

        ArrayList<String> colNames = getColNames(connection, tableName);

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

        return response;
    }

    Response delete(Integer id, String tableName) {
        Response response;
        try {
            Connection connection = getConnection();
            connection.setAutoCommit(false);
            response = delete(connection, id, tableName);
            connection.commit();
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
            connection.setAutoCommit(false);
            response = executeNonQuery(connection, sql, parameters);
            connection.commit();
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
