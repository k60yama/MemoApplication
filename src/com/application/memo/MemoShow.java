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
	
	//�A�N�e�B�r�e�B��onCreate�����s
	@Override
	public void onCreate(Bundle savedInstanceState){
		
		//Activity�N���X��onCreate���s
		super.onCreate(savedInstanceState);
		
		//���C�A�E�g�ݒ�t�@�C���w��
		setContentView(R.layout.show_memo);
		
		//DB�擾
		MemoSQLMethod memodb = this.getMemoDatabase();
		
		//�f�[�^�擾
		sqlWhere = "id=" + getMemoId();
		Cursor cursor = memodb.selectSQLMethod(sqlWhere);
		
		//�����\��
		while(cursor.moveToNext()){
			this.setMemo(cursor);
		}
		
		//���������i�[
		array.add(sqlWhere);
		
		//DB�N���[�Y
		memodb.memoDBClose();
	}
	
	//DB�擾
	public MemoSQLMethod getMemoDatabase(){
		MemoHelper helper = new MemoHelper(this);
		return new MemoSQLMethod(helper);
	}
	
	//����ID�擾
	public String getMemoId(){
		
		//�C���e���g�擾
		Intent intent = this.getIntent();
		
		Bundle data = intent.getExtras();	//����ID���擾	
		return data.getString("MEMO_ID");
	}
	
	//�\�������ݒ�
	public void setMemo(Cursor cursor){

		//���X�g�i�[
		array = new ArrayList<String>();
		
		//�쐬�����ݒ�
		TextView date = (TextView)findViewById(R.id.date);
		date.setText(cursor.getString(3));
		array.add(cursor.getString(3));
		
		//�����ݒ�
		TextView subject = (TextView)findViewById(R.id.subject);
		subject.setText(cursor.getString(1));
		array.add(cursor.getString(1));
		
		//�{���ݒ�
		TextView body = (TextView)findViewById(R.id.body);
		body.setText(cursor.getString(2));
		array.add(cursor.getString(2));
	}
	
	//�I�v�V�������j���[�𐶐�
	@Override
	public boolean onCreateOptionsMenu(Menu menu){
		
		//Activity�N���X��onCreateOptionsMenu�����s
		super.onCreateOptionsMenu(menu);
		
		//�ҏW���j���[
		MenuItem edit = menu.add(0,0,0,"�ҏW");
		edit.setIcon(android.R.drawable.ic_menu_edit);
		
		//�폜���j���[
		MenuItem delete = menu.add(0,1,0,"�폜");
		delete.setIcon(android.R.drawable.ic_menu_delete);
		
		////�������ۑ��ꗗ���j���[
		MenuItem back = menu.add(0,2,0,"�����ꗗ");
		back.setIcon(android.R.drawable.ic_menu_info_details);
		return true;
	}
	
	//���j���[�A�C�e���I������
	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		
		//�C���e���g�ݒ�
		Intent intent = null;
		
		if(item.getItemId() == 1 || item.getItemId() == 2){
			if(item.getItemId() == 1){
				//DB�쐬
				MemoSQLMethod memodb = this.getMemoDatabase();
				
				//�����폜����
				if(memodb.deleteSQLMethod(sqlWhere) == true){
					Toast.makeText(this, "�������폜���܂����B", Toast.LENGTH_SHORT).show();
				}
			}
			//�������ꗗ��
			intent = new Intent(this, MemoList.class);
		}else if(item.getItemId() == 0){
			//�ҏW��ʂ�
			intent = new Intent(this, MemoEdit.class);
			intent.putStringArrayListExtra("memoData", array);
		}
		
		//�A�N�e�B�r�e�B���s
		startActivity(intent);		
		return true;
	}
}
