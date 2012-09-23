package com.application.memo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.TwoLineListItem;

public class MemoList extends ListActivity {
	
	//アクティビティOnCreateメソッド
	public void onCreate(Bundle savedInstanceState){
		
		//ActivityクラスのOnCreateを実行
		super.onCreate(savedInstanceState);
		
		//レイアウト設定ファイルを指定
		setContentView(R.layout.list_memo);
	    		
        //SimpleAdapterのインスタンス生成
        SimpleAdapter adapter = new SimpleAdapter(
        		this,
        		dataRead(),
        		android.R.layout.simple_list_item_2,
        		new String[]{"subject","date"},
        		new int[]{android.R.id.text1, android.R.id.text2});
        
        //リスト表示
        this.setListAdapter(adapter);
 	}
	
	//データ読み込み
	public  List<Map<String,String>> dataRead(){
		
			//リスト表示用
			List<Map<String,String>> listData = new ArrayList<Map<String,String>>();
			
			//DB作成
			MemoHelper helper = new MemoHelper(this);
			MemoSQLMethod memodb = new MemoSQLMethod(helper);
			
			//データ取得
			Cursor cursor = memodb.selectSQLMethod(null);
			
			if(cursor.getCount() == 0){
				//TextViewオブジェクト取得
				TextView view = (TextView)findViewById(R.id.warning);
				view.setVisibility(View.VISIBLE);
				view.setText("保存されているメモはありません！");
			}
			
			//リスト生成
			while(cursor.moveToNext()){
				Map<String,String> data = new HashMap<String,String>();
				data.put("subject", cursor.getString(0) + "　件名：" +cursor.getString(1));
				data.put("date", "作成日時：" + cursor.getString(3));
				listData.add(data);
			}
				
			memodb.memoDBClose();		//DBクローズ
			return listData;
	}
	
	@Override
	public void onListItemClick(ListView list, View view, int position, long id){
		
		//ListActivityクラスのonListItemClickを実行
		super.onListItemClick(list, view, position, id);
		
		//TwoLineListItem型として、Viewを取得
		TwoLineListItem textview = (TwoLineListItem)view;
		
		//選択されたメモIDを取得
		String subject = textview.getText1().getText().toString();
		String memoId = subject.substring(0, subject.indexOf("　"));
		
		//インテント生成
		Intent intent = new Intent(this,MemoShow.class);
		
		//インテント付加情報設定
		intent.putExtra("MEMO_ID", memoId);
		
		//アクティビティ起動
		startActivity(intent);
	}
	
	
	//オプションメニューを生成
	@Override
	public boolean onCreateOptionsMenu(Menu menu){
			
		//ActivityクラスのonCreateOptionsMenuを実行
		super.onCreateOptionsMenu(menu);
			
		//ホームメニュー
		MenuItem edit = menu.add(0,0,0,"ホーム");
		edit.setIcon(android.R.drawable.ic_menu_revert);
		return true;
	}
		
	//メニューアイテム選択処理
	@Override
	public boolean onOptionsItemSelected(MenuItem item){
			
		//インテント設定
		Intent intent = new Intent(this, MemoMain.class);
			
		//アクティビティ実行
		startActivity(intent);		
		return true;
	}	
}
