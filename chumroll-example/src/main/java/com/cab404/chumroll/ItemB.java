package com.cab404.chumroll;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cab404.chumroll.example.R;

/**
 * Well, sorry for no comments here!
 * Still you can send me your question to me@cab404.ru!
 * <p/>
 * Created at 07:55 on 09/03/16
 *
 * @author cab404
 */
public class ItemB implements ViewConverter<Sentence> {
    @Override
    public void convert(View view, Sentence data, int index, ViewGroup parent, final ChumrollAdapter adapter) {
        ((TextView) view.findViewById(R.id.word)).setText(data.word);
        ((TextView) view.findViewById(R.id.prefix)).setText(data.prefix);

        final int id = adapter.idOf(index);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.removeById(id);
            }
        });
    }

    @Override
    public View createView(LayoutInflater inflater, ViewGroup parent, ChumrollAdapter adapter) {
        return inflater.inflate(R.layout.item_b, parent, false);
    }

    @Override
    public boolean enabled(Sentence data, int index, ChumrollAdapter adapter) {
        return true;
    }
}
