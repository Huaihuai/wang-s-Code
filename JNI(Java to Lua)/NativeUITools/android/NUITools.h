//
//  Header.h
//  pinkture
//
//  Created by alex chen on 12-9-3.
//  Copyright (c) 2012年 cimu. All rights reserved.
//

#ifndef pinkture_Header_h
#define pinkture_Header_h


namespace NUITools {
    
void NUITools_Init();

//process with lua script , you know that
void NUITools_RegisterScriptHandler(int tHandler);
void NUITools_UnregisterScriptHandler();
void NUITools_ScriptCompletetCallback();

void NUITools_CompletetCallback();

    
//then input dialog ,,a dialog with a textfield can input text
void NUITools_ShowInputDialog(char* tTitle, char* tMessage, char* tContent, char* tPlaceHolder, char* tButtons);
const char* NUITools_GetInputDialogResult();
    
//then normal alert dialog in ios , then tButtons have format "NO|YES" : this mean have two btn NO and YES
void NUITools_ShowAlertDialog(char* tTitle, char* tMessage, char* tButtons);
//this well return the index of btn array ,index start with 0
int NUITools_GetAlertDialogResult();
    
void NUITools_ShowActionSheet(char* tTitle, char* tCancelBtn, char* tDestructiveBtn, char* tButtons);
int NUITools_GetActionSheetResult();

//show ios like warit circle
// tType : 1,black bg and block touchevent, 2,donot have black bg and block touchevent
//3,black bg and donot block touchevent, 4, donot have black bg and donot block touchevent
void NUITools_ShowIndicator(int tType ,char* tInfo);
void NUITools_HideIndicator();
void NUITools_ShowCompleteIndicator(bool tbAnimated, double tfDelay, char* tInfo);
    
//for custom dialog
void NUITools_ShowCustomDialog(bool tbAnimated, char* tPlaceHolder);
void NUITools_HideCustomDialog(bool tbAnimated);
int NUITools_GetCustomDialogResultBtn();
const char* NUITools_GetCustomDialogResultText();
    
//ios 6 fearture
bool NUITools_ShowAppStoreInfo(char* appID);
    
}
#endif
