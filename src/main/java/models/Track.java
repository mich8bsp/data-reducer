package models;

public class Track {
    public Ellipse lp;
    public Ellipse ip;
    public Point sv;
    public TrackInfo info;
    public Integer trackId;

    public String getKey(){
        return trackId.toString() + "-" + info.systemId;
    }
}
