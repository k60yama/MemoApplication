package com.application.memo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MemoHelper extends SQLiteOpenHelper {

	//データベース定義
	public MemoHelper(Context con){
		super(con, "memodb", null, 1);
	}
	
	//テーブル定義
	@Override
	public void onCreate(SQLiteDatabase db) {
		
		//SQL文
		String sql =
				"create table memo(id integer primary key autoincrement," +
				"subject text not null," +
				"body text not null," +
				"createDate text not null)";
		
		//SQL実行
		db.execSQL(sql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		

	}

}
