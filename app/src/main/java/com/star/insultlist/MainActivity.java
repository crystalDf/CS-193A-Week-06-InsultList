package com.star.insultlist;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class MainActivity extends AppCompatActivity {

    private List<String> mLines;
    private TextToSpeech mTextToSpeech;
    private boolean mSpeechReady = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLines = readEntireFile(R.raw.phrases);

        ListView listView = (ListView) findViewById(R.id.list_of_insults);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, mLines);

        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                handleClick(position);
            }
        });

        mTextToSpeech = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                mSpeechReady = true;
            }
        });
    }

    private void handleClick(int position) {
        String line = mLines.get(position);

        if (mSpeechReady) {
            mTextToSpeech.speak(line, TextToSpeech.QUEUE_FLUSH, null);
        }
    }

    private List<String> readEntireFile(int id) {
        List<String> lines = new ArrayList<>();

        Scanner scanner = new Scanner(getResources().openRawResource(id));

        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            lines.add(line);
        }

        return lines;
    }
}
