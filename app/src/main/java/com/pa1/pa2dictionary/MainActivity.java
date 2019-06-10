package com.pa1.pa2dictionary;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.MenuItem;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    public ArrayList<Pair<String, String>> data = new ArrayList<>();

    private FragmentManager fragmentManager = getSupportFragmentManager();
    //public Menu_Bookmark_Fragment menu_bookmark_fragment = new Menu_Bookmark_Fragment();
    //public Menu_History_Fragment menu_history_fragment = new Menu_History_Fragment();
    //public Menu_Search_Fragment menu_search_fragment = new Menu_Search_Fragment();

    public String history = "";

    public MainActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sd = getSharedPreferences("history", MODE_PRIVATE);
        history = sd.getString("historyset", "");

        Log.e("past_onMAIN", history);

        InputStream is = getResources().openRawResource(R.raw.dataa);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
        String s = "";
        try {

            //reader.readLine();
            int count = 0;
            while ((s = reader.readLine()) != null) {
                if (!s.isEmpty()) {
                    String title = s.substring(0, s.indexOf("(") - 1);
                    String contents = s.substring(s.indexOf("("));
                    data.add(new Pair(title, contents));
                    //Log.e("count", String.valueOf(count++));
                }

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation_view);

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.frame_layout, new Menu_Search_Fragment()).commit();


        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                FragmentTransaction fragmentTransaction1 = fragmentManager.beginTransaction();
                switch (menuItem.getItemId()) {
                    case R.id.navigation_menu1: {
                        fragmentTransaction1.replace(R.id.frame_layout, new Menu_Search_Fragment()).commit();
                        break;
                    }
                    case R.id.navigation_menu2: {
                        fragmentTransaction1.replace(R.id.frame_layout, new Menu_History_Fragment()).commit();
                        break;
                    }
                    case R.id.navigation_menu3: {
                        fragmentTransaction1.replace(R.id.frame_layout, new Menu_Bookmark_Fragment()).commit();
                        break;
                    }

                }
                return true;
            }
        });
    }
}
