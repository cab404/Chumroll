package com.cab404.chumroll;

import android.database.DataSetObserver;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Dynamic adapter, but named Chumroll because of reasons
 * Supports adding multiple types of views.
 * <br/>
 * And yup, it manages itself.
 * <br/>
 * <b>ACHTUNG/WARNING</b>:
 * Well, besides it's MAGICAL ability to create BEAUTIFUL and GORGEOUS views,
 * caches are still on ListView, or wherever you've plugged this WONDERFUL adapter.
 * So if you are adding views of some new TYPE, it's better to first UNPLUG adapter
 * from view, usually by passing null to {@link android.widget.AbsListView#setAdapter setAdapter},
 * then ADD and then PLUG it back.
 * <br/>
 * Or simply add data types with {@link #prepareFor(ViewConverter[])} before initial
 * call to setAdapter(), then you should not experience any problems adding views.
 * <br/>
 * Actually, I don't know, why do I've added automatic creation of converters using reflection, yet
 * it saves two or three lines of code occasionally.
 * <br/>
 * Generally you can't modify it's data outside main thread,
 * yet if you really, like REALLY want - create a thread that
 * returns {@code true} in {@code equals()} with {@code Looper.getMainLooper().getThread()}
 *
 * @author cab404
 */
public class ChumrollAdapter extends BaseAdapter {

    /**
     * Contains list of types of ViewConverters present in this adapter.
     */
    protected List<ViewConverter> usedConverters;

    /**
     * Contains instances of ViewConverters
     */
    protected ConverterPool converters;

    /**
     * Data set of this adapter
     */
    protected List<ViewBinder> list;

    /**
     * Cached layout inflater
     */
    protected LayoutInflater inf;

    {
        usedConverters = new ArrayList<>();
        converters = new ConverterPool();
        list = new ArrayList<>();
    }

    /**
     * Returns converter pool used by this adapter;
     */
    public ConverterPool getConverters() {
        return converters;
    }

    /**
     * Simple entry POJO.
     */
    protected class ViewBinder<From> {
        ViewConverter<? extends From> converter;
        From data;
        final int id;

        public ViewBinder(ViewConverter<? extends From> converter, From data) {
            this.converter = converter;
            this.data = data;
            final int rnd = (int) (Math.random() * Integer.MAX_VALUE);
            this.id = converter.hashCode() ^ rnd;
        }

    }

    AtomicInteger observers = new AtomicInteger(0);

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {
        if (observer == null) return;
        observers.incrementAndGet();
        super.registerDataSetObserver(observer);
    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {
        observers.decrementAndGet();
        super.unregisterDataSetObserver(observer);
    }

    void throwIfIllegal() {
        if (observers.get() == 0)
            return;
        if (!Thread.currentThread().equals(Looper.getMainLooper().getThread()))
            throw new UnsupportedOperationException("Operation should be done outside main thread!");
    }

    /**
     * Returns first index in adapter, which has given data.
     */
    public int indexOf(Object data) {
        for (int i = 0; i < list.size(); i++)
            if (list.get(i).data == data)
                return i;
        return -1;
    }

    /**
     * Adds new entry into adapter.
     */
    @SuppressWarnings("unchecked")
    public <Data> int add(ViewConverter<Data> instance, Data data) {
        throwIfIllegal();
        if (!usedConverters.contains(instance))
            usedConverters.add(instance);
        final ViewBinder<Data> binder = new ViewBinder<>(instance, data);
        list.add(binder);
        notifyDataSetChanged();
        return binder.id;
    }

    /**
     * Adds new entry into adapter.
     */
    @SuppressWarnings("unchecked")
    public <Data> int add(int index, ViewConverter<Data> instance, Data data) {
        throwIfIllegal();
        if (!usedConverters.contains(instance))
            usedConverters.add(instance);
        final ViewBinder<Data> binder = new ViewBinder<>(instance, data);
        list.add(index, binder);
        notifyDataSetChanged();
        return binder.id;
    }

    /**
     * Adds new entry into adapter.
     */
    @SuppressWarnings("unchecked")
    public <Data> void addAll(ViewConverter<Data> instance, Collection<? extends Data> data_set) {
        throwIfIllegal();
        if (!usedConverters.contains(instance))
            usedConverters.add(instance);
        for (Data data : data_set)
            list.add(new ViewBinder<>(instance, data));
        notifyDataSetChanged();
    }

    public void removeById(int id) {
        throwIfIllegal();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).id == id) {
                list.remove(i);
                notifyDataSetChanged();
                break;
            }
        }
    }

    public int indexOfId(int id) {
        for (int i = 0; i < list.size(); i++)
            if (list.get(i).id == id)
                return i;
        return -1;
    }

    public int idOf(int index) {
        return list.get(index).id;
    }

    /**
     * Adds new entry into adapter.
     */
    @SuppressWarnings("unchecked")
    public <Data> int add(Class<? extends ViewConverter<Data>> converter, Data data) {
        throwIfIllegal();
        ViewConverter<Data> instance = (ViewConverter<Data>) converters.getInstance(converter);
        if (!usedConverters.contains(instance))
            usedConverters.add(instance);
        final ViewBinder<Data> binder = new ViewBinder<>(instance, data);
        list.add(binder);
        return binder.id;
    }


    /**
     * Adds new entry into adapter.
     */
    @SuppressWarnings("unchecked")
    public <Data> int add(int index, Class<? extends ViewConverter<Data>> converter, Data data) {
        throwIfIllegal();
        ViewConverter<Data> instance = (ViewConverter<Data>) converters.getInstance(converter);
        if (!usedConverters.contains(instance))
            usedConverters.add(instance);
        final ViewBinder<Data> binder = new ViewBinder<>(instance, data);
        list.add(index, binder);
        return binder.id;
    }


    /**
     * Adds new entry into adapter.
     */
    @SuppressWarnings("unchecked")
    public <Data> void addAll(Class<? extends ViewConverter<Data>> converter, Collection<? extends Data> data_set) {
        throwIfIllegal();
        ViewConverter<Data> instance = (ViewConverter<Data>) converters.getInstance(converter);
        if (!usedConverters.contains(instance))
            usedConverters.add(instance);
        for (Data data : data_set)
            list.add(new ViewBinder<>(instance, data));
        notifyDataSetChanged();
    }

    public Object getData(int at) {
        return list.get(at).data;
    }

    /**
     * Removes everything from adapter.
     */
    public void clear() {
        list.clear();
        usedConverters.clear();
        notifyDataSetChanged();
    }

    /**
     * Removes entry at given index
     */
    public void remove(int at) {
        throwIfIllegal();
        list.remove(at);
    }

    /**
     * Removes first occurrence of entry with given data.
     */
    public <Data> void remove(Data what) {
        remove(indexOf(what));
    }

    public int typeIdOf(ViewConverter converter) {
        return usedConverters.indexOf(converter);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public int getViewTypeCount() {
        return usedConverters.size();
    }

    @Override
    public int getItemViewType(int position) {
        ViewConverter converter = list.get(position).converter;
        return usedConverters.indexOf(converter);
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean isEnabled(int position) {
        ViewBinder binder = list.get(position);
        return binder.converter.enabled(binder.data, position);
    }

    @SuppressWarnings("unchecked")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewBinder binder = list.get(position);

        if (convertView == null) {

            if (inf == null || inf.getContext() != parent.getContext())
                inf = LayoutInflater.from(parent.getContext());

            convertView = binder.converter.createView(parent, inf);
        }

        binder.converter.convert(convertView, binder.data, position, parent);

        return convertView;
    }

    /**
     * You can stuff a bunch of converters inside right here.
     */
    public void prepareFor(ViewConverter... prepare_to_what) {
        for (ViewConverter thing : prepare_to_what) {
            converters.enforceInstance(thing);
            usedConverters.add(thing);
        }
    }

}

