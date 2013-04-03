package cn.itcast.pageload;

import java.util.ArrayList;
import java.util.List;

import cn.itcast.service.DataService;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends Activity {
    private ListView listView;
    private List<String> data = new ArrayList<String>();
    ArrayAdapter<String> adapter;
    View footer;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        footer = getLayoutInflater().inflate(R.layout.footer, null);
        
        listView = (ListView) this.findViewById(R.id.listView);
        listView.setOnScrollListener(new ScrollListener());
        
		//addAll():加入数据集;add():加入单条数据
        data.addAll(DataService.getData(0, 20));        
        adapter = new ArrayAdapter<String>(this, R.layout.listview_item, R.id.textView, data);
        //必须在setAdapter之前把head和Footer设置好,告诉ListView后面要使用页首和页脚功能
		listView.addFooterView(footer);
        listView.setAdapter(adapter);
		//由于在未加载数据时是不需要看到页脚，故声明完后必须去掉
        listView.removeFooterView(footer);
    }
    
    private int number = 20;//每次获取多少条数据
    private int maxpage = 5;//总共有多少页
    private boolean loadfinish = true;
    private final class ScrollListener implements OnScrollListener{
		public void onScrollStateChanged(AbsListView view, int scrollState) {
			Log.i("MainActivity", "onScrollStateChanged(scrollState="+ scrollState+ ")");
		}
	 /**
	 * 正在滚动事件（第一次启动时就会触发该事件，下面的数据分别为0，当前屏幕上的条目数，当前页加载的条目数）
	 * @param view 当前滚动的ListView
	 * @param firstVisibleItem 当前屏幕的第一条条目
	 * @param visibleItemCount 当前屏幕中显示多少条目
	 * @param totalItemCount 当前页加载了多少条目
	 */
		public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
			Log.i("MainActivity", "onScroll(firstVisibleItem="+ firstVisibleItem+ ",visibleItemCount="+
					visibleItemCount+ ",totalItemCount="+ totalItemCount+ ")");
			
			final int loadtotal = totalItemCount;
			int lastItemid = listView.getLastVisiblePosition();//获取当前屏幕最后Item的ID
			if((lastItemid+1) == totalItemCount){//达到数据的最后一条记录
				if(totalItemCount > 0){
					//当前页
					int currentpage = totalItemCount%number == 0 ? totalItemCount/number : totalItemCount/number+1;
					int nextpage = currentpage + 1;//下一页
					//loadfinish:指示数据有没有加载完成（因为onScroll()一直都在被执行，必须要当加载完后才能进行下一次加载）
					if(nextpage <= maxpage && loadfinish){
						loadfinish = false;
						//加载数据之前添加页脚(放在ListView最后)
						listView.addFooterView(footer);
						
						//加载数据是耗时工作，需要子线程中完成，以免阻塞主线程，导致无服务相应
						new Thread(new Runnable() {						
							public void run() {
								try {
									Thread.sleep(3000);
								} catch (InterruptedException e) {
									e.printStackTrace();
								}
								/*getData()方法传入的参数一般为需要加载的数量和从哪个位子开始
								  读取，通常是向服务器请求数据*/
								List<String> result = DataService.getData(loadtotal, number);
								handler.sendMessage(handler.obtainMessage(100, result));
							}
						}).start();
					}		
				}
						
			}
		}
    }
    //在子线程中无法加载界面更新，所以需要创建消息机制发送消息到队列中通知界面更新（Handler在主线程完成工作）
    Handler handler = new Handler(){
		public void handleMessage(Message msg) {
			data.addAll((List<String>) msg.obj);
			//告诉ListView数据已经发生改变(ListView更新界面显示)
			adapter.notifyDataSetChanged();     //特别注意：data是List类型，而且已经被加载到Adapter中，以后如果
			                                    //再向data中加入数据，直接会使得Adapter数据更新

			//getFooterViewsCount()>0:脚注是加载FooterView上的
			if(listView.getFooterViewsCount() > 0) listView.removeFooterView(footer);
			loadfinish = true;
		}    	
    };
    
}