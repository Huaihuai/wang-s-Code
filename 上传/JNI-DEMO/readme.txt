使用说明

DLL目录为VC项目用于生成JNI所需的DLL文件

Demo.java 为JNI接口定义以及使用的类

编译：

1. javac Demo.java  	//编译成class文件
2. javah Demo		//生成DLL的头文件
3. java  Demo		//执行