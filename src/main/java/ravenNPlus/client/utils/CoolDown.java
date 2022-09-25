package ravenNPlus.client.utils;

public class CoolDown {
    private long start;
    private long lasts;
    private boolean checkedFinish;

    public CoolDown(long lasts) { this.lasts = lasts; }
    public void start() { this.start = System.currentTimeMillis(); }

    public boolean hasFinished() {
        if(System.currentTimeMillis() >= start + lasts) {
            return true;
        }

        return false;
    }

    public boolean firstFinish(){
        if(System.currentTimeMillis() >= start + lasts && !checkedFinish) {
            checkedFinish = true;
            return true;
        }
        return false;
    }

    public void setCooldown(long time) { this.lasts = time; }
    public long getElapsedTime() { return System.currentTimeMillis() - this.start; }
    public long getTimeLeft() { return lasts - (System.currentTimeMillis() - start); }

}