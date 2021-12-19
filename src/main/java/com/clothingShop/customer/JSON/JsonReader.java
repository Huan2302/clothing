package com.clothingShop.customer.JSON;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class JsonReader {
    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    public static JSONArray readJsonFromUrl(String url) throws IOException {
        InputStream is = new URL(url).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);

            Object obj = JSONValue.parse(jsonText);
            JSONArray jsonArray = (JSONArray) obj;

            return jsonArray;
        } finally {
            is.close();
        }
    }

    public List<JSONObject> listSuggest(long uid) throws IOException {
        JSONArray jsonArray = new JSONArray();
        try {
            jsonArray = readJsonFromUrl("http://127.0.0.1:5000/api/collab-filtering/"+uid);
        }catch (Exception e){
            return null;
        }
        List<JSONObject> suggest = new ArrayList<>();
        if (jsonArray != null) {
            int len = jsonArray.size();
            for (int i=0;i<len;i++){
                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                if (Integer.parseInt(String.valueOf(jsonObject.get("id"))) !=0){
                    suggest.add(jsonObject);
                }
            }
        }
        return suggest;
    }
    public static void main(String[] args) throws IOException {
        JsonReader j = new JsonReader();
        List<JSONObject> list = j.listSuggest(1);
        if (list!=null){
            for (JSONObject item:list){
                System.out.println(item.get("id"));
            }
        }else {
            System.out.println("rỗng");
        }
    }
}
