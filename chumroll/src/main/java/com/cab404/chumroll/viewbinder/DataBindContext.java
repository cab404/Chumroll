package com.cab404.chumroll.viewbinder;

/**
 *
 * BindContext, but with data to add into view
 * Created at 15:59 on 14/03/17
 *
 * @author cab404
 */
public interface DataBindContext<Item> extends BindContext {

    int getIndex();

    Item getData();

}
