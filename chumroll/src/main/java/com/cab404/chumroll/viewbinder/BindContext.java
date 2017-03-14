package com.cab404.chumroll.viewbinder;

import android.view.View;
import android.view.ViewParent;

import com.cab404.chumroll.ChumrollAdapter;

/**
 * Created at 15:59 on 14/03/17
 *
 * @author cab404
 */
public class BindContext<Item> {
    int index;
    View view;
    Item data;
    ViewParent parent;
    ChumrollAdapter adapter;

    public int getIndex() {
        return index;
    }

    public View getView() {
        return view;
    }

    public Item getData() {
        return data;
    }

    public ViewParent getParent() {
        return parent;
    }

    public ChumrollAdapter getAdapter() {
        return adapter;
    }
}
