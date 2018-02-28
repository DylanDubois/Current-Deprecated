package com.current.android.current;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import java.util.zip.Inflater;

/**
 * Created by duboi on 2/27/2018.
 */

public class EventPopupWindow extends PopupWindow{

    private Inflater inflater;
    private View customView;
    private Context context;
    private Activity activity;
    private RelativeLayout layout;
    public EventPopupWindow (Context context, Activity activity, RelativeLayout layout){
        super();
        this.context = context;
        this.activity = activity;
        this.layout = layout;
    }
}
