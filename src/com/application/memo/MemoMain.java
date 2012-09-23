package com.application.memo;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;

public class MemoMain extends Activity {

	//�A�N�e�B�r�e�BonCreate���\�b�h
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	
    	//Activity�N���X��onCreate�����s
        super.onCreate(savedInstanceState);
        
        //���C�A�E�g�ݒ�t�@�C���̎w��
        setContentView(R.layout.memo_home);
    }
    
    //�I�v�V�������j���[�\�����\�b�h
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	
    	//Activity�N���X��onCreateOptionsMenu�����s
    	super.onCreateOptionsMenu(menu);
    	
    	//�������V�K�쐬���j���[
    	MenuItem newMemo = menu.add(0,0,0,"�V�K�쐬");
    	newMemo.setIcon(android.R.drawable.ic_menu_agenda);
    	
    	//�������ۑ��ꗗ���j���[
    	MenuItem memoList = menu.add(0,1,0,"�����ꗗ");
    	memoList.setIcon(android.R.drawable.ic_menu_info_details);
        return true;
    }

    //���j���[�A�C�e���I������
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
    	
    	//�C���e���g�ݒ�
    	Intent intent = null;
    	
    	if(item.getItemId() == 0){
    		intent = new Intent(this,MemoActivity.class);	//�V�K�����쐬��ʂ�
    	}else{
    		intent = new Intent(this,MemoList.class);		//�����ꗗ��
    	}
    	
    	//�A�N�e�B�r�e�B���s
    	startActivity(intent);
    	return true;
    }
}
