package com.mdsolutions.dimitar.studenthub;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by martin on 3/10/16.
 */
public class AndroidUtils
{
    private static Toast toast;
    public static void createToast(String text, Context context)
    {
        if(toast != null)
        {
            toast.cancel();
        }

        toast = Toast.makeText(context, text, Toast.LENGTH_LONG);
        toast.show();
    }
}
