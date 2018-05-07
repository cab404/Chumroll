package com.cab404.chumroll.proxies;

import com.cab404.chumroll.ChumrollAdapter;
import com.cab404.chumroll.ViewConverter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Modified version of ChumrollAdapter with better event dispatching.
 * @author cab404
 */
public class WatchingChumrollAdapter extends ChumrollAdapter {


    @SuppressWarnings("unchecked")
    public <T> ViewConverter<T> getConverterForType(int type) {
        return usedConverters.get(type);
    }

    @Override
    public <Data> int addRaw(int index, ViewConverter<Data> instance, Data data) {
        final int id = super.addRaw(index, instance, data);
        onItemAdded(index);
        return id;
    }

    @Override
    public void remove(int at) {
        super.remove(at);
        onItemRemoved(at);
    }

    @Override
    public void clear() {
        int size = getCount();
        super.clear();
        onItemRangeRemoved(0, size);
    }

    @Override
    public <Data> void addAllRaw(int index, ViewConverter<Data> instance, Collection<? extends Data> data_set) {
        super.addAllRaw(getCount(), instance, data_set);
        onItemRangeAdded(index, data_set.size());
    }

    @Override
    public <Data> void update(int index, Data data) {
        super.update(index, data);
        onItemUpdated(index);
    }

    @Override
    public void move(int fromIndex, int toIndex) {
        super.move(fromIndex, toIndex);
        onItemMoved(fromIndex, toIndex);
    }

    /**
     * RecyclerView-alike observers
     */
    List<ChumrollDataSetObserver> observers;

    {
        observers = new ArrayList<>();
    }

    private void onItemAdded(int index) {
        for (ChumrollDataSetObserver observer : observers)
            observer.onItemAdded(index);
    }

    private void onItemUpdated(int index) {
        for (ChumrollDataSetObserver observer : observers)
            observer.onItemUpdated(index);
    }

    private void onItemRemoved(int index) {
        for (ChumrollDataSetObserver observer : observers)
            observer.onItemRemoved(index);
    }

    private void onItemRangeAdded(int start, int count) {
        for (ChumrollDataSetObserver observer : observers)
            observer.onItemRangeAdded(start, count);
    }

    private void onItemRangeRemoved(int start, int count) {
        for (ChumrollDataSetObserver observer : observers)
            observer.onItemRangeRemoved(start, count);
    }

    private void onItemMoved(int from, int to) {
        for (ChumrollDataSetObserver observer : observers)
            observer.onItemMoved(from, to);
    }


    public interface ChumrollDataSetObserver {
        void onItemAdded(int index);

        void onItemUpdated(int index);

        void onItemRemoved(int index);

        void onItemRangeAdded(int start, int count);

        void onItemRangeRemoved(int start, int count);

        void onItemMoved(int from, int to);
    }

}
