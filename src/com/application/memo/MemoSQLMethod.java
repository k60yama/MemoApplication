package com.application.memo;

import java.text.SimpleDateFormat;
import java.util.Date;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class MemoSQLMethod {

	private MemoHelper helper;
	private SQLiteDatabase db;
	
	//������(�R���X�g���N�^)
	public MemoSQLMethod(MemoHelper helper){
		this.helper = helper;
	}
	
	//�f�[�^�o�^����
	public void insertSQLMethod(String subject, String body){		
		this.db = this.helper.getWritableDatabase();	//DB�I�u�W�F�N�g�擾
		this.db.beginTransaction();						//�g�����U�N�V��������J�n
		
		//�f�[�^�o�^����
		ContentValues val = new ContentValues();
		val.put("subject", subject);					//����
		val.put("body", body);							//�{��
		
		//�쐬�����ݒ�
		String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		val.put("createDate", date);
		
		this.db.insert("memo", null, val);				//�f�[�^�o�^
		this.db.setTransactionSuccessful();				//�R�~�b�g
		this.db.endTransaction();						//�g�����U�N�V��������I��
		this.memoDBClose();								//DB�N���[�Y
	}
	
	//�f�[�^�擾����
	public Cursor selectSQLMethod(String where){
		this.db = this.helper.getReadableDatabase();	//DB�I�u�W�F�N�g�擾
		
		//�񖼒�`
		String columns[] = {"id", "subject", "body", "createDate"};	
			
		//�f�[�^�擾
		Cursor cursor = db.query("memo", columns, where, null, null, null, "id");
		return cursor;
	}
	
	//�f�[�^�폜����
	public boolean deleteSQLMethod(String where){
		this.db = this.helper.getWritableDatabase();	//DB�I�u�W�F�N�g�擾
		this.db.beginTransaction();						//�g�����U�N�V��������J�n
		
		//�f�[�^�폜
		this.db.delete("memo", where, null);
		
		this.db.setTransactionSuccessful();				//�R�~�b�g
		this.db.endTransaction();						//�g�����U�N�V��������I��
		this.memoDBClose();								//DB�N���[�Y								
		return true;
	}
	
	//�f�[�^�X�V����
	public boolean updateSQLMethod(String subject, String body, String where){		
		this.db = this.helper.getWritableDatabase();	//DB�I�u�W�F�N�g�擾
		this.db.beginTransaction();						//�g�����U�N�V��������J�n
			
		//�f�[�^�X�V����
		ContentValues val = new ContentValues();
		val.put("subject", subject);					//����
		val.put("body", body);							//�{��
						
		this.db.update("memo", val, where, null);		//�f�[�^�X�V
		this.db.setTransactionSuccessful();				//�R�~�b�g
		this.db.endTransaction();						//�g�����U�N�V��������I��
		this.memoDBClose();								//DB�N���[�Y
		return true;
	}
	
	//�f�[�^�x�[�X�N���[�Y����
	public void memoDBClose(){
		this.db.close();								//DB�I�u�W�F�N�g���N���[�Y
	}
}
