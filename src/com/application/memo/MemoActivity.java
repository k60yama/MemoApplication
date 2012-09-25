package com.application.memo;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class MemoActivity extends Activity implements OnClickListener,OnCheckedChangeListener {

	//エラーメッセージ
	private static String err_msg;
	
	//空文字チェック
	public static final String blank_str = "";
	
	//実行モードオブジェクト
	private RadioGroup exec;
	
	//DB作成用変数
	public MemoHelper helper = null;
	public SQLiteDatabase db = null;
	
	//リクエストコード定義
	private static final int REQUEST_CODE = 0;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        
    	//ActivityクラスのonCreateを呼び出し
    	super.onCreate(savedInstanceState);
        
        //Memoレイアウトファイルを指定
        setContentView(R.layout.layout_memo);
        
        //Buttonオブジェクト取得し、クリックリスナーを設定
        Button[] button = {
        		(Button)findViewById(R.id.savebutton),
        		(Button)findViewById(R.id.closebutton)
        };        
        for(int i=0; i<button.length; i++ ){
        	button[i].setOnClickListener(this);
        }
        
        //RadioGroupオブジェクトにチェックリスナーを設定
        exec = (RadioGroup)findViewById(R.id.exec_group);
        exec.setOnCheckedChangeListener(this);
        
        //DB作成
      	helper = new MemoHelper(this);
    }

    //クリック処理
	@Override
	public void onClick(View view) {
		
		//押下ボタン確認
		if(view.getId() == R.id.closebutton){
			finish();	//アクティビティ終了
			return ;	//強制終了
		}
		
	    //件名オブジェクト取得
		EditText subject_obj = (EditText)findViewById(R.id.subject);
		String subject = subject_obj.getText().toString();
		
		//本文オブジェクト取得
		EditText body_obj = (EditText)findViewById(R.id.body);
		String body = body_obj.getText().toString();
		
		//必須入力チェックメソッドへ
		if(!itemChk(subject,body,exec)){
			msgView(err_msg);	//エラーメッセージ表示処理へ
			return ;			//強制終了
		}
		
		//ラジオボタン取得
		RadioButton exec_button = (RadioButton)findViewById(exec.getCheckedRadioButtonId());
		
		//終了メッセージ初期化
		String msg = "";
		
		//MemoSQLMethodのインスタンス化
		MemoSQLMethod memoSQL = new MemoSQLMethod(helper);
		
		//選択別処理
		switch(exec_button.getId()){
		case R.id.save:
			
			//保存処理
			memoSQL.insertSQLMethod(subject, body);
			
			//終了メッセージ表示
			msg = "保存処理が成功しました。";
			msgView(msg);
			finish();
			break;
			
		case R.id.email:
			
			//保存処理
			memoSQL.insertSQLMethod(subject, body);
			
			//終了メッセージ表示
			msg = "保存処理が成功しました。Eメールを起動します。";
			msgView(msg);
			
			//E-MAIL起動処理
			emailMemo(subject,body);
			break;
		}
	}
	
	
	//E-MAIL起動処理
	private void emailMemo(String subject, String body){
		
		//宛先オブジェクト取得
		EditText sendto = (EditText)findViewById(R.id.sendto);
		
		//URI設定(宛先)
		Uri uri = Uri.parse("mailto:" + sendto.getText().toString());
		
		//インテント生成
		Intent intent = new Intent("android.intent.action.SENDTO",uri);
		
		//付属情報設定
		intent.putExtra(Intent.EXTRA_SUBJECT, subject);		//件名
		intent.putExtra(Intent.EXTRA_TEXT, body);			//本文
		
		//アクティビティ実行
		startActivityForResult(intent, REQUEST_CODE);
	}
	
	//E-MAIL起動後処理
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data){
		if(requestCode == REQUEST_CODE){
			//メイン画面へ
			Intent intent = new Intent(this, MemoMain.class);
			startActivity(intent);
		}
	}
	
	//必須入力チェック
	private boolean itemChk(String subject, String body, RadioGroup exec){
		
		//フラグ変数
		boolean itemChk;
		
		//件名、本文オブジェクトチェック
		if(blank_str.equals(subject.trim())){
			err_msg = "Error:件名が未入力です。";
			itemChk = false;
			return itemChk;
		}
		
		//本文オブジェクトチェック
		if(blank_str.equals(body.trim())){
			err_msg = "Error:本文が未入力です。";
			itemChk = false;
			return itemChk;
		}

		//実行モードチェック
		if(exec.getCheckedRadioButtonId() == -1){
			err_msg = "Error:実行モードが未選択です。";
			itemChk = false;
			return itemChk;
		}
		
		itemChk = true;
		return itemChk;
	}
	
	//メッセージ表示処理
	private void msgView(String msg_txt){
		
		//メッセージ表示設定
		Toast msg = Toast.makeText(this, msg_txt, Toast.LENGTH_LONG);
		msg.setGravity(Gravity.CENTER, 0, 0);
		
		//メッセージ表示
		msg.show();
	}

	@Override
	//宛先オブジェクト表示・非表示制御
	public void onCheckedChanged(RadioGroup rGrp, int rid) {
		
		//宛先オブジェクト取得
		View[] sendto_obj = {
			(TextView)findViewById(R.id.sendto_label),
			(EditText)findViewById(R.id.sendto)
		};
		
		//表示・非表示制御
		for(int i = 0; i < sendto_obj.length; i++){
			if(rid == R.id.save){
				sendto_obj[i].setVisibility(View.GONE);
			}else if(rid == R.id.email){
				sendto_obj[i].setVisibility(View.VISIBLE);
			}
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
