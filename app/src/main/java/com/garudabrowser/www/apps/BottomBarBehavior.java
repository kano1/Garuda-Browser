package com.garudabrowser.www.apps;

import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by Nopal on 27/09/2017.
 */

public class BottomBarBehavior extends CoordinatorLayout.Behavior<LinearLayout> {

    private int defaultDependencyTop = -1;

    public BottomBarBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, LinearLayout child, View dependency) {
        return dependency instanceof AppBarLayout;
    }


    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, LinearLayout child, View dependency) {
        if (defaultDependencyTop == -1) {
            defaultDependencyTop = dependency.getTop();
        }
        child.setTranslationY(-dependency.getTop() + defaultDependencyTop);
        return true;
    }

}
