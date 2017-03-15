package com.cab404.chumroll.viewbinder;

/**
 * Created at 15:52 on 14/03/17
 *
 * @author cab404
 */
public abstract class ViewBinder<Item> {
    protected abstract void reuse(DataBindContext<Item> context);
}
