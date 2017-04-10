package com.cab404.chumroll.viewbinder;

import android.view.View;
import android.view.ViewParent;

import com.cab404.chumroll.ChumrollAdapter;

/**
 * @author cab404
 */
class BindContextImpl<Item> implements DataBindContext<Item> {
    int index;
    View view;
    Item data;
    ViewParent parent;
    ChumrollAdapter adapter;

    @Override
    public int getIndex() {
        return index;
    }

    @Override
    public View getView() {
        return view;
    }

    @Override
    public Item getData() {
        return data;
    }

    @Override
    public ViewParent getParent() {
        return parent;
    }

    @Override
    public ChumrollAdapter getAdapter() {
        return adapter;
    }

}

