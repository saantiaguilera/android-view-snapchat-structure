package com.santiago.entity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Entity capable of inflating and serializing from JSONs
 *
 * Created by santiago on 19/03/16.
 */
public abstract class JSONEntity {

    /*-------------------------------------------------JSON Fields References-------------------------------------------------*/

    public static final String ID_JSON = "id";

	/*-------------------------------------------------Fields-----------------------------------------------------------------*/

    private long id;

	/*------------------------------------------------Constructors------------------------------------------------------------*/

    public JSONEntity() {
        setDefaultValues();
    }

    public JSONEntity(JSONEntity JSONEntity) {
        setValuesFrom(JSONEntity);
    }

    public JSONEntity(JSONObject jsonObject) throws JSONException {
        setValuesFrom(jsonObject);
    }

    public JSONEntity(String json) throws JSONException {
        this(new JSONObject(json));
    }

	/*------------------------------------------------Getters------------------------------------------------------------*/

    public long getId() {
        return id;
    }

	/*------------------------------------------------Setters------------------------------------------------------------*/

    public void setId(long id) {
        this.id = id;
    }

    public void setValuesFrom(JSONEntity JSONEntity){
        if(JSONEntity != null)
            setId(JSONEntity.getId());
        else setDefaultValues();
    }

    public void setValuesFrom(JSONObject jsonObject) throws JSONException {
        if (jsonObject == null)
            throw new JSONException("JSONObject cannot be null");

        setId(jsonObject.optLong(ID_JSON));
    }

    public void setDefaultValues(){
        setId(0);
    }

	/*----------------------------------------------------Methods---------------------------------------------------------*/

    @Override
    public String toString() {
        return asJson();
    }

    public String asJson(){
        return asJSONObject().toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;

        if (!(obj instanceof JSONEntity))
            return false;

        if (((JSONEntity) obj).getId() == getId())
            return true;
        else return false;

    }

    /*---------------------------------------------------JSON Serializer--------------------------------------------------------*/

    public JSONObject asJSONObject(){
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put(ID_JSON, getId());
        } catch(JSONException e){}

        return jsonObject;
    }

    /**
     *
     * If you dont like this, just go with creating in each of your JSONEntities this and {@link JSONEntity#listFromJSONArray(Class, JSONArray)} and change the Reflection things (and T)
     * for their own Classes
     * @param list
     * @param <T>
     * @return
     */
    public static <T extends JSONEntity> JSONArray listAsJSONArray(List<T> list) {
        if(list == null)
            throw new NullPointerException("list cannot be null in listAsJSONArray");

        JSONArray result = new JSONArray();

        for(T t : list)
            result.put(t.asJSONObject());

        return result;
    }

    /**
     * TODO refactor. Is this the right approach ? I dont like it
     * If you dont like this, just go with creating in each of your JSONEntities this and {@link JSONEntity#listAsJSONArray(List)} and change the Reflection things (and T)
     * for their own Classes
     * @param jarr
     * @return
     * @throws JSONException
     */
    public static <T extends JSONEntity> List<T> listFromJSONArray(Class<T> mClass, JSONArray jarr) throws JSONException {
        if (jarr == null)
            throw new NullPointerException("JSONArray cannot be null");

        List<T> result = new ArrayList<>();

        for (int i = 0; i < jarr.length(); i++) {
            try {
                result.add(mClass.getConstructor(JSONObject.class).newInstance(jarr.getJSONObject(i)));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return result;
    }

}
