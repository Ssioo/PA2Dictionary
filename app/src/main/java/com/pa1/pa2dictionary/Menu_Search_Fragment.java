package com.pa1.pa2dictionary;


import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.io.InputStreamReader;
import java.util.ArrayList;

import static android.content.Context.INPUT_METHOD_SERVICE;


public class Menu_Search_Fragment extends Fragment {
    public Menu_Search_Fragment() {

    }

    private ArrayList<Pair<String, String>> SearchList = new ArrayList<>();
    private ArrayList<String> SearchList_title = new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_menu_search, container, false);
        final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(INPUT_METHOD_SERVICE);

        final ListView wordlist = view.findViewById(R.id.word_list);
        Button btn_search = view.findViewById(R.id.btn_search);
        final EditText type_word = view.findViewById(R.id.text_input);
        btn_search.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                imm.hideSoftInputFromWindow(type_word.getWindowToken(), 0);
                String mWord = type_word.getText().toString();
                SharedPreferences sd = getActivity().getSharedPreferences("history", 0);
                String past = sd.getString("historyset", "");
                SharedPreferences.Editor editor = sd.edit();

                editor.commit();
                if (past == "") {
                    editor.putString("historyset", mWord);
                    editor.commit();
                }
                else {
                    editor.putString("historyset", past + "," + mWord); // history에 mWord 더하기
                    editor.commit();
                }
                Log.e("past_onSearch", past);
                SearchList.clear();
                SearchList_title.clear();
                Pair<String, String> x;

                for (int i=0; i<((MainActivity)getActivity()).data.size(); i++) {
                    //Log.e("what", x.first);
                    if (((MainActivity) getActivity()).data.get(i).first.equals(mWord)) {

                        SearchList.add(((MainActivity) getActivity()).data.get(i));
                        SearchList_title.add(((MainActivity) getActivity()).data.get(i).first);
                    }

                }
                for (int i=0; i<((MainActivity)getActivity()).data.size(); i++) {
                    //Log.e("what", x.first);
                    if (((MainActivity) getActivity()).data.get(i).first.startsWith(mWord) && !((MainActivity) getActivity()).data.get(i).first.equals(mWord)) {
                        SearchList.add(((MainActivity) getActivity()).data.get(i));
                        SearchList_title.add(((MainActivity) getActivity()).data.get(i).first);
                    }

                }

                ArrayAdapter adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, SearchList_title.toArray(new String[SearchList_title.size()]));

                wordlist.setAdapter(adapter);
            }
        });

        wordlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id) {
                AlertDialog.Builder ad = new AlertDialog.Builder(getActivity());

                ad.setTitle(SearchList.get(position).first); // 제목
                ad.setMessage(SearchList.get(position).second); // 내용

                ad.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                ad.show();
            }
        });
        return view;
    }
}
