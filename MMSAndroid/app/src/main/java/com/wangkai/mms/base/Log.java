package com.wangkai.mms.base;

import android.content.ComponentName;
import android.util.EventLog;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import static android.content.ContentValues.TAG;
public class Log {
    public void Logead(){

        int tagCode = EventLog.getTagCode("am_proc_start");
        Collection<EventLog.Event> output = new ArrayList<EventLog.Event>();
        try {
            EventLog.readEvents(new int[] { tagCode }, output);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        for (EventLog.Event event : output) {
            // PID, UID, Process Name, Type, Component
            Object[] objects = (Object[]) event.getData();
            ComponentName componentName = ComponentName
                    .unflattenFromString(objects[4].toString());
            String packageName = componentName.getPackageName();
            Log.d(TAG, "packageName=" + packageName);
        }

    }
    private static void d(String tag, String s) {
    }

}
