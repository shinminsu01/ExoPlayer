package com.google.android.exoplayer2.demo;

import android.net.Uri;
import android.util.Log;

import com.google.android.exoplayer2.upstream.ByteArrayDataSource;
import com.google.android.exoplayer2.upstream.DataSource;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by naver on 2017. 6. 5..
 */

public class MemoryBufferDataSourceFactory implements DataSource.Factory {
    private static final String TAG = "MemoryBufferDataSourceFactory";

    private final byte[] data;

    MemoryBufferDataSourceFactory(Uri uri) {
        Log.d(TAG, "create MemoryBufferDataSourceFactory using uri");

        File file = new File(uri.getPath());
        int size = (int) file.length();
        this.data = new byte[size];
        BufferedInputStream buf = null;
        try {
            buf = new BufferedInputStream(new FileInputStream(file));
            buf.read(data, 0, data.length);
            buf.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    MemoryBufferDataSourceFactory(byte[] data) {
        Log.d(TAG, "create MemoryBufferDataSourceFactory using byte array");

        this.data = data;
    }

    @Override
    public DataSource createDataSource() {
        Log.d(TAG, "createDataSource");

        return new ByteArrayDataSource(data);
    }
}
