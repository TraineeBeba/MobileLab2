package com.example.mobilelab2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    DBHandler dbHandler;
    Button btnAddCity, btnGetAll, btnGetFiltered, btnGetBiggestAndSmallest;
    ArrayAdapter citiesArrayAdapter;
    ListView lv_citiesList;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        dbHandler = new DBHandler(this);
        lv_citiesList = findViewById(R.id.lv_citiesList);

        lv_citiesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                City city = (City) adapterView.getItemAtPosition(i);
                String name = city.getName();
                Intent intent = new Intent(MainActivity.this, GeolocationActivity.class);
//                Log.i("CONTACT_ADDRESS", name);
                intent.putExtra("CITY_NAME", name);
                startActivity(intent);
            }
        });


        btnAddCity = findViewById(R.id.btnAddCity);
        // Set a click listener for the button
        btnAddCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addCity();
            }
        });
        btnGetAll = findViewById(R.id.btnGetAll);
        // Set a click listener for the button
        btnGetAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                citiesArrayAdapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, dbHandler.getAll());
                lv_citiesList.setAdapter(citiesArrayAdapter);
            }
        });
        btnGetFiltered = findViewById(R.id.btnGetFiltered);
        // Set a click listener for the button
        btnGetFiltered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                citiesArrayAdapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, dbHandler.getFiltered());
                lv_citiesList.setAdapter(citiesArrayAdapter);
            }
        });
        btnGetBiggestAndSmallest = findViewById(R.id.btnGetBiggestAndSmallest);
        // Set a click listener for the button
        btnGetBiggestAndSmallest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                citiesArrayAdapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, dbHandler.getBiggestAndSmallest());
                lv_citiesList.setAdapter(citiesArrayAdapter);
            }
        });
    }
    public void addCity() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();

        // Inflate and set the layout for the dialog
        View view = inflater.inflate(R.layout.add_city, null);
        builder.setView(view);

        // Set up the input fields
        final EditText nameEditText = view.findViewById(R.id.editTextName);
        final EditText distanceEditText = view.findViewById(R.id.editTextDistance);
        final EditText populationEditText = view.findViewById(R.id.editTextPopulation);

        // Set up the buttons
        builder.setPositiveButton("Add", (dialog, which) -> {
            String name = nameEditText.getText().toString();
            double distance = Double.parseDouble(distanceEditText.getText().toString());
            int population = Integer.parseInt(populationEditText.getText().toString());

            // Create a City object with the input values
            City newCity = new City(name, distance, population);

            // Call the addCity method to add the new city to the database
            dbHandler.addCity(newCity);
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

        builder.show();
    }
}
