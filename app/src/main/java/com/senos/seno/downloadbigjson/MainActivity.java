package com.senos.seno.downloadbigjson;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.senos.seno.downloadbigjson.utility.ExtractZip;
import com.tonyodev.fetch2.AbstractFetchListener;
import com.tonyodev.fetch2.Download;
import com.tonyodev.fetch2.Error;
import com.tonyodev.fetch2.Fetch;
import com.tonyodev.fetch2.FetchConfiguration;
import com.tonyodev.fetch2.FetchListener;
import com.tonyodev.fetch2.NetworkType;
import com.tonyodev.fetch2.Priority;
import com.tonyodev.fetch2.Request;
import com.tonyodev.fetch2core.DownloadBlock;

import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    static int PICKFILE_REQUEST_CODE = 100;
    EditText etURL;
    private Fetch fetch;
    String pathFile;
    TextView txtKeterangan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etURL = (EditText) findViewById(R.id.editText);
//        String a = getApplicationContext().getAssets();
        String a = getApplicationContext().getExternalFilesDir(null).getPath();
        txtKeterangan = (TextView) findViewById(R.id.keterangan);
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
            ExtractZip.extract(getApplicationContext().getAssets().open("data.zip"), getApplicationContext().getExternalFilesDir(null).getPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PICKFILE_REQUEST_CODE) {
            Uri a = data.getData();
//            String b = getRealPathFromURI(a);
            Toast.makeText(this, a.toString(), Toast.LENGTH_SHORT).show();

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private String getRealPathFromURI(Uri contentURI) {
        String result = null;
        Cursor cursor = getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) { // Source is Dropbox or other similar local file path
            result = contentURI.getPath();
        }
        return result;
    }

    private void downloadFile() {
        FetchConfiguration fetchConfiguration = new FetchConfiguration.Builder(this).
                setDownloadConcurrentLimit(2).build();
        fetch = fetch.Impl.getDefaultInstance();

        String Url = "http://192.168.137.1:8080/file/data.zip";
        pathFile = getApplicationContext().getExternalFilesDir(null).getPath() + "/data.zip";

        final Request request = new Request(Url, pathFile);
        request.setPriority(Priority.HIGH);
        request.setNetworkType(NetworkType.ALL);
        request.addHeader("clientkey", "SD78DF93_3947&MVNGHE1WONG");


        FetchListener fetchListener = new FetchListener() {


            @Override
            public void onDeleted(Download download) {

            }

            @Override
            public void onRemoved(Download download) {

            }

            @Override
            public void onCancelled(Download download) {

            }

            @Override
            public void onResumed(Download download) {

            }

            @Override
            public void onPaused(Download download) {

            }

            @Override
            public void onProgress(Download download, long l, long l1) {

            }

            @Override
            public void onStarted(Download download, List<? extends DownloadBlock> list, int i) {

            }

            @Override
            public void onDownloadBlockUpdated(Download download, DownloadBlock downloadBlock, int i) {

            }

            @Override
            public void onError(Download download, Error error, Throwable throwable) {

            }

            @Override
            public void onCompleted(Download download) {

            }

            @Override
            public void onWaitingNetwork(Download download) {

            }

            @Override
            public void onQueued(Download download, boolean b) {

            }

            @Override
            public void onAdded(Download download) {

            }
        };

        fetch.addListener(fetchListener);


        fetch.enqueue(request, updatedRequest -> {
            //Request was successfully enqueued for download.


        }, error -> {
            //An error occurred enqueuing the request.
            fetch.removeListener(fetchListener);
        });



    }

    public void DownlaodFileOnline(View view) {
        downloadFile();
    }
}
