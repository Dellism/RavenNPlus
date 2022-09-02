package a.b.utils;

public class BlockPosUtil {

    double x;
    double y;
    double z;

    public BlockPosUtil(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public BlockPosUtil(int x, int y, int z) {
        this.x = (double)x;
        this.y = (double)y;
        this.z = (double)z;
    }

    public BlockPosUtil add(double x, double y, double z) {
        return x == 0.0 && y == 0.0 && z == 0.0 ? this : new BlockPosUtil(this.getX() + x, this.getY() + y, this.getZ() + z);
    }

    public BlockPosUtil subtract(double x, double y, double z) {
        return x == 0.0 && y == 0.0 && z == 0.0 ? this : new BlockPosUtil(this.getX() - x, this.getY() - y, this.getZ() - z);
    }

    public BlockPosUtil add(int x, int y, int z) {
        return (double)x == 0.0 && (double)y == 0.0 && (double)z == 0.0 ? this : new BlockPosUtil(this.getX() + (double)x, this.getY() + (double)y, this.getZ() + (double)z);
    }

    public BlockPosUtil subtract(int x, int y, int z) {
        return (double)x == 0.0 && (double)y == 0.0 && (double)z == 0.0 ? this : new BlockPosUtil(this.getX() - (double)x, this.getY() - (double)y, this.getZ() - (double)z);
    }

    public double getX() {
        return this.x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return this.y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getZ() {
        return this.z;
    }

    public void setZ(double z) {
        this.z = z;
    }

}