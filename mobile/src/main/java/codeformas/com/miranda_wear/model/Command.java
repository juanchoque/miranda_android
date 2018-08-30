package codeformas.com.miranda_wear.model;

public class Command {
    private String cmd;
    private String content;
    private Double latitude;
    private Double longitude;
    private String imei;
    private String imeiDest;

    public String getImeiDest() {
        return imeiDest;
    }

    public void setImeiDest(String imeiDest) {
        this.imeiDest = imeiDest;
    }

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString(){
        return this.cmd + "|" + this.content + "|" + this.latitude + "|" + this.longitude + "|" + this.imei + "|" + this.imeiDest;
    }
}
