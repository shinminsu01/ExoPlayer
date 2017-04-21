package com.google.android.exoplayer2.extractor.ts;

/**
 * Created by shinms on 17. 4. 4.
 */

public class SyncFrame {

    interface Listener {
        void onSyncFrameDetected(SyncFrame syncFrame);
    }

    private long timeUs;
    private long offset;

    public SyncFrame(long timeUs, long offset) {
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
}
