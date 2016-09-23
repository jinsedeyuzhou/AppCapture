package com.android.capture.hooks;

import android.app.Activity;
import android.content.Context;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

import static de.robv.android.xposed.XposedBridge.log;
import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;


/**
 * Created by Berkeley on 8/24/16.
 */
public class KuaiBaoApp {
    public static final String PACKAGE_NAME = "com.tentent.reading";
    private static Context appContext = null;
//    RequestQueue mQueue;

    public static void init(final XC_LoadPackage.LoadPackageParam lPParam) {
        if (!lPParam.packageName.equals(PACKAGE_NAME))
            return;

        log("handleLoadPackage ===== " + lPParam.packageName);

        findAndHookMethod("com.android.systemui.statusbar.policy.Clock", lPParam.classLoader, "updateClock", new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                // this will be called before the clock was updated by the original method


            }

            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                // this will be called after the clock was updated by the original method
                if (appContext != null)
                    return;
                appContext = ((Activity) param.thisObject).getApplicationContext();
                KuaiBaoApp kuaiBao = new KuaiBaoApp();
                kuaiBao.hookMethods(lPParam);

            }


        });
    }

    private void hookMethods(final XC_LoadPackage.LoadPackageParam lpparam) {


    }
}
