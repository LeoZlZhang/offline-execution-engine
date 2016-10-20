package com.vipabc.vliveshow.apitest.bean.asset.dbOperation;

import leo.carnival.workers.prototype.Executor;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.*;
import java.util.Map;

/**
 * Created by leo_zlzhang on 9/20/2016.
 * postgres
 */
@SuppressWarnings({"unused", "WeakerAccess", "Duplicates"})
public enum SqlOperation implements Executor<DBObject, String> {
    insert {
        @Override
        public String execute(DBObject dbObject) {
            if (dbObject == null)
                throw new RuntimeException("dbObject should not be empty");
            if (dbObject.getTable() == null || dbObject.getTable().isEmpty())
                throw new RuntimeException("Invalid insert operation to postgres, missing table");
            if (dbObject.getValues() == null || dbObject.getValues().isEmpty())
                throw new RuntimeException("Invalid insert operation to postgres, missing values");


            StringBuilder command = new StringBuilder(String.format("insert into %s %s",
                    dbObject.getTable(),
                    valueMapToSql(dbObject.getValues())));

            logger.info(String.format("[%d] %s %s", Thread.currentThread().getId(), "SqlOperation", command));

            Statement statement = null;
            try {
                statement = getDBConnection().createStatement();
                int resultSet = statement.executeUpdate(command.toString());
                return String.valueOf(resultSet);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } finally {
                try {
                    if (statement != null)
                        statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    },
    select {
        @Override
        public String execute(DBObject dbObject) {
            if (dbObject == null)
                throw new RuntimeException("dbObject should not be empty");
            if (dbObject.getTable() == null || dbObject.getTable().isEmpty())
                throw new RuntimeException("Invalid select operation to postgres, missing table");

            StringBuilder command = new StringBuilder(String.format("select * from %s %s",
                    dbObject.getTable(),
                    criteriaMapToSql(dbObject.getCriteria())));

            logger.info(String.format("[%d] %s %s", Thread.currentThread().getId(), "SqlOperation", command));

            Statement statement = null;
            try {
                statement = getDBConnection().createStatement();
                ResultSet resultSet = statement.executeQuery(command.toString());
                return rsToJsonString(resultSet);
            } catch (SQLException | JSONException e) {
                throw new RuntimeException(e);
            } finally {
                try {
                    if (statement != null)
                        statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    },
    update {
        @Override
        public String execute(DBObject dbObject) {
            if (dbObject == null)
                throw new RuntimeException("dbObject should not be empty");
            if (dbObject.getTable() == null || dbObject.getTable().isEmpty())
                throw new RuntimeException("Invalid update operation to postgres, missing table");
            if (dbObject.getCriteria() == null || dbObject.getCriteria().isEmpty())
                throw new RuntimeException("Invalid update operation to postgres, missing criteria");

            StringBuilder command = new StringBuilder(String.format("update %s %s %s",
                    dbObject.getTable(),
                    valueMapToSet(dbObject.getValues()),
                    criteriaMapToSql(dbObject.getCriteria())));

            logger.info(String.format("[%d] %s %s", Thread.currentThread().getId(), "SqlOperation", command));

            Statement statement = null;
            try {
                statement = getDBConnection().createStatement();
                int resultSet = statement.executeUpdate(command.toString());
                return String.valueOf(resultSet);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } finally {
                try {
                    if (statement != null)
                        statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    },
    delete {
        @Override
        public String execute(DBObject dbObject) {
            if (dbObject == null)
                throw new RuntimeException("dbObject should not be empty");
            if (dbObject.getTable() == null || dbObject.getTable().isEmpty())
                throw new RuntimeException("Invalid delete operation to postgres, missing table");
            if (dbObject.getCriteria() == null || dbObject.getCriteria().isEmpty())
                throw new RuntimeException("Invalid delete operation to postgres, missing criteria");

            StringBuilder command = new StringBuilder(String.format("delete from %s %s",
                    dbObject.getTable(),
                    criteriaMapToSql(dbObject.getCriteria())));

            logger.info(String.format("[%d] %s %s", Thread.currentThread().getId(), "SqlOperation", command));

            Statement statement = null;
            try {
                statement = getDBConnection().createStatement();
                int resultSet = statement.executeUpdate(command.toString());
                return String.valueOf(resultSet);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } finally {
                try {
                    if (statement != null)
                        statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    };
    protected Logger logger = Logger.getLogger(SqlOperation.class);
    private Connection connection;

    public SqlOperation setDBConnection(Connection dbConnection) {
        this.connection = dbConnection;
        return this;
    }

    public Connection getDBConnection() {
        return connection;
    }

    public static String rsToJsonString(ResultSet rs) throws SQLException, JSONException {
        JSONObject obj = new JSONObject();
        ResultSetMetaData rsMetaData = rs.getMetaData();
        if (rs.next()) {
            for (int i = 1, len = rsMetaData.getColumnCount() + 1; i < len; i++) {
                String column_name = rsMetaData.getColumnName(i);

                if (rsMetaData.getColumnType(i) == java.sql.Types.ARRAY)
                    obj.put(column_name, rs.getArray(column_name));
                else if (rsMetaData.getColumnType(i) == java.sql.Types.BIGINT)
                    obj.put(column_name, rs.getInt(column_name));
                else if (rsMetaData.getColumnType(i) == java.sql.Types.BOOLEAN)
                    obj.put(column_name, rs.getBoolean(column_name));
                else if (rsMetaData.getColumnType(i) == java.sql.Types.BLOB)
                    obj.put(column_name, rs.getBlob(column_name));
                else if (rsMetaData.getColumnType(i) == java.sql.Types.DOUBLE)
                    obj.put(column_name, rs.getDouble(column_name));
                else if (rsMetaData.getColumnType(i) == java.sql.Types.FLOAT)
                    obj.put(column_name, rs.getFloat(column_name));
                else if (rsMetaData.getColumnType(i) == java.sql.Types.INTEGER)
                    obj.put(column_name, rs.getInt(column_name));
                else if (rsMetaData.getColumnType(i) == java.sql.Types.NVARCHAR)
                    obj.put(column_name, rs.getNString(column_name));
                else if (rsMetaData.getColumnType(i) == java.sql.Types.VARCHAR)
                    obj.put(column_name, rs.getString(column_name));
                else if (rsMetaData.getColumnType(i) == java.sql.Types.TINYINT)
                    obj.put(column_name, rs.getInt(column_name));
                else if (rsMetaData.getColumnType(i) == java.sql.Types.SMALLINT)
                    obj.put(column_name, rs.getInt(column_name));
                else if (rsMetaData.getColumnType(i) == java.sql.Types.DATE)
                    obj.put(column_name, rs.getDate(column_name));
                else if (rsMetaData.getColumnType(i) == java.sql.Types.TIMESTAMP)
                    obj.put(column_name, rs.getTimestamp(column_name));
                else
                    obj.put(column_name, rs.getObject(column_name));

            }
        }
        return obj.toString();
    }

    public String criteriaMapToSql(Map<String, Object> criteria) {
        if (criteria == null || criteria.isEmpty())
            return "";

        StringBuilder where = new StringBuilder("where");

        for (Map.Entry<String, Object> entry : criteria.entrySet())
            if (entry.getValue() instanceof String)
                where.append(String.format(" %s=\'%s\' and", entry.getKey(), entry.getValue()));
            else if (entry.getValue() instanceof Number)
                where.append(String.format(" %s=%s and", entry.getKey(), entry.getValue().toString()));
            else
                throw new RuntimeException("Invalid criteria " + entry.getValue().toString());
        where.delete(where.length() - 4, where.length());

        return where.toString();
    }

    public String valueMapToSql(Map<String, Object> valueMap) {
        if (valueMap == null || valueMap.isEmpty())
            throw new RuntimeException("Empty column value map to insert");
        StringBuilder columns = new StringBuilder("(");
        StringBuilder values = new StringBuilder(" values (");


        for (Map.Entry<String, Object> entry : valueMap.entrySet()) {
            columns.append(String.format("%s,", entry.getKey()));
            if (entry.getValue() instanceof String)
                values.append(String.format(" \'%s\',", entry.getValue()));
            else if (entry.getValue() instanceof Number)
                values.append(String.format(" %s,", entry.getValue().toString()));
            else
                throw new RuntimeException("Invalid criteria " + entry.getValue().toString());
        }
        columns.deleteCharAt(columns.length() - 1).append(")");
        values.deleteCharAt(values.length() - 1).append(")");

        return columns.append(values).toString();
    }

    public String valueMapToSet(Map<String, Object> valueMap) {
        if (valueMap == null || valueMap.isEmpty())
            throw new RuntimeException("Empty column value map to update");
        StringBuilder setCommand = new StringBuilder("set");

        for (Map.Entry<String, Object> entry : valueMap.entrySet())
            if (entry.getValue() instanceof String)
                setCommand.append(String.format(" %s=\'%s\',", entry.getKey(), entry.getValue()));
            else if (entry.getValue() instanceof Number)
                setCommand.append(String.format(" %s=%s,", entry.getKey(), entry.getValue().toString()));
            else
                throw new RuntimeException("Invalid criteria " + entry.getValue().toString());
        setCommand.deleteCharAt(setCommand.length() - 1);

        return setCommand.toString();

    }


    public SqlOperation setLogger(Logger logger) {
        this.logger = logger;
        return this;
    }
}
