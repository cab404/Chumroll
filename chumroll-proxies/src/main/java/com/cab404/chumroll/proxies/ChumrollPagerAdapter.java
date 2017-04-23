package com.cab404.chumroll.proxies;

import android.database.DataSetObserver;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.cab404.chumroll.ChumrollAdapter;
import com.cab404.chumroll.ViewConverter;

/**
 * Proxy for using ChumrollAdapter in ViewPager
 * <p>
 * If you want to set title and/or width to your items, see {@link PagerMetaProvider}
 *
 * @author cab404
 */
public class ChumrollPagerAdapter extends PagerAdapter {

    private ChumrollAdapter adapter;

    {
        setAdapter(new ChumrollAdapter());
    }

    public void setAdapter(ChumrollAdapter adapter) {
        this.adapter = adapter;
        adapter.addUntrackedObservable(new DataSetObserver() {
            @Override
            public void onChanged() {
                ChumrollPagerAdapter.this.notifyDataSetChanged();
            }

            @Override
            public void onInvalidated() {
                ChumrollPagerAdapter.this.notifyDataSetChanged();
            }
        });
    }


    @Override
    public float getPageWidth(int position) {
        final ViewConverter converter = adapter.getConverterOf(position);
        if (converter instanceof PagerMetaProvider)
            return ((PagerMetaProvider) converter).getViewPagerPageWidth(this, position);
        return super.getPageWidth(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        final ViewConverter converter = adapter.getConverterOf(position);
        if (converter instanceof PagerMetaProvider)
            return ((PagerMetaProvider) converter).getViewPagerPageTitle(this, position);
        return super.getPageTitle(position);
    }

    @Override
    public int getCount() {
        return adapter.getCount();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        final View view = adapter.getView(position, null, container);
        view.setTag(R.id.tag_id, adapter.idOf(position));
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(((View) object));
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public int getItemPosition(Object object) {
        int id = ((int) ((View) object).getTag(R.id.tag_id));
        int index = adapter.indexOfId(id);
        return index == -1 ? POSITION_NONE : index;
    }

    public ChumrollAdapter getAdapter() {
        return adapter;
    }

}
