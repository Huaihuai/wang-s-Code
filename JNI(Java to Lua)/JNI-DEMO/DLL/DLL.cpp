// DLL.cpp : Defines the entry point for the DLL application.
//

#include "stdafx.h"
#include "../Demo.h"

int JStringToChar(JNIEnv *env, jstring str, LPTSTR desc, int desc_len);

BOOL APIENTRY DllMain( HANDLE hModule, 
                       DWORD  ul_reason_for_call, 
                       LPVOID lpReserved
					 )
{
    return TRUE;
}

JNIEXPORT void JNICALL Java_Demo_sayHello(JNIEnv * env, jobject obj, jstring name)
{	
	TCHAR t_name[128];
	JStringToChar(env,name,t_name,128);
	printf("%s你好,欢迎光临JNI的世界.",t_name);
}


/**
第一个参数是虚拟机的环境指针
第二个参数为待转换的Java字符串定义
第三个参数是本地存储转换后字符串的内存块
第三个参数是内存块的大小
*/
int JStringToChar(JNIEnv *env, jstring str, LPTSTR desc, int desc_len)
{
	int len = 0;
	if(desc==NULL||str==NULL)
		return -1;
	wchar_t *w_buffer = new wchar_t[1024];
	ZeroMemory(w_buffer,1024*sizeof(wchar_t));
	wcscpy(w_buffer,env->GetStringChars(str,0));
	env->ReleaseStringChars(str,w_buffer);
	ZeroMemory(desc,desc_len);
	len = WideCharToMultiByte(CP_ACP,0,w_buffer,1024,desc,desc_len,NULL,NULL);
	if(len>0 && len<desc_len)
		desc[len]=0;
	delete[] w_buffer;
	return strlen(desc);
}