package com.vipabc.vliveshow.apitest.bean.asset.dbOperation;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by leo_zlzhang on 9/19/2016.
 * base
 */
@SuppressWarnings({"WeakerAccess", "unused"})
public class DBObject implements Serializable {

    private String table;
    private Map<String, Object> criteria;
    private Map<String, Object> values;


    public String getTable() {
        return table;
    }

    public Map<String, Object> getCriteria() {
        return criteria;
    }

    public Map<String, Object> getValues() {
        return values;
    }


}
