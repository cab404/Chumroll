package com.cab404.chumroll.example;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

import com.cab404.chumroll.ChumrollAdapter;
import com.cab404.chumroll.example.item.LabelItem;
import com.cab404.chumroll.example.item.NumberItem;
import com.cab404.chumroll.example.item.SentenceItem;
import com.cab404.chumroll.example.item.Util;

import java.util.Arrays;

/**
 * Common functionality for all of test fragments with Chumroll
 *
 * @author cab404
 */
public abstract class ChumrollTestFragment extends Fragment {

    abstract ChumrollAdapter getAdapter();

    /**
     * Adds item to adapter
     */
    public void addItem(int what) {
        switch (what) {
            case R.id.a:
                getAdapter().addAll(SentenceItem.class,
                        Arrays.asList(
                                Sentence.generateSentence(getContext()),
                                Sentence.generateSentence(getContext())
                        )
                );
                break;
            case R.id.b:
                getAdapter().add(NumberItem.class, Util.generateRandom());
                break;
            case R.id.c:
                getAdapter().add(LabelItem.class, Sentence.generateWord(getContext()));
                break;
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initItems();
    }

    /**
     * Initializes adapter with some items
     */
    void initItems() {
        getAdapter().prepareFor(new LabelItem(), new NumberItem(), new SentenceItem());
        for (int i = 0; i < 5; i++) {
            // Pretty rough approach, but it works since ids a, b and c are declared one after another.
            int item = (int) (R.id.a + (Math.random() * 3));
            addItem(item);
        }
    }

}
