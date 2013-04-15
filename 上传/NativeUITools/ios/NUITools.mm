//
//  File.c
//  pinkture
//
//  Created by alex chen on 12-9-3.
//  Copyright (c) 2012年 __MyCompanyName__. All rights reserved.
//

#include <stdio.h>
#include "NUIToolsCore.h"
#include "NUITools.h"
#include "CCScriptSupport.h"
#import "MyCustomDialog.h"

using namespace cocos2d;
namespace NUITools {
    
NUIToolsCore *nuitools_core;
int     m_scriptHandle = 0;

void NUITools_Init()
{
    nuitools_core = [[NUIToolsCore alloc] init];
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
    
    [nuitools_core ShowInputDialogWithTitle:[NSString stringWithUTF8String:tTitle] withMsg:[NSString stringWithUTF8String:tMessage] withContnet:[NSString stringWithUTF8String:tContent] withPlaceholder:[NSString stringWithUTF8String:tPlaceHolder] withButtons:[NSString stringWithUTF8String:tButtons]];
    
}
    
const char* NUITools_GetInputDialogResult()
{
    const char* ret = [[nuitools_core GetInputDialogResult] UTF8String];
    return ret;
}
    
void NUITools_ShowAlertDialog(char* tTitle, char* tMessage, char* tButtons)
{
    [nuitools_core ShowAlertDialogWithTitle:[NSString stringWithUTF8String:tTitle] withMsg:[NSString stringWithUTF8String:tMessage] withButtons:[NSString stringWithUTF8String:tButtons]];
}
    
int NUITools_GetAlertDialogResult()
{
    int btnret = [nuitools_core GetAlertDialogBtnResult];
    return btnret;
}

void NUITools_ShowActionSheet(char* tTitle, char* tCancelBtn, char* tDestructiveBtn, char* tButtons)
{
    [nuitools_core ShowActionSheet:[NSString stringWithUTF8String:tTitle] withCancelBtn:[NSString stringWithUTF8String:tCancelBtn] withDestructiveBtn:[NSString stringWithUTF8String:tDestructiveBtn] withButtons:[NSString stringWithUTF8String:tButtons]];
}

int NUITools_GetActionSheetResult()
{
    return [nuitools_core GetActionSheetBtnResult];
}

void NUITools_ShowIndicator(int tType ,char* tInfo)
{
    [nuitools_core ShowIndicatorWithType:tType withInfo:[NSString stringWithUTF8String:tInfo]];
}
void NUITools_ShowCompleteIndicator(bool tbAnimated, double tfDelay, char* tInfo)
{
    [nuitools_core ShowIndicatorCompleteWith:tbAnimated delay:tfDelay into:[NSString stringWithUTF8String:tInfo]];
}
void NUITools_HideIndicator()
{
    [nuitools_core HideIndicator];
}


MyCustomDialog* m_customDialog = nil;
void NUITools_ShowCustomDialog(bool tbAnimated, char* tPlaceHolder)
{
    if ( m_customDialog != nil) {
        return;
    }
    m_customDialog = [[MyCustomDialog alloc] init];
    if ( tPlaceHolder ) {
        [m_customDialog setTextPlaceHolder:[NSString stringWithUTF8String:tPlaceHolder]];
    }
    else{
        [m_customDialog setTextPlaceHolder:@""];
    }
    [m_customDialog show:tbAnimated];
}
    
void NUITools_HideCustomDialog(bool tbAnimated)
{
    if ( m_customDialog == nil) {
        return;
    }
    
    [m_customDialog hide:YES];
    [m_customDialog autorelease];
    m_customDialog = nil;
}

int NUITools_GetCustomDialogResultBtn()
{
    if ( m_customDialog == nil) {
        return -1;
    }
    
    return [m_customDialog getDialogBtnResult];
}
    
const char* NUITools_GetCustomDialogResultText()
{
    if ( m_customDialog == nil) {
        return nil;
    }
    
    return [[m_customDialog getDialogResult] UTF8String];
}

    
bool NUITools_ShowAppStoreInfo(char* appID)
{
    return [nuitools_core ShowAppStoreInfo:[NSString stringWithUTF8String:appID]];
}

}//end namespace