package com.android.capture.hooks;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import java.lang.reflect.Method;
import java.util.List;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

import static de.robv.android.xposed.XposedBridge.log;
import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;

/**
 * Created by Berkeley on 9/6/16.
 */
public class JiKe {
    public static final String PACKAGE_NAME = "com.ruguoapp.jike";
    private static Context appContext = null;
    //    RequestQueue mQueue;
    private int j;


    public static void init(final XC_LoadPackage.LoadPackageParam lPParam) {
        if (!lPParam.packageName.equals(PACKAGE_NAME))
            return;

        log("handleLoadPackage ===== " + lPParam.packageName);

        findAndHookMethod("com.ruguoapp.jike.ui.activity.MainActivity", lPParam.classLoader, "onCreate", Bundle.class, new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                // this will be called after the clock was updated by the original method
                if (appContext != null)
                    return;
                appContext = ((Activity) param.thisObject).getApplicationContext();

                JiKe jike = new JiKe();
                jike.hookMethods(lPParam);

            }


        });
    }

    /**
     * 记录上次list的size
     */
    private void hookMethods(final XC_LoadPackage.LoadPackageParam lpparam) {

        findAndHookMethod("com.ruguoapp.jike.ui.fragment.FeedFragment", lpparam.classLoader, "onCreate", Bundle.class, new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                super.afterHookedMethod(param);
                log("com.ruguoapp.jike.ui.fragment.FeedFragment::::::::::::afterHookedMethod");


                findAndHookMethod("com.ruguoapp.jike.view.a", lpparam.classLoader, "d", List.class, new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);


                        final List listMessage = (List) param.args[0];

                        int listMessageSize = listMessage.size();
                        log("listMessage.size() ＝＝＝＝ " + listMessageSize);

                        final Class message = XposedHelpers.findClass("com.ruguoapp.jike.model.bean.MessageBean", lpparam.classLoader);
                        log("feedMessage:" + listMessage.toString());
                        for (int i = 1; i < listMessageSize; i++) {

                            Class messageObj = XposedHelpers.findClass("com.ruguoapp.jike.model.bean.feed.FeedMessageBean", lpparam.classLoader);
                            Method feedEntity = messageObj.getMethod("feedEntity");
                            Object messageBean = (Object) feedEntity.invoke(listMessage.get(i), new Object[]{});


                            Method getLinkUrl = message.getMethod("getLinkUrl");
                            String collected = (String) getLinkUrl.invoke(messageBean, new Object[]{});
                            log("collected  param ＝＝＝＝ " + collected);
                        }

                    }
                });

            }
        });

    }
}
