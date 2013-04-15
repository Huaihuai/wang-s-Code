/*
** Lua binding: NUIToolsLuaApi
** Generated automatically by tolua++-1.0.92 on Sun Nov 25 14:58:00 2012.
*/

#ifndef __cplusplus
#include "stdlib.h"
#endif
#include "string.h"

#include "tolua++.h"

/* Exported function */
TOLUA_API int  tolua_NUIToolsLuaApi_open (lua_State* tolua_S);

#include "tolua_fix.h"
#include "NUITools.h"
using namespace NUITools;

/* function to register type */
static void tolua_reg_types (lua_State* tolua_S)
{
 tolua_usertype(tolua_S,"LUA_FUNCTION");
}

/* function: NUITools_Init */
#ifndef TOLUA_DISABLE_tolua_NUIToolsLuaApi_NUITools_Init00
static int tolua_NUIToolsLuaApi_NUITools_Init00(lua_State* tolua_S)
{
#ifndef TOLUA_RELEASE
 tolua_Error tolua_err;
 if (
     !tolua_isnoobj(tolua_S,1,&tolua_err)
 )
  goto tolua_lerror;
 else
#endif
 {
  {
   NUITools_Init();
  }
 }
 return 0;
#ifndef TOLUA_RELEASE
 tolua_lerror:
 tolua_error(tolua_S,"#ferror in function 'NUITools_Init'.",&tolua_err);
 return 0;
#endif
}
#endif //#ifndef TOLUA_DISABLE

/* function: NUITools_RegisterScriptHandler */
#ifndef TOLUA_DISABLE_tolua_NUIToolsLuaApi_NUITools_RegisterScriptHandler00
static int tolua_NUIToolsLuaApi_NUITools_RegisterScriptHandler00(lua_State* tolua_S)
{
#ifndef TOLUA_RELEASE
 tolua_Error tolua_err;
 if (
     (tolua_isvaluenil(tolua_S,1,&tolua_err) || !tolua_isfunction(tolua_S,1,&tolua_err)) ||
     !tolua_isnoobj(tolua_S,2,&tolua_err)
 )
  goto tolua_lerror;
 else
#endif
 {
  int tHandler = (tolua_ref_function(tolua_S,1,0));
  {
   NUITools_RegisterScriptHandler(tHandler);
  }
 }
 return 0;
#ifndef TOLUA_RELEASE
 tolua_lerror:
 tolua_error(tolua_S,"#ferror in function 'NUITools_RegisterScriptHandler'.",&tolua_err);
 return 0;
#endif
}
#endif //#ifndef TOLUA_DISABLE

/* function: NUITools_UnregisterScriptHandler */
#ifndef TOLUA_DISABLE_tolua_NUIToolsLuaApi_NUITools_UnregisterScriptHandler00
static int tolua_NUIToolsLuaApi_NUITools_UnregisterScriptHandler00(lua_State* tolua_S)
{
#ifndef TOLUA_RELEASE
 tolua_Error tolua_err;
 if (
     !tolua_isnoobj(tolua_S,1,&tolua_err)
 )
  goto tolua_lerror;
 else
#endif
 {
  {
   NUITools_UnregisterScriptHandler();
  }
 }
 return 0;
#ifndef TOLUA_RELEASE
 tolua_lerror:
 tolua_error(tolua_S,"#ferror in function 'NUITools_UnregisterScriptHandler'.",&tolua_err);
 return 0;
#endif
}
#endif //#ifndef TOLUA_DISABLE

/* function: NUITools_ScriptCompletetCallback */
#ifndef TOLUA_DISABLE_tolua_NUIToolsLuaApi_NUITools_ScriptCompletetCallback00
static int tolua_NUIToolsLuaApi_NUITools_ScriptCompletetCallback00(lua_State* tolua_S)
{
#ifndef TOLUA_RELEASE
 tolua_Error tolua_err;
 if (
     !tolua_isnoobj(tolua_S,1,&tolua_err)
 )
  goto tolua_lerror;
 else
#endif
 {
  {
   NUITools_ScriptCompletetCallback();
  }
 }
 return 0;
#ifndef TOLUA_RELEASE
 tolua_lerror:
 tolua_error(tolua_S,"#ferror in function 'NUITools_ScriptCompletetCallback'.",&tolua_err);
 return 0;
#endif
}
#endif //#ifndef TOLUA_DISABLE

