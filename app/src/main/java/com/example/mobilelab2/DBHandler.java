package com.example.mobilelab2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DBHandler extends SQLiteOpenHelper {
    private static final String CREATE_QUERY = "CREATE TABLE cities (" +
            "id SERIAL PRIMARY KEY" +
            ",name VARCHAR(255)" +
            ",distance DOUBLE" +
            ",population INT" +
            ");";

//    private int id;
//    private String name;
//    private double distance;
//    private double population;
    public DBHandler(@Nullable Context context) {
        super(context, "cities", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS cities");
    }
    public List<City> getAll(){
        List<City> returnList = new ArrayList<>();
        String queryString = "SELECT * FROM cities";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);
        if (cursor.moveToFirst()){
            do{
                int cityID = cursor.getInt(0);
                String name = cursor.getString(1);
                double  distance = cursor.getDouble(2);
                int  population = cursor.getInt(3);

                City newCity = new City(cityID, name, distance, population);
                returnList.add(newCity);
            }while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return returnList;
    }
    public List<City> getBiggestAndSmallest(){
        List<City> returnList = new ArrayList<>();
        String queryString = "SELECT * FROM cities ORDER BY distance DESC LIMIT 1";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);
        if (cursor.moveToFirst()){
            do{
                int cityID = cursor.getInt(0);
                String name = cursor.getString(1);
                double  distance = cursor.getDouble(2);
                int  population = cursor.getInt(3);

                City newCity = new City(cityID, name, distance, population);
                returnList.add(newCity);
            }while (cursor.moveToNext());
        }
        queryString = "SELECT * FROM cities ORDER BY distance ASC LIMIT 1";
        db = this.getReadableDatabase();
        cursor = db.rawQuery(queryString, null);
        if (cursor.moveToFirst()){
            do{
                int cityID = cursor.getInt(0);
                String name = cursor.getString(1);
                double  distance = cursor.getDouble(2);
                int  population = cursor.getInt(3);

                City newCity = new City(cityID, name, distance, population);
                returnList.add(newCity);
            }while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return returnList;
    }
    public List<City> getFiltered(){
        List<City> returnList = new ArrayList<>();
        String queryString = "SELECT * FROM cities WHERE population > 500000 AND distance < 500";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);
        if (cursor.moveToFirst()){
            do{
                int cityID = cursor.getInt(0);
                String name = cursor.getString(1);
                double  distance = cursor.getDouble(2);
                int  population = cursor.getInt(3);

                City newCity = new City(cityID, name, distance, population);
                returnList.add(newCity);
            }while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return returnList;
    }
    public void addCity(City city) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("name", city.getName());
        values.put("distance", city.getDistance());
        values.put("population", city.getPopulation());

        db.insert("cities", null, values);
        db.close();
    }
}
