package com.android.capture.hooks;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.SpannableStringBuilder;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;
import java.util.Map;

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

                findAndHookMethod("com.sina.weibo.stream.b.d", lpparam.classLoader, "a", List.class, new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                        log("com.sina.weibo.page.view.a======a");
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
//                        final Class MBlogTitleInfoClass = XposedHelpers.findClass("com.sina.weibo.models.MBlogTitleInfo", lpparam.classLoader);
                        final Class MediaDataObjectClass = XposedHelpers.findClass("com.sina.weibo.card.model.MediaDataObject", lpparam.classLoader);

                        final List listMessage = (List) param.args[0];
//                        final List listMessage = (List) param.getResult();
//                        final List trendMessage = (List) param.args[1];

                        log("com.sina.weibo.stream.b.d======b" + listMessage.size() + "Object:::::::::" + listMessage.toString());
//                        log("trend------------------d"+ trendMessage.size()+trendMessage.toString());
//                        List pageCardInfo = (List) param.getResult();


                        for (int i = 0; i < listMessage.size(); i++) {


                            //信息来源，手机weibo,还是其他平台
                            Method getAnnotations = StatusClass.getMethod("getAnnotations");
                            List annotations = (List) getAnnotations.invoke(listMessage.get(i), new Object[]{});
                            log(getAnnotations.getName().toString() + ":::::::::" + annotations);
                            if (annotations != null) {
//
                                for (int j = 0; j < annotations.size(); j++) {
                                    if (null != annotations.get(j)) {
                                        Method getClient_mblogid = StatusAnnotationsClass.getMethod("getClient_mblogid");
                                        String client_mblogid = (String) getClient_mblogid.invoke(annotations.get(j), new Object[]{});
                                        log("getClient_mblogid:::::::::" + client_mblogid);
                                    }
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

                            Method getFormatSourceUrl = StatusClass.getMethod("getFormatSourceUrl");
                            String formatSourceUrl = (String) getFormatSourceUrl.invoke(listMessage.get(i), new Object[]{});
                            log("getFormatSourceUrl:::::::::" + formatSourceUrl);

                            Method getFormatSourceDetail = StatusClass.getMethod("getFormatSourceDetail");
                            List formatSourceDetail = (List) getFormatSourceDetail.invoke(listMessage.get(i), new Object[]{});
                            log("getFormatSourceDetail:::::::::" + formatSourceDetail);


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


                            Method getCreatedDateStr = StatusClass.getMethod("getCreatedDateStr");
                            String createdDateStr = (String) getCreatedDateStr.invoke(listMessage.get(i), new Object[]{});
                            log("getCreatedDateStr:::::::::" + createdDateStr);

                            Method getCreatedDate = StatusClass.getMethod("getCreatedDate");
                            Date createdDate = (Date) getCreatedDate.invoke(listMessage.get(i), new Object[]{});
                            log("getCreatedDate:::::::::" + createdDate);


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

                            if (null != common_structs && common_structs.size() > 0) {
                                for (int j = 0; j < common_structs.size(); j++) {
                                    Method getDesc = MBlogExtendPageClass.getMethod("getDesc");
                                    String Desc = (String) getDesc.invoke(common_structs.get(j), new Object[]{});
                                    log("getDesc:::::::::" + Desc);

                                    Method getImg = MBlogExtendPageClass.getMethod("getImg");
                                    String Img = (String) getImg.invoke(common_structs.get(j), new Object[]{});
                                    log("getImg:::::::::" + Img);

                                    Method getName = MBlogExtendPageClass.getMethod("getName");
                                    String name = (String) getImg.invoke(common_structs.get(j), new Object[]{});
                                    log("getName:::::::::" + name);

                                    Method getType = MBlogExtendPageClass.getMethod("getType");
                                    int type = (int) getType.invoke(common_structs.get(j), new Object[]{});
                                    log("getType:::::::::" + type);

                                    Method getUrl = MBlogExtendPageClass.getMethod("getUrl");
                                    String url = (String) getUrl.invoke(common_structs.get(j), new Object[]{});
                                    log("getUrl:::::::::" + url);


                                }
                            }

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
                            log("getExtraButtonInfo:::::::::" + extraButtonInfo);

                            Method getForwardSummary = StatusClass.getMethod("getForwardSummary");
                            Object forwardSummary = (Object) getForwardSummary.invoke(listMessage.get(i), new Object[]{});
                            log("getForwardSummary:::::::::" + forwardSummary);

                            Method getGeo = StatusClass.getMethod("getGeo");
                            Object Geo = (Object) getGeo.invoke(listMessage.get(i), new Object[]{});
                            log("getGeo:::::::::" + Geo);


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
                            log("getKeyword_struct:::::::::" + keyword_struct);

                            Method getLikeSummary = StatusClass.getMethod("getLikeSummary");
                            Object likeSummary = (Object) getLikeSummary.invoke(listMessage.get(i), new Object[]{});
                            log("getLikeSummary:::::::::" + likeSummary);


                            Method getLocalMblogId = StatusClass.getMethod("getLocalMblogId");
                            final String localMblogId = (String) getLocalMblogId.invoke(listMessage.get(i), new Object[]{});
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
                            if (null != mblogMenus) {
                                for (int j = 0; j < mblogMenus.size(); j++) {
                                    Method getAfterDownLoadName = JsonButtonClass.getMethod("getAfterDownLoadName");
                                    String afterDownLoadName = (String) getAfterDownLoadName.invoke(mblogMenus.get(j), new Object[]{});
                                    log("getAfterDownLoadName:::::::::" + afterDownLoadName);

                                    Method getMonitorUrl = JsonButtonClass.getMethod("getMonitorUrl");
                                    String monitorUrl = (String) getMonitorUrl.invoke(mblogMenus.get(j), new Object[]{});
                                    log("getMonitorUrl:::::::::" + monitorUrl);


                                    Method getFollow_res_pic = JsonButtonClass.getMethod("getFollow_res_pic");
                                    String follow_res_pic = (String) getFollow_res_pic.invoke(mblogMenus.get(j), new Object[]{});
                                    log("getFollow_res_pic:::::::::" + follow_res_pic);
                                }
                            }

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


//                            Method getMultiMedia = StatusClass.getMethod("getMultiMedia");
//                            List multiMedia = (List) getMultiMedia.invoke(listMessage.get(i), new Object[]{});
//                            log("getMultiMedia:::::::::" + multiMedia);

                            Method getCardInfo = StatusClass.getMethod("getCardInfo");
                            Object cardInfo = (Object) getCardInfo.invoke(listMessage.get(i), new Object[]{});
                            log("getCardInfo:::::::::" + cardInfo);
                            if (null != cardInfo) {
                                Method getPeople_desc = MblogCardInfoClass.getMethod("getPeople_desc");
                                String people_desc = (String) getPeople_desc.invoke(cardInfo, new Object[]{});
                                log(" getPeople_desc:::::::::" + people_desc);

//
//                                Method getMedia = MblogCardInfoClass.getMethod("getMedia");
//                                Object media = (Object) getMedia.invoke(cardInfo, new Object[]{});
//                                log(" getMedia:::::::::" + media);
//////
//                                Method getMonitorUrl = MediaDataObjectClass.getMethod("getMonitorUrl");
//                                String monitorUrl = (String) getMonitorUrl.invoke(cardInfo, new Object[]{});
//                                log(" getMonitorUrl:::::::::" + monitorUrl);
//
//                                Method getMultimediaActionlog = MediaDataObjectClass.getMethod("getMultimediaActionlog");
//                                String multimediaActionlog = (String) getMultimediaActionlog.invoke(cardInfo, new Object[]{});
//                                log(" getMultimediaActionlog:::::::::" + multimediaActionlog);


                            }

                            Method getPhotoTag = StatusClass.getMethod("getPhotoTag");
                            List pshotoTag = (List) getPhotoTag.invoke(listMessage.get(i), new Object[]{});
                            log("getPhotoTag:::::::::" + pshotoTag);


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
                            log("getPicInfos:::::::::" + picInfos);
                            if (null != picInfos) {
                                for (int j = 0; j < picInfos.size(); j++) {
//                                    Method getPicSmallUrl = PicInfoClass.getMethod("getPicSmallUrl");
//                                    String picSmallUrl = (String) getPicSmallUrl.invoke(picInfos.get(j), new Object[]{});
//                                    log("getPicSmallUrl:::::::::" + picSmallUrl);

                                    Method getLargeUrl = PicInfoClass.getMethod("getLargeUrl");
                                    String largeUrl = (String) getLargeUrl.invoke(picInfos.get(j), new Object[]{});
                                    log("getLargeUrl:::::::::" + largeUrl);

//                                    Method getLargestUrl = PicInfoClass.getMethod("getLargestUrl");
//                                    String largestUrl = (String) getLargestUrl.invoke(picInfos.get(j), new Object[]{});
//                                    log("getLargestUrl:::::::::" + largestUrl);
//
//                                    Method getMiddlePlusUrl = PicInfoClass.getMethod("getMiddlePlusUrl");
//                                    String middlePlusUrl = (String) getMiddlePlusUrl.invoke(picInfos.get(j), new Object[]{});
//                                    log("getMiddlePlusUrl:::::::::" + middlePlusUrl);
//
//                                    Method getOriginalUrl = PicInfoClass.getMethod("getOriginalUrl");
//                                    String originalUrl = (String) getOriginalUrl.invoke(picInfos.get(j), new Object[]{});
//                                    log("getOriginalUrl:::::::::" + originalUrl);
//
//                                    Method getPicBigUrl = PicInfoClass.getMethod("getPicBigUrl");
//                                    String picBigUrl = (String) getPicBigUrl.invoke(picInfos.get(j), new Object[]{});
//                                    log("getPicBigUrl:::::::::" + picBigUrl);
//
//                                    Method getPicMiddleUrl = PicInfoClass.getMethod("getPicMiddleUrl");
//                                    String picMiddleUrl = (String) getPicMiddleUrl.invoke(picInfos.get(j), new Object[]{});
//                                    log("getPicMiddleUrl:::::::::" + picMiddleUrl);
//
                                    Method getVideo = PicInfoClass.getMethod("getVideo");
                                    String video = (String) getVideo.invoke(picInfos.get(j), new Object[]{});
                                    log("getVideo:::::::::" + video);
                                    Method getVideo_object_id = PicInfoClass.getMethod("getVideo_object_id");
                                    String video_object_id = (String) getVideo_object_id.invoke(picInfos.get(j), new Object[]{});
                                    log("getVideo_object_id:::::::::" + video_object_id);


                                }

                            }


                            Method getPositive_recom_flag = StatusClass.getMethod("getPositive_recom_flag");
                            int positive_recom_flag = (int) getPositive_recom_flag.invoke(listMessage.get(i), new Object[]{});
                            log("getPositive_recom_flag:::::::::" + positive_recom_flag);


                            Method getPpreloadArticleIDs = StatusClass.getMethod("getPreloadArticleIDs");
                            List reloadArticleIDs = (List) getPpreloadArticleIDs.invoke(listMessage.get(i), new Object[]{});
                            log("getPreloadArticleIDs:::::::::" + reloadArticleIDs.toString());

                            Method getPreloadData = StatusClass.getMethod("getPreloadData");
                            Map preloadData = (Map) getPreloadData.invoke(listMessage.get(i), new Object[]{});
                            log("getPreloadData:::::::::" + preloadData);


                            Method getPreloadType = StatusClass.getMethod("getPreloadType");
                            int preloadType = (int) getPreloadType.invoke(listMessage.get(i), new Object[]{});
                            log("getPreloadType:::::::::" + preloadType);

                            Method getProducts = StatusClass.getMethod("getProducts");
                            List products = (List) getProducts.invoke(listMessage.get(i), new Object[]{});
                            log("getProducts:::::::::" + products);

                            Method getPromotion = StatusClass.getMethod("getPromotion");
                            Object promotion = (Object) getPromotion.invoke(listMessage.get(i), new Object[]{});
                            log("getPromotion:::::::::" + promotion);

                            Method getPromotionInfo = StatusClass.getMethod("getPromotionInfo");
                            Object promotionInfo = (Object) getPromotionInfo.invoke(listMessage.get(i), new Object[]{});
                            log("getPromotionInfo:::::::::" + promotionInfo);

                            Method getReads_count = StatusClass.getMethod("getReads_count");
                            int reads_count = (int) getReads_count.invoke(listMessage.get(i), new Object[]{});
                            log("getReads_count:::::::::" + reads_count);

                            Method getRecomState = StatusClass.getMethod("getRecomState");
                            int recomState = (int) getRecomState.invoke(listMessage.get(i), new Object[]{});
                            log("getRecomState:::::::::" + recomState);


                            Method getRefreshTime = StatusClass.getMethod("getRefreshTime");
                            long refreshTime = (long) getRefreshTime.invoke(listMessage.get(i), new Object[]{});
                            log("getRefreshTime:::::::::" + refreshTime);


                            Method getRid = StatusClass.getMethod("getRid");
                            String rid = (String) getRid.invoke(listMessage.get(i), new Object[]{});
                            log("getRid:::::::::" + rid);

                            Method getRetweetReason = StatusClass.getMethod("getRetweetReason");
                            String retweetReason = (String) getRetweetReason.invoke(listMessage.get(i), new Object[]{});
                            log("getRetweetReason:::::::::" + retweetReason);


                            Method getSchema = StatusClass.getMethod("getSchema");
                            String schema = (String) getSchema.invoke(listMessage.get(i), new Object[]{});
                            log("getSchema:::::::::" + schema);


                            Method getShared_count = StatusClass.getMethod("getShared_count");
                            int sharedCount = (int) getShared_count.invoke(listMessage.get(i), new Object[]{});
                            log("sharedCount:::::::::" + sharedCount);


                            Method getShared_url = StatusClass.getMethod("getShared_url");
                            String shared_url = (String) getShared_url.invoke(listMessage.get(i), new Object[]{});
                            log("getShared_url:::::::::" + shared_url);

                            Method getSource = StatusClass.getMethod("getSource");
                            String source = (String) getSource.invoke(listMessage.get(i), new Object[]{});
                            log("getSource:::::::::" + source);

                            Method getSourceAllowClick = StatusClass.getMethod("getSourceAllowClick");
                            int sourceAllowClick = (int) getSourceAllowClick.invoke(listMessage.get(i), new Object[]{});
                            log("getSourceAllowClick:::::::::" + sourceAllowClick);

                            Method getSourceScheme = StatusClass.getMethod("getSourceScheme");
                            String sourceScheme = (String) getSourceScheme.invoke(listMessage.get(i), new Object[]{});
                            log("getSourceScheme:::::::::" + sourceScheme);


                            Method getSource_type = StatusClass.getMethod("getSource_type");
                            int source_type = (int) getSource_type.invoke(listMessage.get(i), new Object[]{});
                            log("getSource_type:::::::::" + source_type);


                            Method getSummaryOrder = StatusClass.getMethod("getSummaryOrder");
                            int[] summaryOrder = (int[]) getSummaryOrder.invoke(listMessage.get(i), new Object[]{});
                            log("getSummaryOrder:::::::::" + summaryOrder);

                            Method getMultiMedia = StatusClass.getMethod("getMultiMedia");
                            List gmultiMedia = (List) getMultiMedia.invoke(listMessage.get(i), new Object[]{});
                            log("getMultiMedia:::::::::" + gmultiMedia);


                            Method getMBlogTag = StatusClass.getMethod("getMBlogTag");
                            List mBlogTag = (List) getMBlogTag.invoke(listMessage.get(i), new Object[]{});
                            log("getMBlogTag:::::::::" + mBlogTag);
                            if (null != mBlogTag) {
                                for (int j = 0; j < mBlogTag.size(); j++) {

                                    Method getOid = MBlogTagClass.getMethod("getOid");
                                    String oid = (String) getOid.invoke(mBlogTag.get(j), new Object[]{});
                                    log("getOid:::::::::" + oid);


                                    Method getName = MBlogTagClass.getMethod("getName");
                                    String name = (String) getName.invoke(mBlogTag.get(j), new Object[]{});
                                    log("getName:::::::::" + name);

                                    Method getScheme = MBlogTagClass.getMethod("getScheme");
                                    String tag_scheme = (String) getScheme.invoke(mBlogTag.get(j), new Object[]{});
                                    log("getScheme:::::::::" + tag_scheme);

                                    Method getUrl_type_pic = MBlogTagClass.getMethod("getUrl_type_pic");
                                    String url_type_pic = (String) getUrl_type_pic.invoke(mBlogTag.get(j), new Object[]{});
                                    log("getUrl_type_pic:::::::::" + url_type_pic);

                                    Method getTagHidden = MBlogTagClass.getMethod("getTagHidden");
                                    int tagHidden = (int) getTagHidden.invoke(mBlogTag.get(j), new Object[]{});
                                    log("getTagHidden:::::::::" + tagHidden);

                                    Method getType = MBlogTagClass.getMethod("getType");
                                    int type = (int) getType.invoke(mBlogTag.get(j), new Object[]{});
                                    log("getType:::::::::" + type);


                                }

                            }

//
//                            Method getContinueTag = StatusClass.getMethod("getContinueTag");
//                            Object continueTag = (Object) getContinueTag.invoke(listMessage.get(i), new Object[]{});
//                            log("getContinueTag:::::::::" + continueTag);
//                            if (null != continueTag) {
//                                Method getPic = MBlogTagClass.getMethod("getPic");
//                                String pic = (String) getPic.invoke(continueTag, new Object[]{});
//                                log("getPic:::::::::" + pic);
//
//                                Method getScheme = MBlogTagClass.getMethod("getScheme");
//                                String scheme = (String) getScheme.invoke(continueTag, new Object[]{});
//                                log("getScheme:::::::::" + scheme);
//
//
//                                Method getTitle = MBlogTagClass.getMethod("getTitle");
//                                String title = (String) getTitle.invoke(continueTag, new Object[]{});
//                                log("getTitle:::::::::" + title);
//
//
//                            }


                            Method getMblogTitle = StatusClass.getMethod("getMblogTitle");
                            Object mblogTitle = (Object) getMblogTitle.invoke(listMessage.get(i), new Object[]{});
                            log("getMblogTitle:::::::::" + mblogTitle);

                            if (null != mblogTitle) {
                                Method getGid = MBlogTitleClass.getMethod("getGid");
                                String gid = (String) getGid.invoke(mblogTitle, new Object[]{});
                                log("getGid:::::::::" + gid);

                                Method getIcon_url = MBlogTitleClass.getMethod("getIcon_url");
                                String icon_url = (String) getIcon_url.invoke(mblogTitle, new Object[]{});
                                log("getIcon_url:::::::::" + icon_url);

                                Method getTitleInfos = MBlogTitleClass.getMethod("getTitleInfos");
                                List titleInfos = (List) getTitleInfos.invoke(mblogTitle, new Object[]{});
                                log("getTitleInfos:::::::::" + titleInfos);
//                                if (null!=titleInfos&titleInfos.size()>0)
//                                for (int j=0;j<titleInfos.size();j++)
//                                {
//                                    Method getName = MBlogTitleInfoClass.getMethod("getName");
//                                    String name = (String) getName.invoke(titleInfos.get(j), new Object[]{});
//                                    log("getName:::::::::" + name);
//
//                                }


                                Method getTitle = MBlogTitleClass.getMethod("getTitle");
                                String title = (String) getTitle.invoke(mblogTitle, new Object[]{});
                                log("getTitle:::::::::" + title);


                            }


                            Method getTopRightButtonType = StatusClass.getMethod("getTopRightButtonType");
                            int topRightButtonType = (int) getTopRightButtonType.invoke(listMessage.get(i), new Object[]{});
                            log("getTopRightButtonType:::::::::" + topRightButtonType);


                            Method getTopicList = StatusClass.getMethod("getTopicList");
                            List topicList = (List) getTopicList.invoke(listMessage.get(i), new Object[]{});
                            log("getTopicList:::::::::" + topicList);
                            if (null != topicList) {
                                for (int j = 0; j < topicList.size(); j++) {

                                    Method getTopic_title = MblogTopicClass.getMethod("getTopic_title");
                                    String topic_title = (String) getTopic_title.invoke(topicList.get(j), new Object[]{});
                                    log("getTopic_title:::::::::" + topic_title);
//
                                    Method getTopic_url = MblogTopicClass.getMethod("getTopic_url");
                                    String topic_url = (String) getTopic_url.invoke(topicList.get(j), new Object[]{});
                                    log("getTopic_url:::::::::" + topic_url);


                                }
                            }

                            Method getUrlList = StatusClass.getMethod("getUrlList");
                            List urlList = (List) getUrlList.invoke(listMessage.get(i), new Object[]{});
                            log("getUrlList:::::::::" + urlList);

                            if (null != urlList) {
                                for (int j = 0; j < urlList.size(); j++) {

                                    Method getOri_url = MblogCardClass.getMethod("getOri_url");
                                    String ori_url = (String) getOri_url.invoke(urlList.get(j), new Object[]{});
                                    log("getOri_url:::::::::" + ori_url);
//
                                    Method getUrl_title = MblogCardClass.getMethod("getUrl_title");
                                    String url_title = (String) getUrl_title.invoke(urlList.get(j), new Object[]{});
                                    log("getUrl_title:::::::::" + url_title);

                                    Method getUrl_type_pic = MblogCardClass.getMethod("getUrl_type_pic");
                                    String url_type_pic = (String) getUrl_type_pic.invoke(urlList.get(j), new Object[]{});
                                    log("getUrl_type_pic:::::::::" + url_type_pic);

                                    Method getShort_url = MblogCardClass.getMethod("getShort_url");
                                    String short_url = (String) getShort_url.invoke(urlList.get(j), new Object[]{});
                                    log("getShort_url:::::::::" + short_url);

                                    Method getUrl_type = MblogCardClass.getMethod("getUrl_type");
                                    String url_type = (String) getUrl_type.invoke(urlList.get(j), new Object[]{});
                                    log("getUrl_type:::::::::" + url_type);

                                    Method getHide = MblogCardClass.getMethod("getHide");
                                    String hide = (String) getHide.invoke(urlList.get(j), new Object[]{});
                                    log("getHide:::::::::" + hide);
////
//                                    Method getPosition = MblogCardClass.getMethod("getPosition");
//                                    int position = (int) getPosition.invoke(urlList.get(j), new Object[]{});
//                                    log("getPosition:::::::::" + position);

//                                    Method getIconResId = MblogCardClass.getMethod("getIconResId");
//                                    int iconResId = (int) getIconResId.invoke(urlList.get(j), new Object[]{});
//                                    log("getIconResId:::::::::" + iconResId);
////
//
//                                    Method getLog = MblogCardClass.getMethod("getLog");
//                                    String log = (String) getLog.invoke(urlList.get(j), new Object[]{});
//                                    log("getLog:::::::::" + log);


//                                    Method getPage_id = MblogCardClass.getMethod("getPage_id");
//                                    String page_id = (String) getPage_id.invoke(urlList.get(j), new Object[]{});
//                                    log("getPage_id:::::::::" + page_id);
//
//                                    Method getPicInfosList = MblogCardClass.getMethod("getPicInfosList");
//                                    List picInfosList = (List) getPicInfosList.invoke(urlList.get(j), new Object[]{});
//                                    log("getPicInfosList:::::::::" + picInfosList);
//                                    if (null != picInfosList) {
//                                        for (int n = 0; n< picInfosList.size(); n++) {
//
//                                            Method getLargeUrl = PicInfoClass.getMethod("getLargeUrl");
//                                            String largeUrl = (String) getLargeUrl.invoke(picInfosList.get(n), new Object[]{});
//                                            log("getLargeUrl:::::::::" + largeUrl);
//                                            Method getVideo = PicInfoClass.getMethod("getVideo");
//                                            String video = (String) getVideo.invoke(picInfosList.get(n), new Object[]{});
//                                            log("getVideo:::::::::" + video);
//                                            Method getVideo_object_id = PicInfoClass.getMethod("getVideo_object_id");
//                                            String video_object_id = (String) getVideo_object_id.invoke(picInfosList.get(n), new Object[]{});
//                                            log("getVideo_object_id:::::::::" + video_object_id);
//
//
//                                        }
//
//                                    }


                                }
                            }


                            Method getUser = StatusClass.getMethod("getUser");
                            Object user = (Object) getUser.invoke(listMessage.get(i), new Object[]{});
                            log("getUser:::::::::" + user);
                            if (null != user) {

                                //空
                                Method getMblogContents = JsonUserInfoClass.getMethod("getMblogContent");
                                String mblogContents = (String) getMblogContents.invoke(user, new Object[]{});
                                log("getMblogContent:::::::::" + mblogContents);
                                //高清图片

                                Method getAvatarHd = JsonUserInfoClass.getMethod("getAvatarHd");
                                String avatarHd = (String) getAvatarHd.invoke(user, new Object[]{});
                                log("getAvatarHd:::::::::" + avatarHd);
                                //微博名
                                Method getName = JsonUserInfoClass.getMethod("getName");
                                String name = (String) getName.invoke(user, new Object[]{});
                                log("getName:::::::::" + name);


                                Method getRegVipMob = JsonUserInfoClass.getMethod("getRegVipMob");
                                String regVipMob = (String) getRegVipMob.invoke(user, new Object[]{});
                                log("getRegVipMob:::::::::" + regVipMob);


                                Method getRemark = JsonUserInfoClass.getMethod("getRemark");
                                String remark = (String) getRemark.invoke(user, new Object[]{});
                                log("getRemark:::::::::" + remark);
                                //配置图片

                                Method getProfileImageUrl = JsonUserInfoClass.getMethod("getProfileImageUrl");
                                String profileImageUrl = (String) getProfileImageUrl.invoke(user, new Object[]{});
                                log("getProfileImageUrl:::::::::" + profileImageUrl);
                                //简介

                                Method getDescription = JsonUserInfoClass.getMethod("getDescription");
                                String description = (String) getDescription.invoke(user, new Object[]{});
                                log("getDescription:::::::::" + description);

                                //图片地址大
                                Method getAvatarLarge = JsonUserInfoClass.getMethod("getAvatarLarge");
                                String avatarLarge = (String) getAvatarLarge.invoke(user, new Object[]{});
                                log("getAvatarLarge:::::::::" + avatarLarge);
//                                //图片地址大
//                                Method getUserId = JsonUserInfoClass.getMethod("getId");
//                                String userId = (String) getUserId.invoke(user, new Object[]{});
//                                log("getUserId:::::::::" + userId);
//  //图片地址大
//                                Method getGidStr = JsonUserInfoClass.getMethod("getGidStr");
//                                String gidStr = (String) getGidStr.invoke(user, new Object[]{});
//                                log("getGidStr:::::::::" + gidStr);

//                                Method getInfo = JsonUserInfoClass.getMethod("getInfo");
//                                String info = (String) getInfo.invoke(user, new Object[]{});
//                                log("getInfo:::::::::" + info);


//                                //认证类型
//                                Method getVerifiedType = JsonUserInfoClass.getMethod("getVerifiedType");
//                                int verifiedType = (int) getVerifiedType.invoke(user, new Object[]{});
//                                log("getVerifiedType:::::::::" + verifiedType);

//                                //认证理由
//                                Method getVerifiedReason = JsonUserInfoClass.getMethod("getVerifiedReason");
//                                String erifiedReason = (String) getVerifiedReason.invoke(user, new Object[]{});
//                                log("getVerifiedReason:::::::::" + erifiedReason);
//
//                                //是否认证
//                                Method getVerified = JsonUserInfoClass.getMethod("getVerified");
//                                String verified = (String) getVerified.invoke(user, new Object[]{});
//                                log("getVerified:::::::::" + verified);
//
                                //会员是否过期
//                                Method isVipCoverOverdue = JsonUserInfoClass.getMethod("isVipCoverOverdue");
//                                Boolean vipCoverOverdue = (Boolean) isVipCoverOverdue.invoke(user, new Object[]{});
//                                log("Boolean:::::::::" + vipCoverOverdue);

//
//                                Method getuRank = JsonUserInfoClass.getMethod("getuRank");
//                                int uRank = (int) getuRank.invoke(user, new Object[]{});
//                                log("getuRank:::::::::" + uRank);


//                                Method getBadges = JsonUserInfoClass.getMethod("getBadges");
//                                List badges = (List) getBadges.invoke(user, new Object[]{});
//                                log("getBadges:::::::::" + badges);

//                                Method getBirthday = JsonUserInfoClass.getMethod("getBirthday");
//                                String birthday = (String) getBirthday.invoke(user, new Object[]{});
//                                log("getBirthday:::::::::" + birthday);

//                                Method getCareerInfos = JsonUserInfoClass.getMethod("getCareerInfos");
//                                List careerInfos = (List) getCareerInfos.invoke(user, new Object[]{});
//                                log("getCareerInfos:::::::::" + careerInfos);
//

//                                Method getCity = JsonUserInfoClass.getMethod("getCity");
//                                String city = (String) getCity.invoke(user, new Object[]{});
//                                log("getCity:::::::::" + city);
//
//                                Method getCloseFriendType = JsonUserInfoClass.getMethod("getCloseFriendType");
//                                int closeFriendType = (int) getCloseFriendType.invoke(user, new Object[]{});
//                                log("getCloseFriendType:::::::::" + closeFriendType);
//
//
//                                Method getCoverUrl = JsonUserInfoClass.getMethod("getCoverUrl");
//                                String coverUrl = (String) getCoverUrl.invoke(user, new Object[]{});
//                                log("getCoverUrl:::::::::" + coverUrl);
//
//                                Method getCoverLevel = JsonUserInfoClass.getMethod("getCoverLevel");
//                                String coverLevel = (String) getCoverLevel.invoke(user, new Object[]{});
//                                log("getCoverLevel:::::::::" + coverLevel);
//
//
//                                Method getCover_overdue_scheme = JsonUserInfoClass.getMethod("getCover_overdue_scheme");
//                                String cover_overdue_scheme = (String) getCover_overdue_scheme.invoke(user, new Object[]{});
//                                log("getCover_overdue_scheme:::::::::" + cover_overdue_scheme);
//
//
//
//                                Method getCover_images_phone = JsonUserInfoClass.getMethod("getCover_images_phone");
//                                List cover_images_phone = (List) getCover_images_phone.invoke(user, new Object[]{});
//                                log("getCover_images_phone:::::::::" + cover_images_phone);
//
//
//
//                                Method getCreatedAt = JsonUserInfoClass.getMethod("getCreatedAt");
//                                String createdAt = (String) getCreatedAt.invoke(user, new Object[]{});
//                                log("getCreatedAt:::::::::" + createdAt);


                            }

//                            log("===============================================");
//                            log("************************************************");
//                            log("===============================================");


//                            for (int n = 0; n < trendMessage.size(); n++) {
//
//                                Method getApi_type = TrendClass.getMethod("getApi_type");
//                                String api_type = (String) getApi_type.invoke(trendMessage.get(n), new Object[]{});
//                                log("getApi_type:::::::::" + api_type);
//
//
//                                Method getHasmore = TrendClass.getMethod("getHasmore");
//                                int hasmore = (int) getHasmore.invoke(trendMessage.get(n), new Object[]{});
//                                log("getHasmore:::::::::" + hasmore);
//
//
//                                Method getHide_border = TrendClass.getMethod("getHide_border");
//                                int Hide_border = (int) getHide_border.invoke(trendMessage.get(n), new Object[]{});
//                                log("getHide_border:::::::::" + Hide_border);
//
//
//                                Method getImageScheme = TrendClass.getMethod("getImageScheme");
//                                String imageScheme = (String) getImageScheme.invoke(trendMessage.get(n), new Object[]{});
//                                log("getImageScheme:::::::::" + imageScheme);
//
//                                Method getImageUrl = TrendClass.getMethod("getImageUrl");
//                                String imageUrl = (String) getImageUrl.invoke(trendMessage.get(n), new Object[]{});
//                                log("getImageUrl:::::::::" + imageUrl);
//
//                                Method getCurrentSelectedId = TrendClass.getMethod("getCurrentSelectedId");
//                                int currentSelectedId = (int) getCurrentSelectedId.invoke(trendMessage.get(n), new Object[]{});
//                                log("getCurrentSelectedId:::::::::" + currentSelectedId);
//
//                                Method getExtraStruct = TrendClass.getMethod("getExtraStruct");
//                                Object extraStruct = (Object) getExtraStruct.invoke(trendMessage.get(n), new Object[]{});
//                                log("getExtraStruct:::::::::" + extraStruct);
//
//                                Method getGroupId = TrendClass.getMethod("getGroupId");
//                                String groupId = (String) getGroupId.invoke(trendMessage.get(n), new Object[]{});
//                                log("getGroupId:::::::::" + groupId);
//
//                                Method getLocalIndex = TrendClass.getMethod("getLocalIndex");
//                                int localIndex = (int) getLocalIndex.invoke(trendMessage.get(n), new Object[]{});
//                                log("getLocalIndex:::::::::" + localIndex);
//
//                                Method getTimeStamp = TrendClass.getMethod("getTimeStamp");
//                                int timeStamp = (int) getTimeStamp.invoke(trendMessage.get(n), new Object[]{});
//                                log("getTimeStamp:::::::::" + timeStamp);
//
//
//                                Method getTitle = TrendClass.getMethod("getTitle");
//                                Object title = (Object) getTitle.invoke(trendMessage.get(n), new Object[]{});
//                                log("getTitle:::::::::" + title);
//
//                                Method getCards = TrendClass.getMethod("getCards");
//                                List cards = (List) getCards.invoke(trendMessage.get(n), new Object[]{});
//                                log("getCards:::::::::" + cards);
//
//
//                                Method getTrendId = TrendClass.getMethod("getTrendId");
//                                String trendId = (String) getTrendId.invoke(trendMessage.get(n), new Object[]{});
//                                log("getTrendId:::::::::" + trendId);
//
//
//                                Method getMenuList = TrendClass.getMethod("getMenuList");
//                                Object menuList = (Object) getMenuList.invoke(trendMessage.get(n), new Object[]{});
//                                log("getMenuList:::::::::" + menuList);
//
//                                Method getPosition = TrendClass.getMethod("getPosition");
//                                int position = (int) getPosition.invoke(trendMessage.get(n), new Object[]{});
//                                log("getPosition:::::::::" + position);
//
//                                Method getShowFeedId = TrendClass.getMethod("getShowFeedId");
//                                String showFeedId = (String) getShowFeedId.invoke(trendMessage.get(n), new Object[]{});
//                                log("getShowFeedId:::::::::" + showFeedId);
//
//
//                                Method getShowFeedType = TrendClass.getMethod("getShowFeedType");
//                                String showFeedType = (String) getShowFeedType.invoke(trendMessage.get(n), new Object[]{});
//                                log("getShowFeedType:::::::::" + showFeedType);
//
//
//                                Method getStyleId = TrendClass.getMethod("getStyleId");
//                                int styleId = (int) getStyleId.invoke(trendMessage.get(n), new Object[]{});
//                                log("getStyleId:::::::::" + styleId);
//
//                                Method getTrend_ext = TrendClass.getMethod("getTrend_ext");
//                                String trend_ext = (String) getTrend_ext.invoke(trendMessage.get(n), new Object[]{});
//                                log("getTrend_ext:::::::::" + trend_ext);
//
//
//                                 Method getTrendType = TrendClass.getMethod("getTrendType");
//                                String TrendType = (String) getTrendType.invoke(trendMessage.get(n), new Object[]{});
//                                log("getTrendType:::::::::" + TrendType);
//
//
//                                Method getTypeImage = TrendClass.getMethod("getTypeImage");
//                                String typeImage = (String) getTypeImage.invoke(trendMessage.get(n), new Object[]{});
//                                log("getTypeImage:::::::::" + typeImage);
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//                            }


                        }


                    }
                });

//                findAndHookMethod("com.sina.weibo.stream.b.d", lpparam.classLoader, "c", List.class, new XC_MethodHook() {
//                    @Override
//                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
//                        super.afterHookedMethod(param);
//                        log("a===============================b");
//                        final Class TrendClass = XposedHelpers.findClass("com.sina.weibo.models.Trend", lpparam.classLoader);
//
//                        final List trendMessage = (List) param.args[0];
//                        log("===============================================");
//                        log("************************************************");
//                        log("===============================================");
//
//                        log("trend#################b===" + trendMessage.size() + "&&&&&&" + trendMessage.toString());
//
//                    }
//                });


            }
        });

    }




}
