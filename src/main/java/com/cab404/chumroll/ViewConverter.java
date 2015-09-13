package com.cab404.chumroll;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Converts data to views.
 * Generic type is type of data this converter handles.
 *
 * @author cab404
 */
public interface ViewConverter<From> {

    /**
     * Modifies given view according to data.
     *
     * @param view   View to modify. Guaranteed to be non-null and to be
     *               created in {@linkplain #createView} method of this class.
     * @param data   Data to use while modifying view.
     * @param index  Index of view in list.
     * @param parent Parent list view, rarely used, but still :/
     */
    void convert(View view, From data, int index, ViewGroup parent);

    /**
     * Creates a new empty view for data.
     */
    View createView(ViewGroup parent, LayoutInflater inflater);

    /**
     * If item is enabled in AbsListView, see
     * {@link android.widget.ListAdapter#isEnabled(int) isEnabled}
     * in ListAdapter.
     *
     * @param index Index of view in list.
     * @param data  Data used to modify view.
     */
    boolean enabled(From data, int index);
}
