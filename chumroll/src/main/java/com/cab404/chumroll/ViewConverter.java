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
     * @param view    View to modify. Guaranteed to be non-null and to be
     *                created in {@linkplain #createView} method of this class.
     * @param data    Data to use while modifying view.
     * @param index   Index of view in list.
     * @param parent  Parent list view, rarely used, but still :/
     * @param adapter Adapter in which this converter resides
     */
    void convert(View view, From data, int index, ViewGroup parent, ChumrollAdapter adapter);

    /**
     * Creates a new empty view for data.
     */
    View createView(LayoutInflater inflater, ViewGroup parent, ChumrollAdapter adapter);

    /**
     * If item is enabled in AbsListView, see
     * {@link android.widget.ListAdapter#isEnabled(int) isEnabled}
     * in ListAdapter.
     *
     * @param data    Data used to modify view.
     * @param index   Index of view in list.
     * @param adapter Adapter in which this converter resides
     */
    boolean enabled(From data, int index, ChumrollAdapter adapter);
}