/* function: NUITools_ShowInputDialog */
#ifndef TOLUA_DISABLE_tolua_NUIToolsLuaApi_NUITools_ShowInputDialog00
static int tolua_NUIToolsLuaApi_NUITools_ShowInputDialog00(lua_State* tolua_S)
{
#ifndef TOLUA_RELEASE
 tolua_Error tolua_err;
 if (
     !tolua_isstring(tolua_S,1,0,&tolua_err) ||
     !tolua_isstring(tolua_S,2,0,&tolua_err) ||
     !tolua_isstring(tolua_S,3,0,&tolua_err) ||
     !tolua_isstring(tolua_S,4,0,&tolua_err) ||
     !tolua_isstring(tolua_S,5,0,&tolua_err) ||
     !tolua_isnoobj(tolua_S,6,&tolua_err)
 )
  goto tolua_lerror;
 else
#endif
 {
  char* tTitle = ((char*)  tolua_tostring(tolua_S,1,0));
  char* tMessage = ((char*)  tolua_tostring(tolua_S,2,0));
  char* tContent = ((char*)  tolua_tostring(tolua_S,3,0));
  char* tPlaceHolder = ((char*)  tolua_tostring(tolua_S,4,0));
  char* tButtons = ((char*)  tolua_tostring(tolua_S,5,0));
  {
   NUITools_ShowInputDialog(tTitle,tMessage,tContent,tPlaceHolder,tButtons);
  }
 }
 return 0;
#ifndef TOLUA_RELEASE
 tolua_lerror:
 tolua_error(tolua_S,"#ferror in function 'NUITools_ShowInputDialog'.",&tolua_err);
 return 0;
#endif
}
#endif //#ifndef TOLUA_DISABLE

/* function: NUITools_GetInputDialogResult */
#ifndef TOLUA_DISABLE_tolua_NUIToolsLuaApi_NUITools_GetInputDialogResult00
static int tolua_NUIToolsLuaApi_NUITools_GetInputDialogResult00(lua_State* tolua_S)
{
#ifndef TOLUA_RELEASE
 tolua_Error tolua_err;
 if (
     !tolua_isnoobj(tolua_S,1,&tolua_err)
 )
  goto tolua_lerror;
 else
#endif
 {
  {
   const char* tolua_ret = (const char*)  NUITools_GetInputDialogResult();
   tolua_pushstring(tolua_S,(const char*)tolua_ret);
  }
 }
 return 1;
#ifndef TOLUA_RELEASE
 tolua_lerror:
 tolua_error(tolua_S,"#ferror in function 'NUITools_GetInputDialogResult'.",&tolua_err);
 return 0;
#endif
}
#endif //#ifndef TOLUA_DISABLE

/* function: NUITools_ShowAlertDialog */
#ifndef TOLUA_DISABLE_tolua_NUIToolsLuaApi_NUITools_ShowAlertDialog00
static int tolua_NUIToolsLuaApi_NUITools_ShowAlertDialog00(lua_State* tolua_S)
{
#ifndef TOLUA_RELEASE
 tolua_Error tolua_err;
 if (
     !tolua_isstring(tolua_S,1,0,&tolua_err) ||
     !tolua_isstring(tolua_S,2,0,&tolua_err) ||
     !tolua_isstring(tolua_S,3,0,&tolua_err) ||
     !tolua_isnoobj(tolua_S,4,&tolua_err)
 )
  goto tolua_lerror;
 else
#endif
 {
  char* tTitle = ((char*)  tolua_tostring(tolua_S,1,0));
  char* tMessage = ((char*)  tolua_tostring(tolua_S,2,0));
  char* tButtons = ((char*)  tolua_tostring(tolua_S,3,0));
  {
   NUITools_ShowAlertDialog(tTitle,tMessage,tButtons);
  }
 }
 return 0;
#ifndef TOLUA_RELEASE
 tolua_lerror:
 tolua_error(tolua_S,"#ferror in function 'NUITools_ShowAlertDialog'.",&tolua_err);
 return 0;
#endif
}
#endif //#ifndef TOLUA_DISABLE

