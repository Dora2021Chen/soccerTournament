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
import java.util.Map;
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

    @Override
    public void finalize() {
        if ((dataSource != null) && (!dataSource.isClosed())) {
            Utility.printStr("close data source...");
            dataSource.close();
        }
    }

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
            //Utility.printStr("initialize...");
            initialize();
        }
        if (dataSource.isClosed()) {
            //Utility.printStr("isClosed: initialize...");
            initialize();
        }
        lock.unlock();

        Connection connection = dataSource.getConnection();
        return connection;
    }

    Response readByColumn(String colName, String tableName, Class<? extends Entity> cls, Object colValue) {
        StringBuilder filterStrBuilder = new StringBuilder();
        filterStrBuilder.append(colName).append("=?");
        ArrayList<Object> parameters = new ArrayList<Object>() {{
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
            response = new Response(Const.STATUS_CODE_FAIL, ex.getMessage());
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
            return new Response(Const.STATUS_CODE_FAIL, ex.getMessage());
        }
    }

    private HashMap<String, Field> getResultColumnFieldMap(Class cls, ResultSetMetaData resultSetMetaData)
            throws SQLException {
        HashMap<String, Field> clsPropertyNameFieldMap = new HashMap<>();
        Field field;
        String colName;
        for (int i = 0; i < cls.getFields().length; i++) {
            field = cls.getFields()[i];
            clsPropertyNameFieldMap.put(field.getName(), field);
        }

        HashMap<String, Field> resultColFieldMap = new HashMap<>();
        for (int i = 1; i <= resultSetMetaData.getColumnCount(); i++) {
            colName = resultSetMetaData.getColumnLabel(i);
            if (!clsPropertyNameFieldMap.containsKey(colName)) {
                continue;
            }
            resultColFieldMap.put(colName, clsPropertyNameFieldMap.get(colName));
        }

        return resultColFieldMap;
    }

    Response read(Connection connection, String sql, ArrayList<Object> parameters, Class<? extends Entity> cls) throws Exception {
        ArrayList<Entity> entities = new ArrayList<>();

        Response response;
        PreparedStatement prepareStatement = connection.prepareStatement(sql);
        if (parameters != null) {
            for (int i = 0; i < parameters.size(); i++) {
                prepareStatement.setObject(i + 1, parameters.get(i));
            }
        }

        ResultSet rs = prepareStatement.executeQuery();
        ResultSetMetaData resultSetMetaData = prepareStatement.getMetaData();
        HashMap<String, Field> resultColFieldMap = getResultColumnFieldMap(cls, resultSetMetaData);

        String colName;
        Object propertyVal;
        Byte propertyValByte;
        Field field;
        while (rs.next()) {
            Entity entity = cls.getDeclaredConstructor().newInstance();
            for (Map.Entry<String, Field> entry : resultColFieldMap.entrySet()) {
                colName = entry.getKey();
                field = entry.getValue();

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
        response = new Response(Const.STATUS_CODE_SUCCEED, entities);

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

    <S extends Entity> Response write(S entity, String tableName) {
        Response response;

        try (Connection connection = getConnection()) {
            connection.setAutoCommit(false);
            response = write(connection, entity, tableName);
            connection.commit();
        } catch (Exception ex) {
            response = new Response(Const.STATUS_CODE_FAIL, ex.getMessage());
        }

        return response;
    }

    <S extends Entity> Response write(Connection connection, S entity, String tableName) throws SQLException, NoSuchFieldException, IllegalAccessException {
        Response response;
        Class cls = entity.getClass();

        StringBuilder sqlStrBuilder = new StringBuilder();
        sqlStrBuilder.append("insert into ").append(tableName).append("(");

        ArrayList<String> colNames = getColNames(connection, tableName);

        StringBuilder paramBuilder = new StringBuilder();
        paramBuilder.append("(");
        String colName;
        StringBuilder updateBuilder = new StringBuilder();
        updateBuilder.append("ON DUPLICATE KEY UPDATE ");
        for (int i = 0; i < colNames.size(); i++) {
            colName = colNames.get(i);
            if ((colName.equalsIgnoreCase("id")) && (entity.id == null)) {
                continue;
            }
            sqlStrBuilder.append(colName).append(",");
            paramBuilder.append("?,");
            updateBuilder.append(colName).append("=VALUES(").append(colName).append("),");
        }
        sqlStrBuilder.deleteCharAt(sqlStrBuilder.length() - 1).append(")").append("values");
        sqlStrBuilder.append(paramBuilder).deleteCharAt(sqlStrBuilder.length() - 1);
        sqlStrBuilder.append(")");
        sqlStrBuilder.append(updateBuilder).deleteCharAt(sqlStrBuilder.length() - 1);
        //Utility.printStr(sqlStrBuilder.toString());
        String sql = sqlStrBuilder.toString();

        PreparedStatement prepareStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        Field field;
        int paramCnt = 0;

        for (int i = 0; i < colNames.size(); i++) {
            colName = colNames.get(i);
            if ((colName.equalsIgnoreCase("id")) && (entity.id == null)) {
                continue;
            }
            field = cls.getField(colName);
            Object value = field.get(entity);
            prepareStatement.setObject(++paramCnt, value);
        }

        prepareStatement.execute();

        response = new Response(Const.STATUS_CODE_SUCCEED);
        ResultSet res = prepareStatement.getGeneratedKeys();
        while (res.next()) {
            Integer id = res.getInt(1);
            response.entities.add(new Entity(id));
        }

        return response;
    }

    Response delete(Integer id, String tableName) {
        Response response;
        try (Connection connection = getConnection()) {
            connection.setAutoCommit(false);
            response = delete(connection, id, tableName);
            connection.commit();
        } catch (Exception ex) {
            response = new Response(Const.STATUS_CODE_FAIL, ex.getMessage());
        }

        return response;
    }

    Response delete(Connection connection, Integer id, String tableName) throws Exception {
        StringBuilder sqlStrBuilder = new StringBuilder();
        sqlStrBuilder.append("delete from ").append(tableName).append(" where id=?");
        ArrayList<Object> parameters = new ArrayList<Object>() {{
            add(id);
        }};

        Response response = executeNonQuery(connection, sqlStrBuilder.toString(), parameters);
        return response;
    }

    Response executeNonQuery(String sql, ArrayList<Object> parameters) {
        Response response;
        try (Connection connection = getConnection()) {
            connection.setAutoCommit(false);
            response = executeNonQuery(connection, sql, parameters);
            connection.commit();
        } catch (Exception ex) {
            response = new Response(Const.STATUS_CODE_FAIL, ex.getMessage());
        }

        return response;
    }

    Response executeNonQuery(Connection connection, String sql, ArrayList<Object> parameters) throws Exception {
        Response response;
        PreparedStatement prepareStatement = connection.prepareStatement(sql);
        if (parameters != null) {
            for (int i = 0; i < parameters.size(); i++) {
                prepareStatement.setObject(i + 1, parameters.get(i));
            }
        }

        prepareStatement.execute();
        response = new Response(Const.STATUS_CODE_SUCCEED);

        return response;
    }
}
