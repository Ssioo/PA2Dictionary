package com.pa1.pa2dictionary;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;


public class Menu_History_Fragment extends Fragment {
    public Menu_History_Fragment() {
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu_history, container, false);

        SharedPreferences sd = getActivity().getSharedPreferences("history", 0);
        String past = sd.getString("historyset", "");

        Log.e("past_onHistory", past);
        final ListView history_list = view.findViewById(R.id.history_list);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, past.split(","));
        history_list.setAdapter(adapter);
        history_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id) {

            }
        });

        Button btn_reset = view.findViewById(R.id.btn_reset);
        btn_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sd = getActivity().getSharedPreferences("history", 0);
                SharedPreferences.Editor editor = sd.edit();
                editor.putString("historyset", "");
                editor.commit();
                ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, sd.getString("historyset", "").split(","));
                history_list.setAdapter(adapter);
            }
        });
        return view;
    }
}
