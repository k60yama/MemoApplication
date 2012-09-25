package com.application.memo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.TwoLineListItem;

public class MemoList extends ListActivity {
	
	//�A�N�e�B�r�e�BOnCreate���\�b�h
	public void onCreate(Bundle savedInstanceState){
		
		//Activity�N���X��OnCreate�����s
		super.onCreate(savedInstanceState);
		
		//���C�A�E�g�ݒ�t�@�C�����w��
		setContentView(R.layout.list_memo);
	    		
        //SimpleAdapter�̃C���X�^���X����
        SimpleAdapter adapter = new SimpleAdapter(
        		this,
        		dataRead(),
        		android.R.layout.simple_list_item_2,
        		new String[]{"subject","date"},
        		new int[]{android.R.id.text1, android.R.id.text2});
        
        //���X�g�\��
        this.setListAdapter(adapter);
 	}
	
	//�f�[�^�ǂݍ���
	public  List<Map<String,String>> dataRead(){
		
			//���X�g�\���p
			List<Map<String,String>> listData = new ArrayList<Map<String,String>>();
			
			//DB�쐬
			MemoHelper helper = new MemoHelper(this);
			MemoSQLMethod memodb = new MemoSQLMethod(helper);
			
			//�f�[�^�擾
			Cursor cursor = memodb.selectSQLMethod(null);
			
			if(cursor.getCount() == 0){
				//TextView�I�u�W�F�N�g�擾
				TextView view = (TextView)findViewById(R.id.warning);
				view.setVisibility(View.VISIBLE);
				view.setText("�ۑ�����Ă��郁���͂���܂���I");
			}
			
			//���X�g����
			while(cursor.moveToNext()){
				Map<String,String> data = new HashMap<String,String>();
				data.put("subject", cursor.getString(0) + "�@�����F" +cursor.getString(1));
				data.put("date", "�쐬�����F" + cursor.getString(3));
				listData.add(data);
			}
				
			memodb.memoDBClose();		//DB�N���[�Y
			return listData;
	}
	
	@Override
	public void onListItemClick(ListView list, View view, int position, long id){
		
		//ListActivity�N���X��onListItemClick�����s
		super.onListItemClick(list, view, position, id);
		
		//TwoLineListItem�^�Ƃ��āAView���擾
		TwoLineListItem textview = (TwoLineListItem)view;
		
		//�I�����ꂽ����ID���擾
		String subject = textview.getText1().getText().toString();
		String memoId = subject.substring(0, subject.indexOf("�@"));
		
		//�C���e���g����
		Intent intent = new Intent(this,MemoShow.class);
		
		//�C���e���g�t�����ݒ�
		intent.putExtra("MEMO_ID", memoId);
		
		//�A�N�e�B�r�e�B�N��
		startActivity(intent);
	}
	
	
	//�I�v�V�������j���[�𐶐�
	@Override
	public boolean onCreateOptionsMenu(Menu menu){
			
		//Activity�N���X��onCreateOptionsMenu�����s
		super.onCreateOptionsMenu(menu);
			
		//�z�[�����j���[
		MenuItem edit = menu.add(0,0,0,"�z�[��");
		edit.setIcon(android.R.drawable.ic_menu_revert);
		return true;
	}
		
	//���j���[�A�C�e���I������
	@Override
	public boolean onOptionsItemSelected(MenuItem item){
			
		//�C���e���g�ݒ�
		Intent intent = new Intent(this, MemoMain.class);
			
		//�A�N�e�B�r�e�B���s
		startActivity(intent);		
		return true;
	}
	
	@Override
	//back(�߂�)�{�^��������
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
