package com.cab404.chumroll.viewbinder;

/**
 * Created at 15:59 on 14/03/17
 *
 * @author cab404
 */
public interface DataBindContext<Item> extends BindContext {

    int getIndex();

    Item getData();

}
