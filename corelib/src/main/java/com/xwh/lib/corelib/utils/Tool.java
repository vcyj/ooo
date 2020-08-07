package com.xwh.lib.corelib.utils;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;


public class Tool {

    public void ListIsEmpty(ArrayList list) {
    }

    public static void toEmail(Activity activity, String email, String emailName) {
        Intent data = new Intent(Intent.ACTION_SENDTO);
        data.setData(Uri.parse("mailto:" + email));
        data.putExtra(Intent.EXTRA_SUBJECT, emailName);
        data.putExtra(Intent.EXTRA_TEXT, " ");
        activity.startActivity(data);

/*        Intent data = new Intent(Intent.ACTION_SENDTO);
        data.setData(Uri.parse("mailto:way.ping.li@gmail.com"));
        data.putExtra(Intent.EXTRA_SUBJECT, "这是标题");
        data.putExtra(Intent.EXTRA_TEXT, "这是内容");
        startActivity(data);*/
    }
}
