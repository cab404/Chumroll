package com.cab404.chumroll.viewbinder;

import android.view.View;
import android.view.ViewGroup;

import com.cab404.chumroll.ChumrollAdapter;
import com.cab404.chumroll.ViewConverter;

/**
 * Helper for ViewBinder pattern. A lot sweeter with Kotlin's `apply`
 *
 * @author cab404
 */
public abstract class ViewBinderItem<Item> implements ViewConverter<Item> {

    private final static int BINDER_TAG = 91550361;

    protected abstract ViewBinder<Item> getBinder(BindContext context);

    private BindContextImpl<Item> context = new BindContextImpl<>();

    @SuppressWarnings("unchecked")
    @Override
    public void convert(View view, Item data, int index, ViewGroup parent, ChumrollAdapter adapter) {
        context.adapter = adapter;
        context.parent = parent;
        context.index = index;
        context.view = view;
        context.data = data;
        ViewBinder<Item> binder = (ViewBinder<Item>) view.getTag(BINDER_TAG);

        if (binder == null) {
            binder = getBinder(context);
            view.setTag(BINDER_TAG, binder);
        }

        binder.reuse(context);

    }

}
