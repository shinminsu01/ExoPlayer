package com.google.android.exoplayer2.extractor;

import java.io.Serializable;
import java.util.List;

/**
 * Created by shinms on 17. 4. 28.
 */

public class ExtractorMetaData implements Serializable {

    private long durationUs;
    private List<SeekPoint> seekPoints;

    public ExtractorMetaData() {
        durationUs = 0;
        seekPoints = null;
    }

    public ExtractorMetaData(long durationUs, List<SeekPoint> seekPoints) {
        this.durationUs = durationUs;
        this.seekPoints = seekPoints;
    }

    public long getDuration() {
        return durationUs;
    }

    public void setDuration(long durationUs) {
        this.durationUs = durationUs;
    }

    public List<SeekPoint> getSeekPoints() {
        return seekPoints;
    }

    public void setSeekPoints(List<SeekPoint> seekPoints){
        this.seekPoints = seekPoints;
    }
}
