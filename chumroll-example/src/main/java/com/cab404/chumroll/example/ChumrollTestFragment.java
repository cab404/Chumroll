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

/**
 * Created at 15:27 on 23/04/17
 *
 * @author cab404
 */
public abstract class ChumrollTestFragment extends Fragment {

    abstract ChumrollAdapter getAdapter();

    public void addItem(int what) {
        switch (what) {
            case R.id.a:
                getAdapter().add(SentenceItem.class, Sentence.generateSentence(getContext()));
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

    void initItems(){
        getAdapter().prepareFor(new LabelItem(), new NumberItem(), new SentenceItem());
        for (int i = 0; i < 5; i++) {
            int item = (int) (R.id.a + (Math.random() * 3));
            addItem(item);
        }
    }

}
