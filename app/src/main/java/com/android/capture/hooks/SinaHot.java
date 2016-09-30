package com.android.capture.hooks;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import java.util.List;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

import static de.robv.android.xposed.XposedBridge.log;
import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;

/**
 * Created by Berkeley on 9/6/16.
 */
public class SinaHot {
    public static final String PACKAGE_NAME = "com.sina.weibo";
    private static Context appContext = null;
    //    RequestQueue mQueue;


    public static void init(final XC_LoadPackage.LoadPackageParam lPParam) {
        if (!lPParam.packageName.equals(PACKAGE_NAME))
            return;

        log("handleLoadPackage ===== " + lPParam.packageName);

        findAndHookMethod("com.sina.weibo.MainTabActivity", lPParam.classLoader, "onCreate", Bundle.class, new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                // this will be called after the clock was updated by the original method
                if (appContext != null)
                    return;
                appContext = ((Activity) param.thisObject).getApplicationContext();

                SinaHot jike = new SinaHot();
                jike.hookMethods(lPParam);

            }


        });
    }

    /**
     * 记录上次list的size
     */
    private void hookMethods(final XC_LoadPackage.LoadPackageParam lpparam) {

        findAndHookMethod("com.sina.weibo.page.DiscoverActivity", lpparam.classLoader, "onCreate", Bundle.class, new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                super.afterHookedMethod(param);
                log("com.sina.weibo.page.DiscoverActivity::::::::::::DiscoverActivity");
                final Class JsonUserInfoClass = XposedHelpers.findClass("com.sina.weibo.models.JsonUserInfo", lpparam.classLoader);
                final Class PageCardInfoClass = XposedHelpers.findClass("com.sina.weibo.card.model.PageCardInfo", lpparam.classLoader);

                findAndHookMethod("com.sina.weibo.page.a", lpparam.classLoader, "a", List.class, int.class, boolean.class, boolean.class, new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);


                        final List pageCardLists = (List) param.args[0];
                        log("com.sina.weibo.page.a===a___counts:" + pageCardLists.size() + "lists:=====" + pageCardLists);
//                        for (int i = 0; i < pageCardLists.size(); i++) {
//                            Method getCardTypeName = PageCardInfoClass.getMethod("getCardTypeName");
//                            String cardTypeName = (String) getCardTypeName.invoke(pageCardLists.get(i),new Object());
//                            log("getCardTypeName=========" + cardTypeName);
//
//                            Method getFlag_pic = PageCardInfoClass.getMethod("getFlag_pic");
//                            String flag_pic = (String) getFlag_pic.invoke(pageCardLists.get(i),new Object());
//                            log("getFlag_pic=========" + flag_pic);
//
//                            Method getHighlightState = PageCardInfoClass.getMethod("getHighlightState");
//                            String highlightState = (String) getHighlightState.invoke(pageCardLists.get(i),new Object());
//                            log("getHighlightState=========" + highlightState);
//
//
//                            Method getMultimediaActionlog = PageCardInfoClass.getMethod("getMultimediaActionlog");
//                            String multimediaActionlog = (String) getMultimediaActionlog.invoke(pageCardLists.get(i),new Object());
//                            log("getMultimediaActionlog=========" + multimediaActionlog);
//
//                            Method getOpenUrl = PageCardInfoClass.getMethod("getOpenUrl");
//                            String openUrl = (String) getOpenUrl.invoke(pageCardLists.get(i),new Object());
//                            log("getOpenUrl=========" + openUrl);
//
//                            Method getScheme = PageCardInfoClass.getMethod("getScheme");
//                            String scheme = (String) getScheme.invoke(pageCardLists.get(i),new Object());
//                            log("getScheme=========" + scheme);
//
//
//                            Method getCardTitle = PageCardInfoClass.getMethod("getCardTitle");
//                            String cardTitle = (String) getCardTitle.invoke(pageCardLists.get(i),new Object());
//                            log("getCardTitle=========" + cardTitle);
//
//
//                            Method getTitle_extra_text = PageCardInfoClass.getMethod("getTitle_extra_text");
//                            String title_extra_text = (String) getTitle_extra_text.invoke(pageCardLists.get(i),new Object());
//                            log("getTitle_extra_text=========" + title_extra_text);
//
//
//                            Method getTitle_flag_pic = PageCardInfoClass.getMethod("getTitle_flag_pic");
//                            String title_flag_pic = (String) getTitle_flag_pic.invoke(pageCardLists.get(i),new Object());
//                            log("getTitle_flag_pic=========" + title_flag_pic);
//
//
//
//
//
//
//                        }

                    }
                });


            }
        });

    }


}
