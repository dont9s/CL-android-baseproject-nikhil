package com.skeleton.fcm;

import java.util.Map;

public interface OnPushReceived {
        void onPush(Map<String, String> data);
    }
