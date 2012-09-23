package com.application.memo;

import java.text.SimpleDateFormat;
import java.util.Date;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class MemoSQLMethod {

	private MemoHelper helper;
	private SQLiteDatabase db;
	
	//初期化(コンストラクタ)
	public MemoSQLMethod(MemoHelper helper){
		this.helper = helper;
	}
	
	//データ登録処理
	public void insertSQLMethod(String subject, String body){		
		this.db = this.helper.getWritableDatabase();	//DBオブジェクト取得
		this.db.beginTransaction();						//トランザクション制御開始
		
		//データ登録処理
		ContentValues val = new ContentValues();
		val.put("subject", subject);					//件名
		val.put("body", body);							//本文
		
		//作成日時設定
		String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		val.put("createDate", date);
		
		this.db.insert("memo", null, val);				//データ登録
		this.db.setTransactionSuccessful();				//コミット
		this.db.endTransaction();						//トランザクション制御終了
		this.memoDBClose();								//DBクローズ
	}
	
	//データ取得処理
	public Cursor selectSQLMethod(String where){
		this.db = this.helper.getReadableDatabase();	//DBオブジェクト取得
		
		//列名定義
		String columns[] = {"id", "subject", "body", "createDate"};	
			
		//データ取得
		Cursor cursor = db.query("memo", columns, where, null, null, null, "id");
		return cursor;
	}
	
	//データ削除処理
	public boolean deleteSQLMethod(String where){
		this.db = this.helper.getWritableDatabase();	//DBオブジェクト取得
		this.db.beginTransaction();						//トランザクション制御開始
		
		//データ削除
		this.db.delete("memo", where, null);
		
		this.db.setTransactionSuccessful();				//コミット
		this.db.endTransaction();						//トランザクション制御終了
		this.memoDBClose();								//DBクローズ								
		return true;
	}
	
	//データ更新処理
	public boolean updateSQLMethod(String subject, String body, String where){		
		this.db = this.helper.getWritableDatabase();	//DBオブジェクト取得
		this.db.beginTransaction();						//トランザクション制御開始
			
		//データ更新処理
		ContentValues val = new ContentValues();
		val.put("subject", subject);					//件名
		val.put("body", body);							//本文
						
		this.db.update("memo", val, where, null);		//データ更新
		this.db.setTransactionSuccessful();				//コミット
		this.db.endTransaction();						//トランザクション制御終了
		this.memoDBClose();								//DBクローズ
		return true;
	}
	
	//データベースクローズ処理
	public void memoDBClose(){
		this.db.close();								//DBオブジェクトをクローズ
	}
}
