package com.google.android.exoplayer2.extractor;

import java.io.Serializable;

/**
 * Created by shinms on 17. 4. 27.
 */

public class SeekPoint implements Serializable {

    public interface EventListener {
        void onSeekPointDetected(SeekPoint seekPoint);
    }

    private long timeUs;
    private long offset;

    public SeekPoint(long timeUs, long offset) {
        this.timeUs = timeUs;
        this.offset = offset;
    }

    public void setTimeUs(long timeUs) {
        this.timeUs = timeUs;
    }

    public long getTimeUs() {
        return timeUs;
    }

    public void setOffset(long offset) {
        this.offset = offset;
    }

    public long getOffset() {
        return offset;
    }

    public String toString(){
        return "time:" + timeUs + ", offset:" + offset;
    }
}
