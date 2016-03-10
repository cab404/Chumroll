package com.cab404.chumroll;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.cab404.chumroll.example.R;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Simple showcase of ChumrollAdapter
 *
 * @author cab404
 */
public class TestActivity extends Activity {
    ChumrollAdapter adapter = new ChumrollAdapter();
    ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        list = (ListView) findViewById(R.id.list);

        /*
         We plan to add new types of things after adapter is connected,
         so we should prepare it for that.
         */
        adapter.prepareFor(
                new ItemA(), new ItemB(), new ItemC()
        );

        list.setAdapter(adapter);

    }

    protected Sentence generateSentence(){
        Sentence sentence = new Sentence();
        String[] prefixes = getResources().getStringArray(R.array.prefix);

        String prefix = prefixes[((int) (prefixes.length * Math.random()))];

        sentence.prefix = prefix;
        sentence.word = generateWord();

        return sentence;
    }

    private String generateWord() {
        String[] parts = getResources().getStringArray(R.array.start_part);
        String[] words = getResources().getStringArray(R.array.end_part);
        String end = words[((int) (words.length * Math.random()))];
        ArrayList<String> nominatedParts = new ArrayList<>(Arrays.asList(parts));
        StringBuilder word = new StringBuilder();

        int partSize = (int) (Math.sqrt(Math.random()) * 4);
        for (int i = 0; i < partSize; i++)
            word.append(nominatedParts.remove((int) (Math.random() * nominatedParts.size())));

        word.append(end);
        return word.toString();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public void addItem(View view) {
        switch (view.getId()) {
            case R.id.a:
                list.setSelection(adapter.indexOfId(adapter.add(ItemA.class, (int) (Math.random() * 5000))));
                break;
            case R.id.b:
                list.setSelection(adapter.indexOfId(adapter.add(ItemB.class, generateSentence())));
                break;
            case R.id.c:
                list.setSelection(adapter.indexOfId(adapter.add(ItemC.class, generateWord())));
                break;
        }
    }

    public void openGithub(MenuItem item) {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/cab404/Chumroll")));
    }
}
