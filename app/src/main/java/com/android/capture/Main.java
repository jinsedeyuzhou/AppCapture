package com.android.capture;

import com.android.capture.hooks.SinaHot;
import com.android.capture.hooks.WeiBo;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.IXposedHookZygoteInit;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

import static de.robv.android.xposed.XposedBridge.log;


/**
 * Created by Berkeley on 8/24/16.
 */
public class Main implements IXposedHookLoadPackage ,IXposedHookZygoteInit
{
    @Override
    public void initZygote(StartupParam startupParam) throws Throwable {
        log("ML: Loaded Main class");

    }


    @Override
    public void handleLoadPackage(LoadPackageParam lpparam) throws Throwable {
//        if (lpparam.packageName.equals(KuaiBaoApp.PACKAGE_NAME))
//        {
//            log("KuaibaoApp Hooked");
//            KuaiBaoApp.init(lpparam);
//        }

//
//        if (lpparam.packageName.equals(WeiBo.PACKAGE_NAME))
//        {
//            log(WeiBo.PACKAGE_NAME+" Hooked");
//            WeiBo.init(lpparam);
////            JiKeTest.init(lpparam);
//
//        }
        if (lpparam.packageName.equals(WeiBo.PACKAGE_NAME))
        {
            log(WeiBo.PACKAGE_NAME+" Hooked");
            SinaHot.init(lpparam);
//            JiKeTest.init(lpparam);

        }
//
//        if (lpparam.packageName.equals(JiKe.PACKAGE_NAME))
//        {
//            log(JiKe.PACKAGE_NAME+" Hooked");
//            JiKe.init(lpparam);
//
//        }



    }



}
