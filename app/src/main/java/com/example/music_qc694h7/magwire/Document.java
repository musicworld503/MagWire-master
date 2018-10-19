package com.example.music_qc694h7.magwire;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;

public class Document extends AppCompatActivity {

    Button load, save, delete;
    EditText inputText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);





        setContentView(R.layout.activity_document);
        load = (Button) findViewById(R.id.load);

        save = (Button) findViewById(R.id.save);

        delete = (Button) findViewById(R.id.delete);

        inputText = (EditText) findViewById(R.id.inputText);

        load.setOnClickListener(listener);

        save.setOnClickListener(listener);

        delete.setOnClickListener(listener);

    }

    public   String filename;

    public void load(String filename){

        FileInputStream fis = null;

        try{

            fis = openFileInput(filename);

            byte[] data = new byte[fis.available()];

            while( fis.read(data) != -1){

            }



            inputText.setText(new String(data));

            Toast.makeText(Document.this, "기존 문서 불러오기 성공", Toast.LENGTH_SHORT).show();

        }catch(Exception e){

            e.printStackTrace();

        }finally{

            try{ if(fis != null) fis.close(); }catch(Exception e){e.printStackTrace();}

        }

    }



    public void save(String filename){

        FileOutputStream fos = null;

        try{

            fos = openFileOutput(filename, Context.MODE_PRIVATE);

            String out = inputText.getText().toString();

            fos.write(out.getBytes());

            Toast.makeText(Document.this, "저장 성공", Toast.LENGTH_SHORT).show();

        }catch(Exception e){

            Toast.makeText(Document.this, "문서에 붙일 태그를 입력해주세요", Toast.LENGTH_SHORT).show();

            e.printStackTrace();

        }finally{

            try{ if(fos != null) fos.close(); }catch(Exception e){e.printStackTrace();}

        }

    }



    public void delete(String filename){

        boolean b = deleteFile(filename);

        if(b){

            Toast.makeText(Document.this, "삭제", Toast.LENGTH_SHORT).show();

        }else{

            Toast.makeText(Document.this, "삭제 실패", Toast.LENGTH_SHORT).show();

        }

    }

//onCreate()메소드 다음에 추가해주시면 됩니다.



    @Override

//menu를 생성해주는 메소드

    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main, menu);

        return true;

    }



    @Override

//menu 선택시 리스너 메소드

    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){

            case R.id.newMemo:

                inputText.setText("");

                filename = null;

                break;

        }

        return false;

    }

    View.OnClickListener listener = new View.OnClickListener() {

        @Override

        public void onClick(View view) {

            switch (view.getId()) {

                case R.id.load:

                    Log.i("TAG", "기존문서 불러오기 진행");


                    LinearLayout alert_layout1 = (LinearLayout) View.inflate(Document.this, R.layout.alert_layout, null);

                    final EditText alert_edit = (EditText) alert_layout1.findViewById(R.id.search_memo);

                    new AlertDialog.Builder(Document.this)

                            .setTitle("메모 불러오기")

                            .setMessage("불러올 메모")

                            .setView(alert_layout1)

                            .setPositiveButton("불러오기", new DialogInterface.OnClickListener() {

                                @Override

                                public void onClick(DialogInterface dialogInterface, int i) {

                                    filename = alert_edit.getText().toString();

                                    Log.i("TAG", filename);

                                    load(filename);

                                }

                            })

                            .setNegativeButton("취소", new DialogInterface.OnClickListener() {

                                @Override

                                public void onClick(DialogInterface dialogInterface, int i) {

                                    Toast.makeText(Document.this, "불러오기 취소", Toast.LENGTH_SHORT).show();

                                }

                            }).show();

                    break;

                case R.id.save:

                    Log.i("TAG", "저장중");


                    LinearLayout alert_layout2 = (LinearLayout) View.inflate(Document.this, R.layout.alert_layout, null);

                    final EditText alert_edit2 = (EditText) alert_layout2.findViewById(R.id.search_memo);

                    if (filename == null) {

                        new AlertDialog.Builder(Document.this)

                                .setTitle("메모 저장")

                                .setMessage("저장할 이름")

                                .setView(alert_layout2)

                                .setPositiveButton("저장", new DialogInterface.OnClickListener() {

                                    @Override

                                    public void onClick(DialogInterface dialogInterface, int i) {

                                        filename = alert_edit2.getText().toString();

                                        save(filename);

                                    }

                                })

                                .setNegativeButton("취소", new DialogInterface.OnClickListener() {

                                    @Override

                                    public void onClick(DialogInterface dialogInterface, int i) {

                                        Toast.makeText(Document.this, "저장 취소", Toast.LENGTH_SHORT).show();

                                    }

                                }).show();

                    } else {

                        save(filename);

                    }

                    break;

                case R.id.delete:

                    Log.i("TAG", "삭제중");

                    if (filename == null) {

                        Toast.makeText(Document.this, "삭제할 파일이 없습니다.", Toast.LENGTH_SHORT).show();

                    } else {

                        delete(filename);

                        filename = null;

                    }

                    break;

            }

        }
        };
    }




