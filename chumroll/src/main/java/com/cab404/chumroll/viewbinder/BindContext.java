package com.cab404.chumroll.viewbinder;

import android.view.View;
import android.view.ViewParent;

import com.cab404.chumroll.ChumrollAdapter;

/**
 * Information about adapter and target view. Used for ViewBinder initialization.
 *
 * @author cab404
 */
public interface BindContext {

    View getView();

    ViewParent getParent();

    ChumrollAdapter getAdapter();

}
