package com.cab404.chumroll.proxies;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cab404.chumroll.ChumrollAdapter;

/**
 * Proxy for using ChumrollAdapter in RecyclerView
 *
 * @author cab404
 */
public class ChumrollRecyclerAdapter extends RecyclerView.Adapter<ChumrollRecyclerAdapter.ChumrollViewHolder> implements WatchingChumrollAdapter.ChumrollDataSetObserver {
    private final WatchingChumrollAdapter adapter = new WatchingChumrollAdapter();

    {
        adapter.observers.add(this);
    }

    public ChumrollAdapter getAdapter() {
        return adapter;
    }

    @Override
    public ChumrollViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        final View view = adapter.getConverterForType(viewType).createView(inflater, parent, adapter);
        return new ChumrollViewHolder(view, parent);
    }

    @Override
    public void onBindViewHolder(ChumrollViewHolder holder, int position) {
        adapter.getView(position, holder.itemView, holder.parent);
    }

    @Override
    public int getItemCount() {
        return adapter.getCount();
    }

    @Override
    public int getItemViewType(int position) {
        return adapter.getItemViewType(position);
    }

    @Override
    public long getItemId(int position) {
        return adapter.getItemId(position);
    }

    @Override
    public void onItemAdded(int index) {
        notifyItemInserted(index);
    }

    @Override
    public void onItemUpdated(int index) {
        notifyItemChanged(index);
    }

    @Override
    public void onItemRemoved(int index) {
        notifyItemRemoved(index);
    }

    @Override
    public void onItemRangeAdded(int start, int count) {
        notifyItemRangeInserted(start, count);
    }

    @Override
    public void onItemRangeRemoved(int start, int count) {
        notifyItemRangeRemoved(start, count);
    }

    @Override
    public void onItemMoved(int from, int to) {
        notifyItemMoved(from, to);
    }

    class ChumrollViewHolder extends RecyclerView.ViewHolder {
        private final ViewGroup parent;

        public ChumrollViewHolder(View itemView, ViewGroup parent) {
            super(itemView);
            this.parent = parent;
        }

    }

}
