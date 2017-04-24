package com.cab404.chumroll.example;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cab404.chumroll.ChumrollAdapter;
import com.cab404.chumroll.proxies.ChumrollRecyclerAdapter;

/**
 * RecyclerView test fragment
 *
 * @author cab404
 */
public class RecyclerTestFragment extends ChumrollTestFragment {

    ChumrollRecyclerAdapter proxy = new ChumrollRecyclerAdapter();
    ChumrollAdapter adapter = proxy.getAdapter();

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recycler, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler);
        final DisplayMetrics metrics = getResources().getDisplayMetrics();
        recyclerView.setLayoutManager(
                new StaggeredGridLayoutManager(
                        (int) Math.floor(metrics.widthPixels / (metrics.density * 180)),
                        StaggeredGridLayoutManager.VERTICAL
                )
        );
        recyclerView.setAdapter(proxy);
    }

    @Override
    public ChumrollAdapter getAdapter() {
        return adapter;
    }
}
