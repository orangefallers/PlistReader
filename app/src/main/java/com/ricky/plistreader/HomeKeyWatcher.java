package com.ricky.plistreader;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

public class HomeKeyWatcher {
	
	static final String TAG = "HomeWatcher";  
    private Context mContext;  
    private IntentFilter mFilter;  
    private OnHomePressedListener mListener;  
    private InnerRecevier mRecevier;  
  
    public interface OnHomePressedListener {  
        public void onHomePressed();  
        public void onHomeLongPressed();  
    }  
      
    public  HomeKeyWatcher(Context context) {  
        mContext = context;  
        mRecevier = new InnerRecevier();  
        mFilter = new IntentFilter(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);  
    }  
  
  
    public void setOnHomePressedListener(OnHomePressedListener listener) {  
        mListener = listener;  
    }  
  
   
    public void startWatch() {  
        if (mRecevier != null) {  
            mContext.registerReceiver(mRecevier, mFilter);  
        }  
    }  
  
    
    public void stopWatch() {  
        if (mRecevier != null) {  
            mContext.unregisterReceiver(mRecevier);  
        }  
    }  
    
    class InnerRecevier extends BroadcastReceiver {  
        final String SYSTEM_DIALOG_REASON_KEY = "reason";  
        final String SYSTEM_DIALOG_REASON_GLOBAL_ACTIONS = "globalactions";  
        final String SYSTEM_DIALOG_REASON_RECENT_APPS = "recentapps";  
        final String SYSTEM_DIALOG_REASON_HOME_KEY = "homekey";  
  
        @Override  
        public void onReceive(Context context, Intent intent) {  
            String action = intent.getAction();  
            if (action.equals(Intent.ACTION_CLOSE_SYSTEM_DIALOGS)) {  
                String reason = intent.getStringExtra(SYSTEM_DIALOG_REASON_KEY);  
                if (reason != null) {  
                    Log.i(TAG, "action:" + action + ",reason:" + reason);  
                    if (mListener != null) {  
                        if (reason.equals(SYSTEM_DIALOG_REASON_HOME_KEY)) {  
                            //  short click home key
                            mListener.onHomePressed();
                        } else if (reason.equals(SYSTEM_DIALOG_REASON_RECENT_APPS)) {  
                            // long click home key
                            mListener.onHomeLongPressed();  
                        }  
                    }  
                }  
            }  
        }  
    }
    

}
