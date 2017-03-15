package com.cab404.chumroll.example.item;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cab404.chumroll.ChumrollAdapter;
import com.cab404.chumroll.example.Sentence;
import com.cab404.chumroll.example.R;
import com.cab404.chumroll.viewbinder.BindContext;
import com.cab404.chumroll.viewbinder.ViewBinder;
import com.cab404.chumroll.viewbinder.ViewBinderItem;

/**
 * An example of ViewBinderItem
 * (I secretly h8 RecyclerViews :D)
 *
 * @author cab404
 */
public class SentenceItem extends ViewBinderItem<Sentence> {

    private class VB implements ViewBinder<Sentence> {
        TextView word;
        TextView prefix;

        @Override
        public void init(BindContext<Sentence> context) {
            word = (TextView) context.getView().findViewById(R.id.word);
            prefix = (TextView) context.getView().findViewById(R.id.prefix);
        }

        @Override
        public void reuse(final BindContext<Sentence> context) {
            word.setText(context.getData().word);
            prefix.setText(context.getData().prefix);

            final int id = context.getAdapter().idOf(context.getIndex());
            context.getView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    context.getAdapter().removeById(id);
                }
            });
        }
    }

    @Override
    protected VB getBinder() {
        return new VB();
    }

    @Override
    public View createView(LayoutInflater inflater, ViewGroup parent, ChumrollAdapter adapter) {
        return inflater.inflate(R.layout.item_b, parent, false);
    }

    @Override
    public boolean enabled(Sentence data, int index, ChumrollAdapter adapter) {
        return true;
    }
}
