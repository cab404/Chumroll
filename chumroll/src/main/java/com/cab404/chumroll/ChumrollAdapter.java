package com.cab404.chumroll;

import android.database.DataSetObserver;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Dynamic adapter, but named Chumroll because of reasons
 * Supports adding multiple types of views.
 * <p>
 * And yup, it manages itself.
 * <p>
 * <b>ACHTUNG/WARNING</b>:
 * Well, besides it's MAGICAL ability to create BEAUTIFUL and GORGEOUS views,
 * caches are still on ListView, or wherever you've plugged this WONDERFUL adapter.
 * <p>
 * So if you are adding views of some new TYPE, it's better to first UNPLUG adapter
 * from view, usually by passing null to {@link android.widget.AbsListView#setAdapter setAdapter},
 * then ADD your data and then PLUG it back.
 * <p>
 * Or simply add data types with {@link #prepareFor(ViewConverter[])} before initial
 * call to setAdapter(), then you should not experience any problems adding views.
 * <p>
 * Actually, I don't know, why do I've added automatic creation of converters using reflection, yet
 * it saves two or three lines of code occasionally.
 * <p>
 * You can't modify it's data outside main thread if you set it as adapter for a view, yet you
 * can modify whatever you want as long as it is unplugged
 *
 * @author cab404
 */
@SuppressWarnings("WeakerAccess")
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
    protected AtomicInteger observerCount = new AtomicInteger(0);

    {
        usedConverters = new ArrayList<>();
        converters = new ConverterPool();
        list = new ArrayList<>();
    }

    /**
     * @return converter pool used by this adapter;
     */
    public ConverterPool getConverters() {
        return converters;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {
        if (observer == null) return;
        observerCount.incrementAndGet();
        super.registerDataSetObserver(observer);
    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {
        observerCount.decrementAndGet();
        super.unregisterDataSetObserver(observer);
    }

    /**
     * Adds DataSetObservable without enabling thread safety alarms.
     * Useful when implementing some sort of proxy and you need to track changes of data,
     * but still want library user to have ability to add items outside of main thread while
     * adapter is detached.
     */
    public void addUntrackedObservable(DataSetObserver observer) {
        if (observer == null) return;
        super.registerDataSetObserver(observer);
    }

    protected void throwIfIllegal() {
        if (observerCount.get() == 0)
            return;

        if (Looper.myLooper() != Looper.getMainLooper())
            throw new UnsupportedOperationException(
                    "Operation on connected adapter should be done inside main thread!"
            );
    }

    /**
     * @return first index in adapter, which has given data.
     */
    public int indexOf(Object data) {
        throwIfIllegal();
        for (int i = 0; i < list.size(); i++) {
            final Object item = list.get(i).data;
            if (item == null ? data == null : item.equals(data))
                return i;
        }
        return -1;
    }

    /**
     * @return first index in adapter, which has given data and type.
     */
    public int indexOf(Class<ViewConverter> clazz, Object data) {
        throwIfIllegal();
        for (int i = 0; i < list.size(); i++) {
            final ViewBinder item = list.get(i);
            if (clazz.isInstance(item.converter) && item.data == null ? data == null : item.equals(data))
                return i;
        }
        return -1;
    }

    /**
     * Replaces entry with given index with a new one.
     */
    public <Data> int replace(int index, ViewConverter<Data> instance, Data data) {
        throwIfIllegal();
        addConverterWithCheck(instance);
        final ViewBinder<Data> binder = new ViewBinder<>(instance, data);
        list.set(index, binder);
        notifyDataSetChanged();
        return binder.id;
    }

    /**
     * Updates data at index with given index. <p>
     * Difference between {@link #replace} is that entry id won't be changed. <p>
     * Also it consumes less memory to update a bunch of entries instead of replacing them. <p>
     * Not a very safe method, though, because no type checking could be done. You'll know about your mistakes by the time ClassCastException hits you.
     */
    public <Data> void update(int index, Data data) {
        throwIfIllegal();
        list.get(index).data = data;
        notifyDataSetChanged();
    }


    /**
     * Adds new entry into adapter.
     */
    public <Data> int add(ViewConverter<Data> instance, Data data) {
        return add(getCount(), instance, data);
    }

    /**
     * Adds new entry into adapter.
     *
     * @return new item's id
     * @throws UnsupportedOperationException If is connected to a view and operation implies adding new view type.
     */
    public <Data> int add(int index, ViewConverter<Data> instance, Data data) {
        try {
            return addRaw(index, instance, data);
        } finally {
            notifyDataSetChanged();
        }
    }

    /**
     * Adds new entry into adapter, and does not invoke {@link #notifyDataSetChanged}
     *
     * @return new item's id
     * @throws UnsupportedOperationException If is connected to a view and operation implies adding new view type.
     */
    protected <Data> int addRaw(int index, ViewConverter<Data> instance, Data data) {
        throwIfIllegal();
        addConverterWithCheck(instance);
        final ViewBinder<Data> binder = new ViewBinder<>(instance, data);
        list.add(index, binder);
        return binder.id;
    }

    /**
     * Adds new entries into adapter.
     */
    @SuppressWarnings("unchecked")
    public <Data> void addAll(ViewConverter<Data> instance, Collection<? extends Data> dataSet) {
        addAll(getCount(), instance, dataSet);
    }

    /**
     * Adds new entries into adapter.
     */
    @SuppressWarnings("unchecked")
    public <Data> void addAll(int index, ViewConverter<Data> instance, Collection<? extends Data> dataSet) {
        addAllRaw(index, instance, dataSet);
        notifyDataSetChanged();
    }

    /**
     * Adds new entries into adapter without invoking {@link #notifyDataSetChanged()}, just like {@link #addRaw(int, ViewConverter, Object)}
     */
    @SuppressWarnings("unchecked")
    public <Data> void addAllRaw(int index, ViewConverter<Data> instance, Collection<? extends Data> dataSet) {
        throwIfIllegal();
        addConverterWithCheck(instance);
        final LinkedList<ViewBinder<Data>> mapped = new LinkedList<>();
        for (Data data : dataSet)
            mapped.add(new ViewBinder<>(instance, data));
        list.addAll(index, mapped);
    }

    /**
     * Removes item by its id.
     *
     * @see ChumrollAdapter#getItemId(int)
     * @see ChumrollAdapter#add(int, ViewConverter, Object)
     */
    public void removeById(int id) {
        final int index = indexOfId(id);
        if (index >= 0)
            remove(index);
    }

    /**
     * @return index in list of item with given id.
     * @see ChumrollAdapter#add(int, ViewConverter, Object)
     */
    public int indexOfId(int id) {
        for (int i = 0; i < list.size(); i++)
            if (list.get(i).id == id)
                return i;
        return -1;
    }

    /**
     * @return id of item with given index in list.
     */
    public int idOf(int index) {
        return list.get(index).id;
    }

    /**
     * Adds new entry into adapter.
     *
     * @return id of added item
     */
    @SuppressWarnings("unchecked")
    public <Data> int add(Class<? extends ViewConverter<Data>> converter, Data data) {
        ViewConverter<Data> instance = (ViewConverter<Data>) converters.getInstance(converter);
        return add(instance, data);
    }

    /**
     * Adds new entry into adapter.
     *
     * @return id of added item
     */
    @SuppressWarnings("unchecked")
    public <Data> int add(int index, Class<? extends ViewConverter<Data>> converter, Data data) {
        ViewConverter<Data> instance = (ViewConverter<Data>) converters.getInstance(converter);
        return add(index, instance, data);
    }

    /**
     * Adds new entry into adapter.
     */
    @SuppressWarnings("unchecked")
    public <Data> void addAll(Class<? extends ViewConverter<Data>> converter, Collection<? extends Data> dataSet) {
        ViewConverter<Data> instance = (ViewConverter<Data>) converters.getInstance(converter);
        addAll(instance, dataSet);
    }

    private <Data> void addConverterWithCheck(ViewConverter<Data> instance) {
        if (!usedConverters.contains(instance)) {
            if (observerCount.get() > 0) {
                throw new RuntimeException(instance.getClass().getSimpleName() + ": Cannot add new data types on fly :(");
            }
            usedConverters.add(instance);
        }
    }

    public Object getData(int at) {
        return list.get(at).data;
    }

    /**
     * Removes everything from adapter.
     */
    public void clear() {
        throwIfIllegal();
        list.clear();
        notifyDataSetChanged();
    }

    /**
     * Removes every converter from adapter. Note that data is not removed.
     */
    public void clearConverters() {
        usedConverters.clear();
    }

    /**
     * Removes entry at given index
     */
    public void remove(int at) {
        throwIfIllegal();
        list.remove(at);
        notifyDataSetChanged();
    }

    /**
     * Removes first occurrence of entry with given data.
     */
    public <Data> void remove(Data what) {
        remove(indexOf(what));
    }

    public void move(int fromIndex, int toIndex) {
        list.add(toIndex, list.remove(fromIndex));
        notifyDataSetChanged();
    }

    /**
     * @return type id of a view. Sometimes that's useful.
     */
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
        return getData(position);
    }

    @Override
    public long getItemId(int position) {
        return idOf(position);
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    /**
     * Returns class of converter for index
     */
    public Class<? extends ViewConverter> classOf(int index) {
        return list.get(index).converter.getClass();
    }

    /**
     * Returns converter for index
     */
    @SuppressWarnings("unchecked")
    public <A extends ViewConverter> A converterOf(int index) {
        return (A) list.get(index).converter;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean isEnabled(int position) {
        ViewBinder binder = list.get(position);
        return binder.converter.enabled(binder.data, position, this);
    }

    @SuppressWarnings("unchecked")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewBinder binder = list.get(position);

        if (convertView == null) {

            if (inf == null || inf.getContext() != parent.getContext())
                inf = LayoutInflater.from(parent.getContext());

            convertView = binder.converter.createView(inf, parent, this);
        }

        binder.converter.convert(convertView, binder.data, position, parent, this);

        return convertView;
    }

    /**
     * You can stuff a bunch of converters inside right here,
     * if you are planning to add data of this type to adapter after connecting it to ListView.
     */
    public void prepareFor(ViewConverter... prepareForWhat) {
        for (ViewConverter thing : prepareForWhat) {
            converters.enforceInstance(thing);
            if (observerCount.get() > 0)
                throw new RuntimeException("Cannot add new data types on fly :(");
            usedConverters.add(thing);
        }
    }

    /**
     * @return if data of item at given index is instance of given class
     */
    public boolean isInstanceOf(int index, Class dataType) {
        return dataType.isInstance(list.get(index).data);
    }

    /**
     * @return if converter of item at given index is instance of given class
     */
    public boolean converterIsInstanceOf(int index, Class convType) {
        return convType.isInstance(list.get(index).converter);
    }

    /**
     * @return converter of item at given index
     */
    public ViewConverter getConverterOf(int index) {
        return list.get(index).converter;
    }

    protected AtomicInteger nonce = new AtomicInteger();

    /**
     * Simple entry POJO.
     */
    protected class ViewBinder<From> {
        final int id;
        ViewConverter<? extends From> converter;
        From data;

        public ViewBinder(ViewConverter<? extends From> converter, From data) {
            this.converter = converter;
            this.data = data;
            this.id = nonce.getAndIncrement();
        }

    }

}

