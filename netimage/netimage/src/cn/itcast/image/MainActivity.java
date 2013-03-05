package cn.itcast.image;

import cn.itcast.service.ImageService;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends Activity {
    private EditText pathText;
    private ImageView imageView;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        pathText = (EditText) this.findViewById(R.id.imagepath);
        imageView = (ImageView) this.findViewById(R.id.imageView);
        Button button = (Button) this.findViewById(R.id.button);
        button.setOnClickListener(new ButtonClickListener());
    }
    
    private final class ButtonClickListener implements View.OnClickListener{

    	public void onClick(View v) {
			String path = pathText.getText().toString();
			try{
				byte[] data = ImageService.getImage(path);
				/**
				 * function作用：转换数据得到位图
				 * param说明：.decodeByteArray(转换的二进制数据，截取数据其实位置，截取数据长度)
				 */
				Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
				imageView.setImageBitmap(bitmap);//显示图片
			}catch (Exception e) {
				e.printStackTrace();
				Toast.makeText(getApplicationContext(), R.string.error, 1).show();
			}
		}
    }
}