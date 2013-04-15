//
//  File.c
//  pinkture
//
//  Created by alex chen on 12-9-3.
//  Copyright (c) 2012年 __MyCompanyName__. All rights reserved.
//

#include <stdio.h>
#include <string>
#include "NUITools.h"
#include "CCScriptSupport.h"
#include "platform/android/jni/JniHelper.h"


#define JAVA_UITOOLS "com/cimu/NUITools/NUIToolsCore"


#include <android/log.h>
#define  LOG_TAG    "NUITOOLS"  
#define  LOGD(...)  __android_log_print(ANDROID_LOG_DEBUG,LOG_TAG,__VA_ARGS__)  

using namespace cocos2d;
namespace NUITools {
int     m_scriptHandle = 0;

jobject NUITools_GetJavaObj()
{
	jobject java_obj = NULL;
	JniMethodInfo minfo;
    bool isHave = JniHelper::getStaticMethodInfo(minfo,JAVA_UITOOLS,"getInstanceC", "()Ljava/lang/Object;");
    if (isHave) {
    	java_obj = minfo.env->CallStaticObjectMethod(minfo.classID, minfo.methodID);
    }
    return java_obj;
}

void NUITools_Init()
{

}

void NUITools_RegisterScriptHandler(int tHandler)
{
    m_scriptHandle = tHandler;
}
void NUITools_UnregisterScriptHandler()
{
     m_scriptHandle = 0;
}

void NUITools_ScriptCompletetCallback()
{
    if (m_scriptHandle != 0 ) {
        CCScriptEngineManager::executeFunctionByRefID(m_scriptHandle);
    }
}

void NUITools_CompletetCallback()
{
    NUITools_ScriptCompletetCallback();
}

void NUITools_ShowInputDialog(char* tTitle, char* tMessage, char* tContent, char* tPlaceHolder, char* tButtons)
{
	LOGD("NUITools_ShowInputDialog   ");

   	jobject javaObj = NUITools_GetJavaObj();
    JniMethodInfo minfo;
    bool isHave = JniHelper::getMethodInfo(minfo,  
                                           JAVA_UITOOLS,
                                           "showInputDialogC", 
                                           "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V");
    if (isHave) {
    	LOGD("NUITools_ShowInputDialog  call java ");
        jstring stringArg1 = minfo.env->NewStringUTF(tTitle);
        jstring stringArg2 = minfo.env->NewStringUTF(tMessage);
        jstring stringArg3 = minfo.env->NewStringUTF(tContent);
        jstring stringArg4 = minfo.env->NewStringUTF(tPlaceHolder);
        jstring stringArg5 = minfo.env->NewStringUTF(tButtons);
        minfo.env->CallVoidMethod(javaObj,minfo.methodID,stringArg1,stringArg2,stringArg3,stringArg4,stringArg5);

    }
}

const char* NUITools_GetInputDialogResult()
{
	jobject javaObj = NUITools_GetJavaObj();
	JniMethodInfo minfo;
    bool isHave = JniHelper::getMethodInfo(minfo,  
                                           JAVA_UITOOLS,   
                                           "GetInputDialogResult", 
                                           "()Ljava/lang/String;");
    if (isHave) {
    	jstring trtrtr;
    	// minfo.env->CallObjectMethod(minfo.env, trtrtr, 100);
        jobject tret = minfo.env->CallObjectMethod(javaObj, minfo.methodID);
        jstring tretStr = (jstring)tret;
        const char *nativeString = minfo.env->GetStringUTFChars(tretStr, 0);

        LOGD(nativeString);
		std::string tStr = std::string(nativeString);

        minfo.env->ReleaseStringUTFChars(tretStr, nativeString);
        return tStr.c_str();
    }
    return NULL;
}
//have limit donot more then 3 btns
void NUITools_ShowAlertDialog(char* tTitle, char* tMessage, char* tButtons)
{
	jobject javaObj = NUITools_GetJavaObj();
    JniMethodInfo minfo;
    bool isHave = JniHelper::getMethodInfo(minfo,  
                                           JAVA_UITOOLS,
                                           "showAlertDialogC", 
                                           "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V");
    if (isHave) {
        jstring stringArg1 = minfo.env->NewStringUTF(tTitle);
        jstring stringArg2 = minfo.env->NewStringUTF(tMessage);
        jstring stringArg3 = minfo.env->NewStringUTF(tButtons);
        minfo.env->CallVoidMethod(javaObj,minfo.methodID,stringArg1,stringArg2,stringArg3);
    }
}
    
int NUITools_GetAlertDialogResult()
{
	jobject javaObj = NUITools_GetJavaObj();
	JniMethodInfo minfo;
    bool isHave = JniHelper::getMethodInfo(minfo,  
                                           JAVA_UITOOLS,   
                                           "GetAlertDialogResult", 
                                           "()I");
    if (isHave) {
        return minfo.env->CallIntMethod(javaObj,minfo.methodID);
    }
    return -1;
}

void NUITools_ShowActionSheet(char* tTitle, char* tCancelBtn, char* tDestructiveBtn, char* tButtons)
{
    jobject javaObj = NUITools_GetJavaObj();
    JniMethodInfo minfo;
    bool isHave = JniHelper::getMethodInfo(minfo,  
                                           JAVA_UITOOLS,
                                           "showListDialogC", 
                                           "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V");
    if (isHave) {
        jstring stringArg1 = minfo.env->NewStringUTF(tTitle);
        jstring stringArg2 = minfo.env->NewStringUTF(tCancelBtn);
        jstring stringArg3 = minfo.env->NewStringUTF(tDestructiveBtn);
        jstring stringArg4 = minfo.env->NewStringUTF(tButtons);
        minfo.env->CallVoidMethod(javaObj,minfo.methodID,stringArg1,stringArg2,stringArg3,stringArg4);
    }
}

int NUITools_GetActionSheetResult()
{
     jobject javaObj = NUITools_GetJavaObj();
    JniMethodInfo minfo;
    bool isHave = JniHelper::getMethodInfo(minfo,  
                                           JAVA_UITOOLS,   
                                           "GetListDialogResult", 
                                           "()I");
    if (isHave) {
        return minfo.env->CallIntMethod(javaObj,minfo.methodID);
    }
    return -1;
}

//type useless in here
void NUITools_ShowIndicator(int tType ,char* tInfo)
{
	jobject javaObj = NUITools_GetJavaObj();
    JniMethodInfo minfo;
    bool isHave = JniHelper::getMethodInfo(minfo,  
                                           JAVA_UITOOLS,   
                                           "showProgressC", 
                                           "(Ljava/lang/String;)V");
    if (isHave) {
        jstring stringArg1 = minfo.env->NewStringUTF(tInfo);
        minfo.env->CallVoidMethod(javaObj,minfo.methodID,stringArg1);
    }
}

//tbAnimated, tfDelay useless in android, remind;
void NUITools_ShowCompleteIndicator(bool tbAnimated, double tfDelay, char* tInfo)
{
 	jobject javaObj = NUITools_GetJavaObj();
    JniMethodInfo minfo;
    bool isHave = JniHelper::getMethodInfo(minfo,  
                                           JAVA_UITOOLS,   
                                           "showToastC", 
                                           "(ZDLjava/lang/String;)V");
    if (isHave) {
        jstring stringArg1 = minfo.env->NewStringUTF(tInfo);
        minfo.env->CallVoidMethod(javaObj,minfo.methodID,tbAnimated,tfDelay,stringArg1);
    }   
}
void NUITools_HideIndicator()
{
	jobject javaObj = NUITools_GetJavaObj();
    JniMethodInfo minfo;
    bool isHave = JniHelper::getMethodInfo(minfo,
                                           JAVA_UITOOLS,
                                           "diemessProgress", 
                                           "()V");
    if (isHave) {
        minfo.env->CallVoidMethod(javaObj,minfo.methodID);
    }
}

void NUITools_ShowCustomDialog(bool tbAnimated, char* tPlaceHolder)
{

}
    
void NUITools_HideCustomDialog(bool tbAnimated)
{
}

int NUITools_GetCustomDialogResultBtn()
{

}
    
const char* NUITools_GetCustomDialogResultText()
{

}

    
bool NUITools_ShowAppStoreInfo(char* appID)
{
    
}

}//end namespace


extern "C"
{
    void Java_com_cimu_NUITools_NUIToolsCore_NUIToolsCompleteCallback(JNIEnv *tEnv, jclass tCls)
    {
        NUITools::NUITools_ScriptCompletetCallback();
    }
}