package com.android.capture.hooks;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

import static de.robv.android.xposed.XposedBridge.log;
import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;

/**
 * Created by Berkeley on 9/6/16.
 */
public class SinaComments {

    public static final String PACKAGE_NAME = "com.sina.weibo";
    private static Context appContext = null;
    private String oneLevelPath;
    private String twoLevelPath;
    private String threeLevelPath;
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

                SinaComments jike = new SinaComments();
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
                final Class StatusClass = XposedHelpers.findClass("com.sina.weibo.models.Status", lpparam.classLoader);
                final Class TrendClass = XposedHelpers.findClass("com.sina.weibo.models.Trend", lpparam.classLoader);

                final Class StatusAnnotationsClass = XposedHelpers.findClass("com.sina.weibo.models.StatusAnnotations", lpparam.classLoader);
                final Class CommentSummaryClass = XposedHelpers.findClass("com.sina.weibo.models.CommentSummary", lpparam.classLoader);
                final Class MBlogExtendPageClass = XposedHelpers.findClass("com.sina.weibo.models.MBlogExtendPage", lpparam.classLoader);
                final Class ForwardSummaryClass = XposedHelpers.findClass("com.sina.weibo.models.ForwardSummary", lpparam.classLoader);
                final Class LongTextClass = XposedHelpers.findClass("com.sina.weibo.models.LongText", lpparam.classLoader);
                final Class ContinueTagClass = XposedHelpers.findClass("com.sina.weibo.models.ContinueTag", lpparam.classLoader);
                final Class PicInfoClass = XposedHelpers.findClass("com.sina.weibo.models.PicInfo", lpparam.classLoader);
                final Class StatusCommentClass = XposedHelpers.findClass("com.sina.weibo.models.StatusComment", lpparam.classLoader);
                final Class StatusComplaintClass = XposedHelpers.findClass("com.sina.weibo.models.StatusComplaint", lpparam.classLoader);
                final Class MBlogKeywordClass = XposedHelpers.findClass("com.sina.weibo.models.MBlogKeyword", lpparam.classLoader);
                final Class JsonButtonClass = XposedHelpers.findClass("com.sina.weibo.card.model.JsonButton", lpparam.classLoader);
                final Class MblogCardInfoClass = XposedHelpers.findClass("com.sina.weibo.card.model.MblogCardInfo", lpparam.classLoader);
                final Class MBlogTagClass = XposedHelpers.findClass("com.sina.weibo.models.MBlogTag", lpparam.classLoader);
                final Class MBlogTitleClass = XposedHelpers.findClass("com.sina.weibo.models.MBlogTitle", lpparam.classLoader);
                final Class MblogCardClass = XposedHelpers.findClass("com.sina.weibo.models.MblogCard", lpparam.classLoader);
                final Class JsonUserInfoClass = XposedHelpers.findClass("com.sina.weibo.models.JsonUserInfo", lpparam.classLoader);
                final Class MblogTopicClass = XposedHelpers.findClass("com.sina.weibo.models.MblogTopic", lpparam.classLoader);
                final Class MBlogMultiMediaClass = XposedHelpers.findClass("com.sina.weibo.models.MBlogMultiMedia", lpparam.classLoader);
                final Class VisibleClass = XposedHelpers.findClass("com.sina.weibo.models.Visible", lpparam.classLoader);
//                        final Class MBlogTitleInfoClass = XposedHelpers.findClass("com.sina.weibo.models.MBlogTitleInfo", lpparam.classLoader);
                final Class MediaDataObjectClass = XposedHelpers.findClass("com.sina.weibo.card.model.MediaDataObject", lpparam.classLoader);

                oneLevelPath="com.sina.weibo.feed.DetailWeiboActivity";
                findAndHookMethod(oneLevelPath, lpparam.classLoader, "onCreate", Bundle.class,new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
//                        final  List listMessage= (List) param.args[0];
                        log(oneLevelPath+":::::::");
                        final Class JsonCommentClass = XposedHelpers.findClass("com.sina.weibo.models.JsonComment", lpparam.classLoader);

                        twoLevelPath="com.sina.weibo.feed.detail.DetailWeiboView";
                        findAndHookMethod(twoLevelPath, lpparam.classLoader, "a",JsonCommentClass, new XC_MethodHook() {
                            @Override
                            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                                super.afterHookedMethod(param);
                                log(twoLevelPath+"=======a");
                                Object arg = param.args[0];
                                log(twoLevelPath+"====="+arg);


                            }
                        });







                    }
                });


            }
        });

    }


}
