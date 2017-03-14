package com.cab404.chumroll.item;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cab404.chumroll.ChumrollAdapter;
import com.cab404.chumroll.ViewConverter;
import com.cab404.chumroll.example.R;

/**
 * Just some number info
 * <p>
 * Created at 05:33 on 09/03/16
 *
 * @author cab404
 */
public class NumberItem implements ViewConverter<Integer> {
    @Override
    public void convert(View view, Integer data, int index, ViewGroup parent, final ChumrollAdapter adapter) {
        ((TextView) view.findViewById(R.id.dec)).setText(Integer.toString(data));
        ((TextView) view.findViewById(R.id.hex)).setText("0x" + Integer.toHexString(data).toUpperCase());
        ((TextView) view.findViewById(R.id.bin)).setText(Integer.toBinaryString(data) + "b");

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
        return inflater.inflate(R.layout.item_a, parent, false);
    }

    @Override
    public boolean enabled(Integer data, int index, ChumrollAdapter adapter) {
        return true;
    }
}