/* function: NUITools_GetAlertDialogResult */
#ifndef TOLUA_DISABLE_tolua_NUIToolsLuaApi_NUITools_GetAlertDialogResult00
static int tolua_NUIToolsLuaApi_NUITools_GetAlertDialogResult00(lua_State* tolua_S)
{
#ifndef TOLUA_RELEASE
 tolua_Error tolua_err;
 if (
     !tolua_isnoobj(tolua_S,1,&tolua_err)
 )
  goto tolua_lerror;
 else
#endif
 {
  {
   int tolua_ret = (int)  NUITools_GetAlertDialogResult();
   tolua_pushnumber(tolua_S,(lua_Number)tolua_ret);
  }
 }
 return 1;
#ifndef TOLUA_RELEASE
 tolua_lerror:
 tolua_error(tolua_S,"#ferror in function 'NUITools_GetAlertDialogResult'.",&tolua_err);
 return 0;
#endif
}
#endif //#ifndef TOLUA_DISABLE

/* function: NUITools_ShowActionSheet */
#ifndef TOLUA_DISABLE_tolua_NUIToolsLuaApi_NUITools_ShowActionSheet00
static int tolua_NUIToolsLuaApi_NUITools_ShowActionSheet00(lua_State* tolua_S)
{
#ifndef TOLUA_RELEASE
 tolua_Error tolua_err;
 if (
     !tolua_isstring(tolua_S,1,0,&tolua_err) ||
     !tolua_isstring(tolua_S,2,0,&tolua_err) ||
     !tolua_isstring(tolua_S,3,0,&tolua_err) ||
     !tolua_isstring(tolua_S,4,0,&tolua_err) ||
     !tolua_isnoobj(tolua_S,5,&tolua_err)
 )
  goto tolua_lerror;
 else
#endif
 {
  char* tTitle = ((char*)  tolua_tostring(tolua_S,1,0));
  char* tCancelBtn = ((char*)  tolua_tostring(tolua_S,2,0));
  char* tDestructiveBtn = ((char*)  tolua_tostring(tolua_S,3,0));
  char* tButtons = ((char*)  tolua_tostring(tolua_S,4,0));
  {
   NUITools_ShowActionSheet(tTitle,tCancelBtn,tDestructiveBtn,tButtons);
  }
 }
 return 0;
#ifndef TOLUA_RELEASE
 tolua_lerror:
 tolua_error(tolua_S,"#ferror in function 'NUITools_ShowActionSheet'.",&tolua_err);
 return 0;
#endif
}
#endif //#ifndef TOLUA_DISABLE

/* function: NUITools_GetActionSheetResult */
#ifndef TOLUA_DISABLE_tolua_NUIToolsLuaApi_NUITools_GetActionSheetResult00
static int tolua_NUIToolsLuaApi_NUITools_GetActionSheetResult00(lua_State* tolua_S)
{
#ifndef TOLUA_RELEASE
 tolua_Error tolua_err;
 if (
     !tolua_isnoobj(tolua_S,1,&tolua_err)
 )
  goto tolua_lerror;
 else
#endif
 {
  {
   int tolua_ret = (int)  NUITools_GetActionSheetResult();
   tolua_pushnumber(tolua_S,(lua_Number)tolua_ret);
  }
 }
 return 1;
#ifndef TOLUA_RELEASE
 tolua_lerror:
 tolua_error(tolua_S,"#ferror in function 'NUITools_GetActionSheetResult'.",&tolua_err);
 return 0;
#endif
}
#endif //#ifndef TOLUA_DISABLE

