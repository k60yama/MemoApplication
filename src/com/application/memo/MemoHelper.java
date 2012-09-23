package com.application.memo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MemoHelper extends SQLiteOpenHelper {

	//�f�[�^�x�[�X��`
	public MemoHelper(Context con){
		super(con, "memodb", null, 1);
	}
	
	//�e�[�u����`
	@Override
	public void onCreate(SQLiteDatabase db) {
		
		//SQL��
		String sql =
				"create table memo(id integer primary key autoincrement," +
				"subject text not null," +
				"body text not null," +
				"createDate text not null)";
		
		//SQL���s
		db.execSQL(sql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		

	}

}
