package com.application.memo;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;

public class MemoMain extends Activity {

	//アクティビティonCreateメソッド
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	
    	//ActivityクラスのonCreateを実行
        super.onCreate(savedInstanceState);
        
        //レイアウト設定ファイルの指定
        setContentView(R.layout.memo_home);
    }
    
    //オプションメニュー表示メソッド
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	
    	//ActivityクラスのonCreateOptionsMenuを実行
    	super.onCreateOptionsMenu(menu);
    	
    	//メモ帳新規作成メニュー
    	MenuItem newMemo = menu.add(0,0,0,"新規作成");
    	newMemo.setIcon(android.R.drawable.ic_menu_agenda);
    	
    	//メモ帳保存一覧メニュー
    	MenuItem memoList = menu.add(0,1,0,"メモ一覧");
    	memoList.setIcon(android.R.drawable.ic_menu_info_details);
        return true;
    }

    //メニューアイテム選択処理
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
    	
    	//インテント設定
    	Intent intent = null;
    	
    	if(item.getItemId() == 0){
    		intent = new Intent(this,MemoActivity.class);	//新規メモ作成画面へ
    	}else{
    		intent = new Intent(this,MemoList.class);		//メモ一覧へ
    	}
    	
    	//アクティビティ実行
    	startActivity(intent);
    	return true;
    }
}
