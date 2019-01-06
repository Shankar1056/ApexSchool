package softgalli.gurukulshikshalay.common;


import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import softgalli.gurukulshikshalay.AppController;
import softgalli.gurukulshikshalay.model.ClassAndSectionModel;

public class ClsGeneral {
    public static void setPreferences(String key, String value) {
        SharedPreferences.Editor editor = AppController.getInstance().getSharedPreferences(
                "WED_APP", Context.MODE_PRIVATE).edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static void setPreferences(String key, boolean value) {
        SharedPreferences.Editor editor = AppController.getInstance().getSharedPreferences(
                "WED_APP", Context.MODE_PRIVATE).edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public static void setPreferences(String key, int value) {
        SharedPreferences.Editor editor = AppController.getInstance().getSharedPreferences(
                "WED_APP", Context.MODE_PRIVATE).edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public static boolean getBoolPreferences(String key) {
        SharedPreferences prefs = AppController.getInstance().getSharedPreferences("WED_APP",
                Context.MODE_PRIVATE);
        return prefs.getBoolean(key, false);
    }

    public static String getStrPreferences(String key) {
        SharedPreferences prefs = AppController.getInstance().getSharedPreferences("WED_APP",
                Context.MODE_PRIVATE);
        return prefs.getString(key, "");
    }

    public static int getIntPreferences(String key) {
        SharedPreferences prefs = AppController.getInstance().getSharedPreferences("WED_APP",
                Context.MODE_PRIVATE);
        return prefs.getInt(key, 0);
    }

    public static List<ClassAndSectionModel> getClassAndSection() {
        Gson gson = new Gson();
        List<ClassAndSectionModel> productFromShared;
        SharedPreferences sharedPref = AppController.getInstance().getSharedPreferences("CLASS_AND_SECTION", Context.MODE_PRIVATE);
        String jsonPreferences = sharedPref.getString("CLASS_AND_SECTION", "");

        Type type = new TypeToken<List<ClassAndSectionModel>>() {
        }.getType();
        productFromShared = gson.fromJson(jsonPreferences, type);
        if (productFromShared != null && productFromShared.size() > 0)
            return productFromShared;
        else
            return null;
    }

    private void setClassAndSection(ClassAndSectionModel classAndSection) {
        Gson gson = new Gson();
        String jsonCurProduct = gson.toJson(classAndSection);

        SharedPreferences sharedPref = AppController.getInstance().getSharedPreferences("CLASS_AND_SECTION", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        editor.putString("CLASS_AND_SECTION", jsonCurProduct);
        editor.commit();
    }
}
