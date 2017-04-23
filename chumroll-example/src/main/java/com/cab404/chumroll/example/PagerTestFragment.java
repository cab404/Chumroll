package com.cab404.chumroll.example;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cab404.chumroll.ChumrollAdapter;
import com.cab404.chumroll.proxies.ChumrollPagerAdapter;

/**
 * Created at 12:09 on 22/04/17
 *
 * @author cab404
 */
public class PagerTestFragment extends ChumrollTestFragment {

    ChumrollPagerAdapter proxy = new ChumrollPagerAdapter();
    ChumrollAdapter adapter = proxy.getAdapter();

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_pager, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((ViewPager) view.findViewById(R.id.pager)).setAdapter(proxy);
    }

    @Override
    public ChumrollAdapter getAdapter() {
        return adapter;
    }
}
