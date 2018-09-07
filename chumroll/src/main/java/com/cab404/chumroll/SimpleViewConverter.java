package com.cab404.chumroll;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class SimpleViewConverter<A> implements ViewConverter<A> {
    @Override
    public void convert(View view, A data, int index, ViewGroup parent, ChumrollAdapter adapter) {
    }

    @Override
    public View createView(LayoutInflater inflater, ViewGroup parent, ChumrollAdapter adapter) {
        return null;
    }

    @Override
    public boolean enabled(A data, int index, ChumrollAdapter adapter) {
        return false;
    }
}
