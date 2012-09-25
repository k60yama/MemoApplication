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
	
	//�A�N�e�B�r�e�B��onCreate�����s
	@Override
	public void onCreate(Bundle savedInstanceState){
		//Activity�N���X��onCreate���s
		super.onCreate(savedInstanceState);
				
		//���C�A�E�g�ݒ�t�@�C���w��
		setContentView(R.layout.edit_memo);
		
		//�����ݒ�
		setMemo();
		
		//Button�I�u�W�F�N�g�擾
		Button[] button = {
				(Button)findViewById(R.id.updatebutton),
				(Button)findViewById(R.id.closebutton)
		};
		
		//�N���b�N���X�i�[�ݒ�
		for(int i = 0; i < button.length; i++ ){
			button[i].setOnClickListener(this);
		}
	}

	//�����ݒ�
	public void setMemo(){
		//�C���e���g�擾
		Intent intent = this.getIntent();
		data = intent.getStringArrayListExtra("memoData");
		
		//�쐬����
		TextView date = (TextView)findViewById(R.id.date);
		date.setText(data.get(0));
		
		//����
		EditText subject = (EditText)findViewById(R.id.subject);
		subject.setText(data.get(1));
		
		//�{��
		EditText body = (EditText)findViewById(R.id.body);
		body.setText(data.get(2));
	}
	
	@Override
	public void onClick(View view) {
		
		//�X�V����
		if(view.getId() == R.id.updatebutton){
			
			//����
			String subject = ((EditText)findViewById(R.id.subject)).getText().toString();
			
			//�{��
			String body = ((EditText)findViewById(R.id.body)).getText().toString();

			//DB�쐬
			MemoHelper helper = new MemoHelper(this);
			MemoSQLMethod memoSQL = new MemoSQLMethod(helper);
			
			//�f�[�^�X�V
			if(memoSQL.updateSQLMethod(subject, body, data.get(3)) == true){
				Toast.makeText(this, "�������X�V���܂����B", Toast.LENGTH_SHORT).show();;
			}
			
			//�C���e���g
			Intent intent = new Intent(this,MemoList.class);
			startActivity(intent);
		}else{
			//�A�N�e�B�r�e�B�I��
			finish();
		}
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
