package com.application.memo;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class MemoShow extends Activity {
	
	private String sqlWhere;
	
	private ArrayList<String> array;
	
	//アクティビティのonCreateを実行
	@Override
	public void onCreate(Bundle savedInstanceState){
		
		//ActivityクラスのonCreate実行
		super.onCreate(savedInstanceState);
		
		//レイアウト設定ファイル指定
		setContentView(R.layout.show_memo);
		
		//DB取得
		MemoSQLMethod memodb = this.getMemoDatabase();
		
		//データ取得
		sqlWhere = "id=" + getMemoId();
		Cursor cursor = memodb.selectSQLMethod(sqlWhere);
		
		//メモ表示
		while(cursor.moveToNext()){
			this.setMemo(cursor);
		}
		
		//検索条件格納
		array.add(sqlWhere);
		
		//DBクローズ
		memodb.memoDBClose();
	}
	
	//DB取得
	public MemoSQLMethod getMemoDatabase(){
		MemoHelper helper = new MemoHelper(this);
		return new MemoSQLMethod(helper);
	}
	
	//メモID取得
	public String getMemoId(){
		
		//インテント取得
		Intent intent = this.getIntent();
		
		Bundle data = intent.getExtras();	//メモIDを取得	
		return data.getString("MEMO_ID");
	}
	
	//表示メモ設定
	public void setMemo(Cursor cursor){

		//リスト格納
		array = new ArrayList<String>();
		
		//作成日時設定
		TextView date = (TextView)findViewById(R.id.date);
		date.setText(cursor.getString(3));
		array.add(cursor.getString(3));
		
		//件名設定
		TextView subject = (TextView)findViewById(R.id.subject);
		subject.setText(cursor.getString(1));
		array.add(cursor.getString(1));
		
		//本文設定
		TextView body = (TextView)findViewById(R.id.body);
		body.setText(cursor.getString(2));
		array.add(cursor.getString(2));
	}
	
	//オプションメニューを生成
	@Override
	public boolean onCreateOptionsMenu(Menu menu){
		
		//ActivityクラスのonCreateOptionsMenuを実行
		super.onCreateOptionsMenu(menu);
		
		//編集メニュー
		MenuItem edit = menu.add(0,0,0,"編集");
		edit.setIcon(android.R.drawable.ic_menu_edit);
		
		//削除メニュー
		MenuItem delete = menu.add(0,1,0,"削除");
		delete.setIcon(android.R.drawable.ic_menu_delete);
		
		////メモ帳保存一覧メニュー
		MenuItem back = menu.add(0,2,0,"メモ一覧");
		back.setIcon(android.R.drawable.ic_menu_info_details);
		return true;
	}
	
	//メニューアイテム選択処理
	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		
		//インテント設定
		Intent intent = null;
		
		if(item.getItemId() == 1 || item.getItemId() == 2){
			if(item.getItemId() == 1){
				//DB作成
				MemoSQLMethod memodb = this.getMemoDatabase();
				
				//メモ削除処理
				if(memodb.deleteSQLMethod(sqlWhere) == true){
					Toast.makeText(this, "メモを削除しました。", Toast.LENGTH_SHORT).show();
				}
			}
			//メモ帳一覧へ
			intent = new Intent(this, MemoList.class);
		}else if(item.getItemId() == 0){
			//編集画面へ
			intent = new Intent(this, MemoEdit.class);
			intent.putStringArrayListExtra("memoData", array);
		}
		
		//アクティビティ実行
		startActivity(intent);		
		return true;
	}
}
