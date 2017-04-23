package com.cab404.chumroll.example;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.cab404.chumroll.ChumrollAdapter;

/**
 * Created at 12:09 on 22/04/17
 *
 * @author cab404
 */
public class ListTestFragment extends ChumrollTestFragment {

    ChumrollAdapter adapter = new ChumrollAdapter();

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((ListView) view.findViewById(R.id.list)).setAdapter(adapter);
    }

    @Override
    public ChumrollAdapter getAdapter() {
        return adapter;
    }
}