/* function: NUITools_ShowIndicator */
#ifndef TOLUA_DISABLE_tolua_NUIToolsLuaApi_NUITools_ShowIndicator00
static int tolua_NUIToolsLuaApi_NUITools_ShowIndicator00(lua_State* tolua_S)
{
#ifndef TOLUA_RELEASE
 tolua_Error tolua_err;
 if (
     !tolua_isnumber(tolua_S,1,0,&tolua_err) ||
     !tolua_isstring(tolua_S,2,0,&tolua_err) ||
     !tolua_isnoobj(tolua_S,3,&tolua_err)
 )
  goto tolua_lerror;
 else
#endif
 {
  int tType = ((int)  tolua_tonumber(tolua_S,1,0));
  char* tInfo = ((char*)  tolua_tostring(tolua_S,2,0));
  {
   NUITools_ShowIndicator(tType,tInfo);
  }
 }
 return 0;
#ifndef TOLUA_RELEASE
 tolua_lerror:
 tolua_error(tolua_S,"#ferror in function 'NUITools_ShowIndicator'.",&tolua_err);
 return 0;
#endif
}
#endif //#ifndef TOLUA_DISABLE

/* function: NUITools_HideIndicator */
#ifndef TOLUA_DISABLE_tolua_NUIToolsLuaApi_NUITools_HideIndicator00
static int tolua_NUIToolsLuaApi_NUITools_HideIndicator00(lua_State* tolua_S)
{
#ifndef TOLUA_RELEASE
 tolua_Error tolua_err;
 if (
     !tolua_isnoobj(tolua_S,1,&tolua_err)
 )
  goto tolua_lerror;
 else
#endif
 {
  {
   NUITools_HideIndicator();
  }
 }
 return 0;
#ifndef TOLUA_RELEASE
 tolua_lerror:
 tolua_error(tolua_S,"#ferror in function 'NUITools_HideIndicator'.",&tolua_err);
 return 0;
#endif
}
#endif //#ifndef TOLUA_DISABLE

/* function: NUITools_ShowCompleteIndicator */
#ifndef TOLUA_DISABLE_tolua_NUIToolsLuaApi_NUITools_ShowCompleteIndicator00
static int tolua_NUIToolsLuaApi_NUITools_ShowCompleteIndicator00(lua_State* tolua_S)
{
#ifndef TOLUA_RELEASE
 tolua_Error tolua_err;
 if (
     !tolua_isboolean(tolua_S,1,0,&tolua_err) ||
     !tolua_isnumber(tolua_S,2,0,&tolua_err) ||
     !tolua_isstring(tolua_S,3,0,&tolua_err) ||
     !tolua_isnoobj(tolua_S,4,&tolua_err)
 )
  goto tolua_lerror;
 else
#endif
 {
  bool tbAnimated = ((bool)  tolua_toboolean(tolua_S,1,0));
  double tfDelay = ((double)  tolua_tonumber(tolua_S,2,0));
  char* tInfo = ((char*)  tolua_tostring(tolua_S,3,0));
  {
   NUITools_ShowCompleteIndicator(tbAnimated,tfDelay,tInfo);
  }
 }
 return 0;
#ifndef TOLUA_RELEASE
 tolua_lerror:
 tolua_error(tolua_S,"#ferror in function 'NUITools_ShowCompleteIndicator'.",&tolua_err);
 return 0;
#endif
}
#endif //#ifndef TOLUA_DISABLE

/* function: NUITools_ShowCustomDialog */
#ifndef TOLUA_DISABLE_tolua_NUIToolsLuaApi_NUITools_ShowCustomDialog00
static int tolua_NUIToolsLuaApi_NUITools_ShowCustomDialog00(lua_State* tolua_S)
{
#ifndef TOLUA_RELEASE
 tolua_Error tolua_err;
 if (
     !tolua_isboolean(tolua_S,1,0,&tolua_err) ||
     !tolua_isstring(tolua_S,2,0,&tolua_err) ||
     !tolua_isnoobj(tolua_S,3,&tolua_err)
 )
  goto tolua_lerror;
 else
#endif
 {
  bool tbAnimated = ((bool)  tolua_toboolean(tolua_S,1,0));
  char* tPlaceHolder = ((char*)  tolua_tostring(tolua_S,2,0));
  {
   NUITools_ShowCustomDialog(tbAnimated,tPlaceHolder);
  }
 }
 return 0;
#ifndef TOLUA_RELEASE
 tolua_lerror:
 tolua_error(tolua_S,"#ferror in function 'NUITools_ShowCustomDialog'.",&tolua_err);
 return 0;
#endif
}
#endif //#ifndef TOLUA_DISABLE

