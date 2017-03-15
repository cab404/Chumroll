package com.cab404.chumroll.viewbinder;

import android.view.View;
import android.view.ViewParent;

import com.cab404.chumroll.ChumrollAdapter;

/**
 * Created at 15:59 on 14/03/17
 *
 * @author cab404
 */
public interface BindContext {

    View getView();

    ViewParent getParent();

    ChumrollAdapter getAdapter();

}
