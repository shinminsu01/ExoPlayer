package com.google.android.exoplayer2.extractor;

/**
 * Created by shinms on 17. 4. 28.
 */

public interface MetaDataRetriever  {

    void startFeeding();

    int feedData(byte[] data, int size);

    void finishFeeding();

    ExtractorMetaData getMetaData();
}