/* function: NUITools_HideCustomDialog */
#ifndef TOLUA_DISABLE_tolua_NUIToolsLuaApi_NUITools_HideCustomDialog00
static int tolua_NUIToolsLuaApi_NUITools_HideCustomDialog00(lua_State* tolua_S)
{
#ifndef TOLUA_RELEASE
 tolua_Error tolua_err;
 if (
     !tolua_isboolean(tolua_S,1,0,&tolua_err) ||
     !tolua_isnoobj(tolua_S,2,&tolua_err)
 )
  goto tolua_lerror;
 else
#endif
 {
  bool tbAnimated = ((bool)  tolua_toboolean(tolua_S,1,0));
  {
   NUITools_HideCustomDialog(tbAnimated);
  }
 }
 return 0;
#ifndef TOLUA_RELEASE
 tolua_lerror:
 tolua_error(tolua_S,"#ferror in function 'NUITools_HideCustomDialog'.",&tolua_err);
 return 0;
#endif
}
#endif //#ifndef TOLUA_DISABLE

/* function: NUITools_GetCustomDialogResultBtn */
#ifndef TOLUA_DISABLE_tolua_NUIToolsLuaApi_NUITools_GetCustomDialogResultBtn00
static int tolua_NUIToolsLuaApi_NUITools_GetCustomDialogResultBtn00(lua_State* tolua_S)
{
#ifndef TOLUA_RELEASE
 tolua_Error tolua_err;
 if (
     !tolua_isnoobj(tolua_S,1,&tolua_err)
 )
  goto tolua_lerror;
 else
#endif
 {
  {
   int tolua_ret = (int)  NUITools_GetCustomDialogResultBtn();
   tolua_pushnumber(tolua_S,(lua_Number)tolua_ret);
  }
 }
 return 1;
#ifndef TOLUA_RELEASE
 tolua_lerror:
 tolua_error(tolua_S,"#ferror in function 'NUITools_GetCustomDialogResultBtn'.",&tolua_err);
 return 0;
#endif
}
#endif //#ifndef TOLUA_DISABLE

/* function: NUITools_GetCustomDialogResultText */
#ifndef TOLUA_DISABLE_tolua_NUIToolsLuaApi_NUITools_GetCustomDialogResultText00
static int tolua_NUIToolsLuaApi_NUITools_GetCustomDialogResultText00(lua_State* tolua_S)
{
#ifndef TOLUA_RELEASE
 tolua_Error tolua_err;
 if (
     !tolua_isnoobj(tolua_S,1,&tolua_err)
 )
  goto tolua_lerror;
 else
#endif
 {
  {
   const char* tolua_ret = (const char*)  NUITools_GetCustomDialogResultText();
   tolua_pushstring(tolua_S,(const char*)tolua_ret);
  }
 }
 return 1;
#ifndef TOLUA_RELEASE
 tolua_lerror:
 tolua_error(tolua_S,"#ferror in function 'NUITools_GetCustomDialogResultText'.",&tolua_err);
 return 0;
#endif
}
#endif //#ifndef TOLUA_DISABLE

