package cn.itcast.videoplayer;

import java.io.File;

import android.app.Activity;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Bundle;
import android.os.Environment;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {
    private EditText nameText;
    private String path;
    private MediaPlayer mediaPlayer;
    private SurfaceView surfaceView;
    private boolean pause;
    private int position;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        mediaPlayer = new MediaPlayer();
        nameText = (EditText) this.findViewById(R.id.filename);
        surfaceView = (SurfaceView) this.findViewById(R.id.surfaceView);
        //把输送给surfaceView的视频画面，直接显示到屏幕上,不要维持它自身的缓冲区
        surfaceView.getHolder().setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		//显示画面的分辨率
        surfaceView.getHolder().setFixedSize(176, 144);
		//保持屏幕不要锁屏
        surfaceView.getHolder().setKeepScreenOn(true);
		//设置surfaceView对象被重新创建的监听对象
        surfaceView.getHolder().addCallback(new SurfaceCallback());
    }
    
	//当SurfaceView所在的Activity离开了前台，SurfaceView会被destory,当Activity又重新回到前台时，
	//SurfaceView会在onResume()方法之后被重新创建。
    private final class SurfaceCallback implements Callback{
		//当surfaceView重新被绘制处理，又开始播放视频
		public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
		}
		public void surfaceCreated(SurfaceHolder holder) {
			if(position>0 && path!=null){
				play(position);
				position = 0;
			}
		}
		//当Activity离开前台，surfaceView摧毁就要停止播放
		public void surfaceDestroyed(SurfaceHolder holder) {
			if(mediaPlayer.isPlaying()){
				position = mediaPlayer.getCurrentPosition();
				mediaPlayer.stop();
			}
		}
    }

	@Override
	protected void onDestroy() {
		//必须先释放MediaPlayer对象
    	mediaPlayer.release();
    	mediaPlayer = null;
		super.onDestroy();
	}

	public void mediaplay(View v){
    	switch (v.getId()) {
		case R.id.playbutton:
			String filename = nameText.getText().toString();
			if(filename.startsWith("http")){
				path = filename;
				play(0);
			}else{
				File file = new File(Environment.getExternalStorageDirectory(), filename);
				if(file.exists()){
					path = file.getAbsolutePath();
					play(0);
				}else{
					path = null;
					Toast.makeText(this, R.string.filenoexsit, 1).show();
				}
			}
		
			break;

		case R.id.pausebutton:
			if(mediaPlayer.isPlaying()){
				mediaPlayer.pause();
				pause = true;
			}else{
				if(pause){
					mediaPlayer.start();
					pause = false;
				}
			}
			break;
			
		case R.id.resetbutton:
			if(mediaPlayer.isPlaying()){
			    //重播，就重头播放
				mediaPlayer.seekTo(0);
			}else{
				if(path!=null){
					play(0);
				}
			}
			break;
		case R.id.stopbutton:
			if(mediaPlayer.isPlaying()){
				mediaPlayer.stop();
			}
			break;
		}
    }

	private void play(int position) {
		try {
			mediaPlayer.reset();   //重置播放状态
			mediaPlayer.setDataSource(path);   //设置播放路径
			mediaPlayer.setDisplay(surfaceView.getHolder());   //设置播放的控制对象
			mediaPlayer.prepare();//缓冲
			//监听缓冲是否完成
			mediaPlayer.setOnPreparedListener(new PrepareListener(position));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private final class PrepareListener implements OnPreparedListener{
		private int position;
		
		//实现获取当前的播放位置
		public PrepareListener(int position) {
		     this.position = position;
		}

		public void onPrepared(MediaPlayer mp) {
			mediaPlayer.start();//播放视频
			if(position>0) mediaPlayer.seekTo(position);
		}
	}
}