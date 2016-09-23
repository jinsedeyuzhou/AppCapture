package com.android.capture.hooks;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.SpannableStringBuilder;

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
public class WeiBo {
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

                WeiBo jike = new WeiBo();
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

                findAndHookMethod("com.sina.weibo.stream.b.d", lpparam.classLoader, "b", List.class, new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                        log("com.sina.weibo.page.view.a======a");
                        final Class StatusClass = XposedHelpers.findClass("com.sina.weibo.models.Status", lpparam.classLoader);
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

                        final List listMessage = (List) param.args[0];
                        log("com.sina.weibo.stream.b.d======b" + listMessage.size() + "Object:::::::::" + listMessage.toString());
//                        List pageCardInfo = (List) param.getResult();
                        for (int i = 0; i < listMessage.size(); i++) {

                            //信息来源，手机weibo,还是其他平台
                            Method getAnnotations = StatusClass.getMethod("getAnnotations");
                            List annotations = (List) getAnnotations.invoke(listMessage.get(i), new Object[]{});
                            log(getAnnotations.getName().toString() + ":::::::::" + annotations);
                            if (annotations != null) {
//
                                for (int j = 0; j < annotations.size(); j++) {
                                    Method getClient_mblogid = StatusAnnotationsClass.getMethod("getClient_mblogid");
                                    String client_mblogid = (String) getClient_mblogid.invoke(annotations.get(j), new Object[]{});
                                    log("getClient_mblogid:::::::::" + client_mblogid);
                                }
                            }


                            //点赞数量
                            Method getAttitudes_count = StatusClass.getMethod("getAttitudes_count");
                            int attitudes_count = (int) getAttitudes_count.invoke(listMessage.get(i), new Object[]{});
                            log("getAttitudes_count:::::::::" + attitudes_count);

                            Method getAttitudes_status = StatusClass.getMethod("getAttitudes_status");
                            int attitudes_status = (int) getAttitudes_status.invoke(listMessage.get(i), new Object[]{});
                            log("getAttitudes_status:::::::::" + attitudes_status);

                            //转发数量
                            Method getReposts_count = StatusClass.getMethod("getReposts_count");
                            int reposts_count = (int) getReposts_count.invoke(listMessage.get(i), new Object[]{});
                            log("getReposts_count:::::::::" + reposts_count);

                            //评论数量
                            Method getComments_count = StatusClass.getMethod("getComments_count");
                            int ommencts_count = (int) getComments_count.invoke(listMessage.get(i), new Object[]{});
                            log("getComments_count:::::::::" + ommencts_count);

                            //内容
                            Method getMblogContent = StatusClass.getMethod("getMblogContent");
                            SpannableStringBuilder mblogContent = (SpannableStringBuilder) getMblogContent.invoke(listMessage.get(i), new Object[]{});
                            log("getMblogContent:::::::::" + mblogContent);


                            //内容带地址
                            Method getText = StatusClass.getMethod("getText");
                            String text = (String) getText.invoke(listMessage.get(i), new Object[]{});
                            log("getText:::::::::" + text);


                            //null
                            Method getMblogSubContent = StatusClass.getMethod("getMblogSubContent");
                            SpannableStringBuilder nmblogSubContent = (SpannableStringBuilder) getMblogSubContent.invoke(listMessage.get(i), new Object[]{});
                            log("getMblogSubContent:::::::::" + nmblogSubContent);

                            //无
                            Method getMark = StatusClass.getMethod("getMark");
                            String mark = (String) getMark.invoke(listMessage.get(i), new Object[]{});
                            log("getMark:::::::::" + mark);


                            Method getPreloadInfo = StatusClass.getMethod("getPreloadInfo");
                            String preloadInfo = (String) getPreloadInfo.invoke(listMessage.get(i), new Object[]{});
                            log("getPreloadInfo:::::::::" + preloadInfo);


                            //微博距离发布时间
                            Method getTimestampText = StatusClass.getMethod("getTimestampText");
                            String timestampText = (String) getTimestampText.invoke(listMessage.get(i), new Object[]{});
                            log("getTimestampText:::::::::" + timestampText); //

                            //貌似没什么用
                            Method getCommentSummary = StatusClass.getMethod("getCommentSummary");
                            Object commentSummary = (Object) getCommentSummary.invoke(listMessage.get(i), new Object[]{});
                            log("getCommentSummary:::::::::" + commentSummary);
                            if (commentSummary != null) {
                                Method getSummaryInfo = CommentSummaryClass.getMethod("getSummaryInfo");
                                String summaryInfo = (String) getSummaryInfo.invoke(listMessage.get(i), new Object[]{});
                                log("getSummaryInfo:::::::::" + summaryInfo);

                                Method getStatusComments = CommentSummaryClass.getMethod("getStatusComments");
                                List statusComments = (List) getStatusComments.invoke(listMessage.get(i), new Object[]{});
                                log("getStatusComments:::::::::" + statusComments.size());

                            }

                            Method getCommon_struct = StatusClass.getMethod("getCommon_struct");
                            List common_structs = (List) getCommon_struct.invoke(listMessage.get(i), new Object[]{});
                            log("getCommon_struct:::::::::" + common_structs.size() + "=======" + common_structs.toString());

//                                for (int j=0;j<common_structs.size();j++) {
//                                    Method getSummaryInfo = CommentSummaryClass.getMethod("getSummaryInfo");
//                                    String summaryInfo = (String) getSummaryInfo.invoke(listMessage.get(i), new Object[]{});
//                                    log("getSummaryInfo:::::::::" + summaryInfo);
//
//                                    Method getStatusComments = CommentSummaryClass.getMethod("getStatusComments");
//                                    List statusComments = (List) getStatusComments.invoke(listMessage.get(i), new Object[]{});
//                                    log("getStatusComments:::::::::" + statusComments.size());
//
//                                }


                            Method getComplaint = StatusClass.getMethod("getComplaint");
                            String gcomplaint = (String) getComplaint.invoke(listMessage.get(i), new Object[]{});
                            log("getComplaint:::::::::" + gcomplaint);


                            Method getComplaintUrl = StatusClass.getMethod("getComplaintUrl");
                            String complaintUrl = (String) getComplaintUrl.invoke(listMessage.get(i), new Object[]{});
                            log("getComplaintUrl:::::::::" + complaintUrl);


//
//                            Method getStatusComplaint = StatusClass.getMethod("getStatusComplaint");
//                            Object statusComplaint = (Object) getStatusComplaint.invoke(listMessage.get(i), new Object[]{});
//                            log("getStatusComplaint:::::::::" + statusComplaint.toString());
//
//                            Method getShowcontent = StatusComplaintClass.getMethod("getShowcontent");
//                            String showcontent = (String) getShowcontent.invoke(statusComplaint, new Object[]{});
//                            log("getShowcontent:::::::::" + showcontent);

//
                            Method getExtraButtonInfo = StatusClass.getMethod("getExtraButtonInfo");
                            Object extraButtonInfo = (Object) getExtraButtonInfo.invoke(listMessage.get(i), new Object[]{});
                            log("getExtraButtonInfo:::::::::");

                            Method getForwardSummary = StatusClass.getMethod("getForwardSummary");
                            Object forwardSummary = (Object) getForwardSummary.invoke(listMessage.get(i), new Object[]{});
                            log("getForwardSummary:::::::::");

                            Method getGeo = StatusClass.getMethod("getGeo");
                            Object Geo = (Object) getGeo.invoke(listMessage.get(i), new Object[]{});
                            log("getGeo:::::::::");


                            Method getId = StatusClass.getMethod("getId");
                            String id = (String) getId.invoke(listMessage.get(i), new Object[]{});
                            log("getId:::::::::" + id);

                            Method getIdstr = StatusClass.getMethod("getIdstr");
                            String str = (String) getIdstr.invoke(listMessage.get(i), new Object[]{});
                            log("getIdstr:::::::::" + str);


                            Method getIn_reply_to_screen_name = StatusClass.getMethod("getIn_reply_to_screen_name");
                            String In_reply_to_screen_name = (String) getIn_reply_to_screen_name.invoke(listMessage.get(i), new Object[]{});
                            log("getIn_reply_to_screen_name:::::::::" + In_reply_to_screen_name);

                            Method getIn_reply_to_status_id = StatusClass.getMethod("getIn_reply_to_status_id");
                            String In_reply_to_status_id = (String) getIn_reply_to_status_id.invoke(listMessage.get(i), new Object[]{});
                            log("getIn_reply_to_status_id:::::::::" + In_reply_to_status_id);

                            Method getIn_reply_to_user_id = StatusClass.getMethod("getIn_reply_to_user_id");
                            String Reply_to_user_id = (String) getIn_reply_to_user_id.invoke(listMessage.get(i), new Object[]{});
                            log("getIn_reply_to_user_id:::::::::" + Reply_to_user_id);


                            Method getIsShowBulletin = StatusClass.getMethod("getIsShowBulletin");
                            int IsShowBulletin = (int) getIsShowBulletin.invoke(listMessage.get(i), new Object[]{});
                            log("getIsShowBulletin:::::::::" + IsShowBulletin);

                            Method is_controlled_by_server = StatusClass.getMethod("isControlledByServer");
                            int controlled_by_server = (int) is_controlled_by_server.invoke(listMessage.get(i), new Object[]{});
                            log("is_controlled_by_server:::::::::" + controlled_by_server);

                            Method is_disable_highlight = StatusClass.getMethod("isDisableTimeHiLight");
                            int disable_highlight = (int) is_disable_highlight.invoke(listMessage.get(i), new Object[]{});
                            log("is_disable_highlight:::::::::" + disable_highlight);


                            Method getKeyword_struct = StatusClass.getMethod("getKeyword_struct");
                            List keyword_struct = (List) getKeyword_struct.invoke(listMessage.get(i), new Object[]{});
                            log("getKeyword_struct:::::::::"+keyword_struct);

                            Method getLikeSummary = StatusClass.getMethod("getLikeSummary");
                            Object likeSummary = (Object) getLikeSummary.invoke(listMessage.get(i), new Object[]{});
                            log("getLikeSummary:::::::::" + likeSummary);


                            Method getLocalMblogId = StatusClass.getMethod("getLocalMblogId");
                            String localMblogId = (String) getLocalMblogId.invoke(listMessage.get(i), new Object[]{});
                            log("getLocalMblogId:::::::::" + localMblogId);


                            Method getLongText = StatusClass.getMethod("getLongText");
                            Object longText = (Object) getLongText.invoke(listMessage.get(i), new Object[]{});
                            log("getLongText:::::::::" + longText);


                            Method getIndex = StatusClass.getMethod("getIndex");
                            int index = (int) getIndex.invoke(listMessage.get(i), new Object[]{});
                            log("getIndex:::::::::" + index);

                            Method getMblogButtons = StatusClass.getMethod("getMblogButtons");
                            List mblogButtons = (List) getMblogButtons.invoke(listMessage.get(i), new Object[]{});
                            log("getMblogButtons:::::::::" + mblogButtons);
                            if (null != mblogButtons) {
                                for (int j = 0; j < mblogButtons.size(); j++) {
                                    Method getAfterDownLoadName = JsonButtonClass.getMethod("getAfterDownLoadName");
                                    String afterDownLoadName = (String) getAfterDownLoadName.invoke(mblogButtons.get(j), new Object[]{});
                                    log("getAfterDownLoadName:::::::::" + afterDownLoadName);

                                    Method getMonitorUrl = JsonButtonClass.getMethod("getMonitorUrl");
                                    String monitorUrl = (String) getMonitorUrl.invoke(mblogButtons.get(j), new Object[]{});
                                    log("getMonitorUrl:::::::::" + monitorUrl);


                                    Method getFollow_res_pic = JsonButtonClass.getMethod("getFollow_res_pic");
                                    String follow_res_pic = (String) getFollow_res_pic.invoke(mblogButtons.get(j), new Object[]{});
                                    log("getFollow_res_pic:::::::::" + follow_res_pic);
                                }
                            }

                            Method getCommentList = StatusClass.getMethod("getCommentList");
                            List commentLists = (List) getCommentList.invoke(listMessage.get(i), new Object[]{});
                            log("getCommentList:::::::::" + commentLists);

                            Method getMblogMenus = StatusClass.getMethod("getMblogMenus");
                            List mblogMenus = (List) getMblogMenus.invoke(listMessage.get(i), new Object[]{});
                            log("getMblogMenus:::::::::" + mblogMenus);

                            Method getSummaryType = StatusClass.getMethod("getSummaryType");
                            int summaryType = (int) getSummaryType.invoke(listMessage.get(i), new Object[]{});
                            log("getSummaryType:::::::::" + summaryType);

                            Method getMblogType = StatusClass.getMethod("getMblogType");
                            int mblogType = (int) getMblogType.invoke(listMessage.get(i), new Object[]{});
                            log("getMblogType:::::::::" + mblogType);

                            Method getMblogTypeName = StatusClass.getMethod("getMblogTypeName");
                            String mblogTypeName = (String) getMblogTypeName.invoke(listMessage.get(i), new Object[]{});
                            log("getMblogTypeName:::::::::" + mblogTypeName);

                            Method getMlevel = StatusClass.getMethod("getMlevel");
                            int mlevel = (int) getMlevel.invoke(listMessage.get(i), new Object[]{});
                            log("getMlevel:::::::::" + mlevel);


                            Method getMultiMedia = StatusClass.getMethod("getMultiMedia");
                            List multiMedia = (List) getMultiMedia.invoke(listMessage.get(i), new Object[]{});
                            log("getMultiMedia:::::::::"+multiMedia);

                            Method getCardInfo = StatusClass.getMethod("getCardInfo");
                            Object cardInfo = (Object) getCardInfo.invoke(listMessage.get(i), new Object[]{});
                            log("getCardInfo:::::::::" + cardInfo);
                            if (null!=cardInfo)
                            {
                                Method  getPeople_desc = MblogCardInfoClass.getMethod("getPeople_desc");
                                String  people_desc = (String)  getPeople_desc.invoke(cardInfo, new Object[]{});
                                log(" getPeople_desc:::::::::" + people_desc);

                            }

                            Method getPhotoTag = StatusClass.getMethod("getPhotoTag");
                            List pshotoTag = (List) getPhotoTag.invoke(listMessage.get(i), new Object[]{});
                            log("getPhotoTag:::::::::"+pshotoTag);



                            Method getPicBg = StatusClass.getMethod("getPicBg");
                            String picBg = (String) getPicBg.invoke(listMessage.get(i), new Object[]{});
                            log("getPicBg:::::::::" + picBg);


                            Method getPic_bg_scheme = StatusClass.getMethod("getPic_bg_scheme");
                            String pic_bg_scheme = (String) getPic_bg_scheme.invoke(listMessage.get(i), new Object[]{});
                            log("getPic_bg_scheme:::::::::" + pic_bg_scheme);

                            Method getPic_bg_text = StatusClass.getMethod("getPic_bg_text");
                            String pic_bg_text = (String) getPic_bg_text.invoke(listMessage.get(i), new Object[]{});
                            log("getPic_bg_text:::::::::" + pic_bg_text);

                            Method getPicBgType = StatusClass.getMethod("getPicBgType");
                            int picBgType = (int) getPicBgType.invoke(listMessage.get(i), new Object[]{});
                            log("getPicBgType:::::::::" + picBgType);


                            Method getPicInfos = StatusClass.getMethod("getPicInfos");
                            List picInfos = (List) getPicInfos.invoke(listMessage.get(i), new Object[]{});
                            log("getPhotoTag:::::::::"+picInfos);
                            if (null!=picInfos)
                            {
                                for (int j=0;j<picInfos.size();j++) {
                                    Method getPicSmallUrl = PicInfoClass.getMethod("getPicSmallUrl");
                                    String picSmallUrl = (String) getPicSmallUrl.invoke(picInfos.get(j), new Object[]{});
                                    log("getPicSmallUrl:::::::::" + picSmallUrl);

                                    Method getLargeUrl = PicInfoClass.getMethod("getLargeUrl");
                                    String largeUrl = (String) getLargeUrl.invoke(picInfos.get(j), new Object[]{});
                                    log("getLargeUrl:::::::::" + largeUrl);

                                    Method getLargestUrl = PicInfoClass.getMethod("getLargestUrl");
                                    String largestUrl = (String) getLargestUrl.invoke(picInfos.get(j), new Object[]{});
                                    log("getLargestUrl:::::::::" + largestUrl);

                                    Method getMiddlePlusUrl = PicInfoClass.getMethod("getMiddlePlusUrl");
                                    String middlePlusUrl = (String) getMiddlePlusUrl.invoke(picInfos.get(j), new Object[]{});
                                    log("getMiddlePlusUrl:::::::::" + middlePlusUrl);

                                    Method getOriginalUrl = PicInfoClass.getMethod("getOriginalUrl");
                                    String originalUrl = (String) getOriginalUrl.invoke(picInfos.get(j), new Object[]{});
                                    log("getOriginalUrl:::::::::" + originalUrl);

                                    Method getPicBigUrl = PicInfoClass.getMethod("getPicBigUrl");
                                    String picBigUrl = (String) getPicBigUrl.invoke(picInfos.get(j), new Object[]{});
                                    log("getPicBigUrl:::::::::" + picBigUrl);

                                    Method getPicMiddleUrl = PicInfoClass.getMethod("getPicMiddleUrl");
                                    String picMiddleUrl = (String) getPicMiddleUrl.invoke(picInfos.get(j), new Object[]{});
                                    log("getPicMiddleUrl:::::::::" + picMiddleUrl);


                                }

                            }


                             Method getPositive_recom_flag = StatusClass.getMethod("getPositive_recom_flag");
                            int positive_recom_flag = (int) getPositive_recom_flag.invoke(listMessage.get(i), new Object[]{});
                            log("getPositive_recom_flag:::::::::" + positive_recom_flag);












//                            Method getTitle = StatusComplaintClass.getMethod("getTitle");
//                            String title = (String) getTitle.invoke(continueTag, new Object[]{});
//                            log("getTitle:::::::::" + title);


//                            Method getContinueTag = StatusClass.getMethod("getContinueTag");
//                            Object continueTags = (Object) getContinueTag.invoke(listMessage.get(i), new Object[]{});
//                            log("getContinueTag:::::::::" + continueTags.toString());
//
//
//                            Method getPic = ContinueTagClass.getMethod("getPic");
//                            String pic = (String) getPic.invoke(continueTags, new Object[]{});
//                            log("getPic:::::::::" + pic);
//
//                            Method getTitle = ContinueTagClass.getMethod("getTitle");
//                            String title = (String) getTitle.invoke(continueTags, new Object[]{});
//                            log("getPic:::::::::" + title);


//                            Method preloadData = StatusClass.getMethod("preloadData");
//                            Map mpreloadData = (Map) preloadData.invoke(listMessage.get(i), new Object[]{});
//                            log("getPreloadArticleIDs:::::::::" + mpreloadData.toString());


//                            Method getSummaryInfo = StatusClass.getMethod("getSummaryInfo");
//                            String summaryInfo = (String) getSummaryInfo.invoke(forwardSummary, new Object[]{});
//
//                            log("getCreatedDate:::::::::" + summaryInfo);
//
//
//                            Method getShared_count = StatusClass.getMethod("getShared_count");
//                            int sharedCount = (int) getShared_count.invoke(listMessage.get(i), new Object[]{});
//
//                            log("sharedCount:::::::::" + sharedCount);
//                            Method getReposts_count = StatusClass.getMethod("getReposts_count");
//                            int reposts_count = (int) getReposts_count.invoke(listMessage.get(i), new Object[]{});
//                            log("getReposts_count:::::::::" + reposts_count);

//                            for (int j=0;j<commonStructs.size();j++)
//                            {
//
//                                Method getDesc = MBlogExtendPageClass.getMethod("getDesc");
//                                String Desc = (String) getDesc.invoke(commonStructs.get(j), new Object[]{});
//                                log("getDesc==========="+Desc);
//
//                            }
//                            if (commentSummary!=null) {
//
//                                Method getSummaryInfo = CommentSummaryClass.getMethod("getSummaryInfo");
//                                String summaryInfo = (String) getSummaryInfo.invoke(commentSummary, new Object[]{});
//                                log("getSummaryInfo=======" + summaryInfo);
//                            }

//                            Method getPic = StatusClass.getMethod("getPic");
//                            String cardTitle = (String) getPic.invoke(listMessage.get(i), new Object[]{});
//                            log(getPic.getName().toString() + ":::::::::" + cardTitle);
//
//                            Method getArticleDeleteText = StatusClass.getMethod("getArticleDeleteText");
//                            String articleDeleteText = (String) getArticleDeleteText.invoke(listMessage.get(i), new Object[]{});
//                            log(getArticleDeleteText.getName().toString() + ":::::::::" + articleDeleteText);

//                            Method getAttitudes_count = StatusClass.getMethod("getAttitudes_count");
//                            int attitudes_count = (int) getAttitudes_count.invoke(listMessage.get(i), new Object[]{});
//                            log(getAttitudes_count.getName().toString() + ":::::::::" + attitudes_count);
//
//                            Method getAttitudes_status = StatusClass.getMethod("getAttitudes_status");
//                            int attitudes_status = (int) getAttitudes_status.invoke(listMessage.get(i), new Object[]{});
//                            log(getAttitudes_status.getName().toString() + ":::::::::" + attitudes_status);
//
//


                        }


                    }
                });


            }
        });

    }


}