/* function: NUITools_ShowAppStoreInfo */
#ifndef TOLUA_DISABLE_tolua_NUIToolsLuaApi_NUITools_ShowAppStoreInfo00
static int tolua_NUIToolsLuaApi_NUITools_ShowAppStoreInfo00(lua_State* tolua_S)
{
#ifndef TOLUA_RELEASE
 tolua_Error tolua_err;
 if (
     !tolua_isstring(tolua_S,1,0,&tolua_err) ||
     !tolua_isnoobj(tolua_S,2,&tolua_err)
 )
  goto tolua_lerror;
 else
#endif
 {
  char* appID = ((char*)  tolua_tostring(tolua_S,1,0));
  {
   bool tolua_ret = (bool)  NUITools_ShowAppStoreInfo(appID);
   tolua_pushboolean(tolua_S,(bool)tolua_ret);
  }
 }
 return 1;
#ifndef TOLUA_RELEASE
 tolua_lerror:
 tolua_error(tolua_S,"#ferror in function 'NUITools_ShowAppStoreInfo'.",&tolua_err);
 return 0;
#endif
}
#endif //#ifndef TOLUA_DISABLE

/* Open function */
TOLUA_API int tolua_NUIToolsLuaApi_open (lua_State* tolua_S)
{
 tolua_open(tolua_S);
 tolua_reg_types(tolua_S);
 tolua_module(tolua_S,NULL,0);
 tolua_beginmodule(tolua_S,NULL);
  tolua_function(tolua_S,"NUITools_Init",tolua_NUIToolsLuaApi_NUITools_Init00);
  tolua_function(tolua_S,"NUITools_RegisterScriptHandler",tolua_NUIToolsLuaApi_NUITools_RegisterScriptHandler00);
  tolua_function(tolua_S,"NUITools_UnregisterScriptHandler",tolua_NUIToolsLuaApi_NUITools_UnregisterScriptHandler00);
  tolua_function(tolua_S,"NUITools_ScriptCompletetCallback",tolua_NUIToolsLuaApi_NUITools_ScriptCompletetCallback00);
  tolua_function(tolua_S,"NUITools_ShowInputDialog",tolua_NUIToolsLuaApi_NUITools_ShowInputDialog00);
  tolua_function(tolua_S,"NUITools_GetInputDialogResult",tolua_NUIToolsLuaApi_NUITools_GetInputDialogResult00);
  tolua_function(tolua_S,"NUITools_ShowAlertDialog",tolua_NUIToolsLuaApi_NUITools_ShowAlertDialog00);
  tolua_function(tolua_S,"NUITools_GetAlertDialogResult",tolua_NUIToolsLuaApi_NUITools_GetAlertDialogResult00);
  tolua_function(tolua_S,"NUITools_ShowActionSheet",tolua_NUIToolsLuaApi_NUITools_ShowActionSheet00);
  tolua_function(tolua_S,"NUITools_GetActionSheetResult",tolua_NUIToolsLuaApi_NUITools_GetActionSheetResult00);
  tolua_function(tolua_S,"NUITools_ShowIndicator",tolua_NUIToolsLuaApi_NUITools_ShowIndicator00);
  tolua_function(tolua_S,"NUITools_HideIndicator",tolua_NUIToolsLuaApi_NUITools_HideIndicator00);
  tolua_function(tolua_S,"NUITools_ShowCompleteIndicator",tolua_NUIToolsLuaApi_NUITools_ShowCompleteIndicator00);
  tolua_function(tolua_S,"NUITools_ShowCustomDialog",tolua_NUIToolsLuaApi_NUITools_ShowCustomDialog00);
  tolua_function(tolua_S,"NUITools_HideCustomDialog",tolua_NUIToolsLuaApi_NUITools_HideCustomDialog00);
  tolua_function(tolua_S,"NUITools_GetCustomDialogResultBtn",tolua_NUIToolsLuaApi_NUITools_GetCustomDialogResultBtn00);
  tolua_function(tolua_S,"NUITools_GetCustomDialogResultText",tolua_NUIToolsLuaApi_NUITools_GetCustomDialogResultText00);
  tolua_function(tolua_S,"NUITools_ShowAppStoreInfo",tolua_NUIToolsLuaApi_NUITools_ShowAppStoreInfo00);
 tolua_endmodule(tolua_S);
 return 1;
}


#if defined(LUA_VERSION_NUM) && LUA_VERSION_NUM >= 501
 TOLUA_API int luaopen_NUIToolsLuaApi (lua_State* tolua_S) {
 return tolua_NUIToolsLuaApi_open(tolua_S);
};
#endif

