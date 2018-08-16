package com.senos.seno.downloadbigjson;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.net.URI;

public class MainActivity extends AppCompatActivity {
    static int PICKFILE_REQUEST_CODE = 100;
    EditText  etURL ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etURL = (EditText) findViewById(R.id.editText);
//        String a = getApplicationContext().getAssets();
        String a = getApplicationContext().getExternalFilesDir(null).getPath();
        etURL.setText(a);
    }

    public void DownlaodFile(View view) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
//        intent.setType("file/.JPG");
        startActivityForResult(intent, PICKFILE_REQUEST_CODE);
    }

    public void Extract(View view) {
        try {
            ExtractZip.extract(getApplicationContext().getAssets().open("data.zip"),getApplicationContext().getExternalFilesDir(null).getPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == PICKFILE_REQUEST_CODE){
            Uri a =  data.getData();
//            String b = getRealPathFromURI(a);
            Toast.makeText(this, a.toString(), Toast.LENGTH_SHORT).show();

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private String getRealPathFromURI(Uri contentURI) {
        String result=null;
        Cursor cursor = getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) { // Source is Dropbox or other similar local file path
            result = contentURI.getPath();
        }
        return result;
    }
}
