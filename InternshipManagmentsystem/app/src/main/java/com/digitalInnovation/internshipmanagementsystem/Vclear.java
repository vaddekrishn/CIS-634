package com.digitalInnovation.internshipmanagementsystem;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class Vclear {
    public static void cclear(Context context){
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.getCache().clear();
    }


}
