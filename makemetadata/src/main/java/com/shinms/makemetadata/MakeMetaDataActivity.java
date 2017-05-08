package com.shinms.makemetadata;

import android.Manifest;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.extractor.ExtractorMetaData;
import com.google.android.exoplayer2.extractor.ts.TsMetaDataRetriever;
import com.google.android.exoplayer2.upstream.DataSpec;
import com.google.android.exoplayer2.upstream.FileDataSource;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class MakeMetaDataActivity extends AppCompatActivity {
    private static final String TAG = "MakeMetaDataActivity";

    private static final String TEST_STREAM = "/Movies/TestStream.ts";
    private static final int PERMISSIONS_REQUEST_ID = 1234;

    String [] mPermissionsNeeded = { Manifest.permission.WRITE_EXTERNAL_STORAGE };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (checkAndRequestPermissions())
            startRecodingTest();
    }

    public void startRecodingTest() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String recordFile = Environment.getExternalStorageDirectory() + TEST_STREAM;

                byte[] data = new byte[TsMetaDataRetriever.BUFFER_SIZE];
                FileDataSource source = new FileDataSource();
                try {
                    source.open(new DataSpec(Uri.parse(recordFile)));
                } catch (FileDataSource.FileDataSourceException e) {
                    e.printStackTrace();
                }
                TsMetaDataRetriever retriever = new TsMetaDataRetriever();
                Log.d(TAG, "start feeding...");
                retriever.startFeeding();
                int size = 0;
                while (true) {
                    try {
                        size = source.read(data, 0, TsMetaDataRetriever.BUFFER_SIZE);
                        if (size == 0 || size == C.RESULT_END_OF_INPUT) {
                            Log.d(TAG, "end feeding...");
                            break;
                        }
                    } catch (FileDataSource.FileDataSourceException e) {
                        e.printStackTrace();
                    }
                    retriever.feedData(data, size);
                }
                retriever.finishFeeding();
                ExtractorMetaData metaData = retriever.getMetaData();

                // serialize
                try {
                    ObjectOutputStream out = new ObjectOutputStream(
                            new FileOutputStream(recordFile.replace(".ts", ".dat")));
                    out.writeObject(metaData);
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Log.d(TAG, "complete meta data generation...");
            }
        }).start();
    }

    private boolean checkAndRequestPermissions() {
        List<String> listPermissionsNeeded = new ArrayList<>();

        for (String request : mPermissionsNeeded) {
            int permission = ContextCompat.checkSelfPermission(getApplicationContext(), request);
            if (permission != PackageManager.PERMISSION_GRANTED)
                listPermissionsNeeded.add(request);
        }

        if (!listPermissionsNeeded.isEmpty()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), PERMISSIONS_REQUEST_ID);
            }
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {

        switch (requestCode) {
            case PERMISSIONS_REQUEST_ID: {
                if (grantResults.length > 0) {
                    boolean permssionsGranted = true;
                    for (int i : grantResults) {
                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED)
                            permssionsGranted = false;
                    }
                    if (permssionsGranted) {
                        startRecodingTest();
                    }
                }
                return;
            }
        }
    }
}
