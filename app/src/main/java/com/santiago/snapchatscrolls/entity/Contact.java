package com.santiago.snapchatscrolls.entity;

import com.santiago.entity.JSONEntity;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by santiago on 30/03/16.
 */
public class Contact extends JSONEntity {

    private static final String JSON_KEY_NAME = "name";

    private String name;

    public String getName() {
        return name;
    }

    @Override
    public void setValuesFrom(JSONEntity JSONEntity) {
        super.setValuesFrom(JSONEntity);

        name = ((Contact) JSONEntity).name;
    }

    @Override
    public void setValuesFrom(JSONObject jsonObject) throws JSONException {
        super.setValuesFrom(jsonObject);

        name = jsonObject.optString(JSON_KEY_NAME, "");
    }

    @Override
    public void setDefaultValues() {
        super.setDefaultValues();

        name = "";
    }

    @Override
    public JSONObject asJSONObject() {
        JSONObject jobj = super.asJSONObject();

        try {
            jobj.put(JSON_KEY_NAME, name);
        } catch (JSONException e) {
        }

        return jobj;
    }

}
