package com.cab404.chumroll.example.item;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cab404.chumroll.ChumrollAdapter;
import com.cab404.chumroll.ViewConverter;
import com.cab404.chumroll.example.R;
import com.cab404.chumroll.proxies.ChumrollPagerAdapter;
import com.cab404.chumroll.proxies.PagerMetaProvider;

/**
 * Label item
 *
 * Created at 14:50 on 09/03/16
 *
 * @author cab404
 */
public class LabelItem implements ViewConverter<String>, PagerMetaProvider {
    @Override
    public void convert(View view, String data, int index, ViewGroup parent, final ChumrollAdapter adapter) {
        final int id = adapter.idOf(index);
        ((TextView) view.findViewById(R.id.title)).setText(data);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.removeById(id);
            }
        });
    }

    @Override
    public View createView(LayoutInflater inflater, ViewGroup parent, ChumrollAdapter adapter) {
        return inflater.inflate(R.layout.item_c, parent, false);
    }

    @Override
    public boolean enabled(String data, int index, ChumrollAdapter adapter) {
        return true;
    }

    @Override
    public float getViewPagerPageWidth(ChumrollPagerAdapter adapter, int position) {
        return 0.7f;
    }

    @Override
    public String getViewPagerPageTitle(ChumrollPagerAdapter adapter, int position) {
        return ((String) adapter.getAdapter().getItem(position));
    }
}
