package com.application.memo;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MemoEdit extends Activity implements OnClickListener{

	private ArrayList<String> data;
	
	//アクティビティのonCreateを実行
	@Override
	public void onCreate(Bundle savedInstanceState){
		//ActivityクラスのonCreate実行
		super.onCreate(savedInstanceState);
				
		//レイアウト設定ファイル指定
		setContentView(R.layout.edit_memo);
		
		//メモ設定
		setMemo();
		
		//Buttonオブジェクト取得
		Button[] button = {
				(Button)findViewById(R.id.updatebutton),
				(Button)findViewById(R.id.closebutton)
		};
		
		//クリックリスナー設定
		for(int i = 0; i < button.length; i++ ){
			button[i].setOnClickListener(this);
		}
	}

	//メモ設定
	public void setMemo(){
		//インテント取得
		Intent intent = this.getIntent();
		data = intent.getStringArrayListExtra("memoData");
		
		//作成日時
		TextView date = (TextView)findViewById(R.id.date);
		date.setText(data.get(0));
		
		//件名
		EditText subject = (EditText)findViewById(R.id.subject);
		subject.setText(data.get(1));
		
		//本文
		EditText body = (EditText)findViewById(R.id.body);
		body.setText(data.get(2));
	}
	
	@Override
	public void onClick(View view) {
		
		//更新処理
		if(view.getId() == R.id.updatebutton){
			
			//件名
			String subject = ((EditText)findViewById(R.id.subject)).getText().toString();
			
			//本文
			String body = ((EditText)findViewById(R.id.body)).getText().toString();

			//DB作成
			MemoHelper helper = new MemoHelper(this);
			MemoSQLMethod memoSQL = new MemoSQLMethod(helper);
			
			//データ更新
			if(memoSQL.updateSQLMethod(subject, body, data.get(3)) == true){
				Toast.makeText(this, "メモを更新しました。", Toast.LENGTH_SHORT).show();;
			}
			
			//インテント
			Intent intent = new Intent(this,MemoList.class);
			startActivity(intent);
		}else{
			//アクティビティ終了
			finish();
		}
	}
	
	@Override
	//back(戻る)ボタン無効化
	public boolean dispatchKeyEvent(KeyEvent event){
		if(event.getAction() == KeyEvent.ACTION_DOWN){
			switch(event.getKeyCode()){
			case KeyEvent.KEYCODE_BACK:
				return true;
			}
		}
		return super.dispatchKeyEvent(event);
	}
}
