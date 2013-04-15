
public class Demo{

	static{
		System.loadLibrary("DLL");
	}

	public native void sayHello(String who);
	
	public static void main(String[] args){
		
		new Demo().sayHello("Αυ¶¬");
	}

}