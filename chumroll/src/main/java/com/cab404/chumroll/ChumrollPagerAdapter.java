package com.cab404.chumroll;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Wrapper around {@link ChumrollAdapter} made for use in PagerViews.
 *
 * @author cab404
 */
public class ChumrollPagerAdapter extends PagerAdapter {

    private final ChumrollAdapter adapter;

    public ChumrollPagerAdapter(ChumrollAdapter adapter) {
        this.adapter = adapter;
    }

    /**
     * Returns underlying ChumrollAdapter
     */
    public ChumrollAdapter getNestedAdapter() {
        return adapter;
    }

    public <T> ChumrollPagerAdapter(Class<? extends ViewConverter<T>> converter, List<T> data) {
        adapter = new ChumrollAdapter();
        adapter.addAll(converter, data);
    }

    @Override
    public float getPageWidth(int position) {
        return 1f;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = adapter.getView(position, null, container);

        view.getLayoutParams().width = (int) getPageWidth(position);
        view.getLayoutParams().height = container.getHeight();

        container.addView(view, position > container.getChildCount() ? container.getChildCount() : position);

        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return adapter.getCount();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
