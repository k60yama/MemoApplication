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

	//�G���[���b�Z�[�W
	private static String err_msg;
	
	//�󕶎��`�F�b�N
	public static final String blank_str = "";
	
	//���s���[�h�I�u�W�F�N�g
	private RadioGroup exec;
	
	//DB�쐬�p�ϐ�
	public MemoHelper helper = null;
	public SQLiteDatabase db = null;
	
	//���N�G�X�g�R�[�h��`
	private static final int REQUEST_CODE = 0;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        
    	//Activity�N���X��onCreate���Ăяo��
    	super.onCreate(savedInstanceState);
        
        //Memo���C�A�E�g�t�@�C�����w��
        setContentView(R.layout.layout_memo);
        
        //Button�I�u�W�F�N�g�擾���A�N���b�N���X�i�[��ݒ�
        Button[] button = {
        		(Button)findViewById(R.id.savebutton),
        		(Button)findViewById(R.id.closebutton)
        };        
        for(int i=0; i<button.length; i++ ){
        	button[i].setOnClickListener(this);
        }
        
        //RadioGroup�I�u�W�F�N�g�Ƀ`�F�b�N���X�i�[��ݒ�
        exec = (RadioGroup)findViewById(R.id.exec_group);
        exec.setOnCheckedChangeListener(this);
        
        //DB�쐬
      	helper = new MemoHelper(this);
    }

    //�N���b�N����
	@Override
	public void onClick(View view) {
		
		//�����{�^���m�F
		if(view.getId() == R.id.closebutton){
			finish();	//�A�N�e�B�r�e�B�I��
			return ;	//�����I��
		}
		
	    //�����I�u�W�F�N�g�擾
		EditText subject_obj = (EditText)findViewById(R.id.subject);
		String subject = subject_obj.getText().toString();
		
		//�{���I�u�W�F�N�g�擾
		EditText body_obj = (EditText)findViewById(R.id.body);
		String body = body_obj.getText().toString();
		
		//�K�{���̓`�F�b�N���\�b�h��
		if(!itemChk(subject,body,exec)){
			msgView(err_msg);	//�G���[���b�Z�[�W�\��������
			return ;			//�����I��
		}
		
		//���W�I�{�^���擾
		RadioButton exec_button = (RadioButton)findViewById(exec.getCheckedRadioButtonId());
		
		//�I�����b�Z�[�W������
		String msg = "";
		
		//MemoSQLMethod�̃C���X�^���X��
		MemoSQLMethod memoSQL = new MemoSQLMethod(helper);
		
		//�I��ʏ���
		switch(exec_button.getId()){
		case R.id.save:
			
			//�ۑ�����
			memoSQL.insertSQLMethod(subject, body);
			
			//�I�����b�Z�[�W�\��
			msg = "�ۑ��������������܂����B";
			msgView(msg);
			finish();
			break;
			
		case R.id.email:
			
			//�ۑ�����
			memoSQL.insertSQLMethod(subject, body);
			
			//�I�����b�Z�[�W�\��
			msg = "�ۑ��������������܂����BE���[�����N�����܂��B";
			msgView(msg);
			
			//E-MAIL�N������
			emailMemo(subject,body);
			break;
		}
	}
	
	
	//E-MAIL�N������
	private void emailMemo(String subject, String body){
		
		//����I�u�W�F�N�g�擾
		EditText sendto = (EditText)findViewById(R.id.sendto);
		
		//URI�ݒ�(����)
		Uri uri = Uri.parse("mailto:" + sendto.getText().toString());
		
		//�C���e���g����
		Intent intent = new Intent("android.intent.action.SENDTO",uri);
		
		//�t�����ݒ�
		intent.putExtra(Intent.EXTRA_SUBJECT, subject);		//����
		intent.putExtra(Intent.EXTRA_TEXT, body);			//�{��
		
		//�A�N�e�B�r�e�B���s
		startActivityForResult(intent, REQUEST_CODE);
	}
	
	//E-MAIL�N���㏈��
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data){
		if(requestCode == REQUEST_CODE){
			//���C����ʂ�
			Intent intent = new Intent(this, MemoMain.class);
			startActivity(intent);
		}
	}
	
	//�K�{���̓`�F�b�N
	private boolean itemChk(String subject, String body, RadioGroup exec){
		
		//�t���O�ϐ�
		boolean itemChk;
		
		//�����A�{���I�u�W�F�N�g�`�F�b�N
		if(blank_str.equals(subject.trim())){
			err_msg = "Error:�����������͂ł��B";
			itemChk = false;
			return itemChk;
		}
		
		//�{���I�u�W�F�N�g�`�F�b�N
		if(blank_str.equals(body.trim())){
			err_msg = "Error:�{���������͂ł��B";
			itemChk = false;
			return itemChk;
		}

		//���s���[�h�`�F�b�N
		if(exec.getCheckedRadioButtonId() == -1){
			err_msg = "Error:���s���[�h�����I���ł��B";
			itemChk = false;
			return itemChk;
		}
		
		itemChk = true;
		return itemChk;
	}
	
	//���b�Z�[�W�\������
	private void msgView(String msg_txt){
		
		//���b�Z�[�W�\���ݒ�
		Toast msg = Toast.makeText(this, msg_txt, Toast.LENGTH_LONG);
		msg.setGravity(Gravity.CENTER, 0, 0);
		
		//���b�Z�[�W�\��
		msg.show();
	}

	@Override
	//����I�u�W�F�N�g�\���E��\������
	public void onCheckedChanged(RadioGroup rGrp, int rid) {
		
		//����I�u�W�F�N�g�擾
		View[] sendto_obj = {
			(TextView)findViewById(R.id.sendto_label),
			(EditText)findViewById(R.id.sendto)
		};
		
		//�\���E��\������
		for(int i = 0; i < sendto_obj.length; i++){
			if(rid == R.id.save){
				sendto_obj[i].setVisibility(View.GONE);
			}else if(rid == R.id.email){
				sendto_obj[i].setVisibility(View.VISIBLE);
			}
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
