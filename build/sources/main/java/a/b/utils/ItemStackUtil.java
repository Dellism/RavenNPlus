package a.b.utils;

public class ItemStackUtil {

    public int id;
    public int size;
    public int meta;
    public String name;
    public String hashCode;

    public ItemStackUtil(int id, int size, int meta, String name, String hashCode) {
        this.id = id;
        this.size = size;
        this.meta = meta;
        this.hashCode = hashCode;
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSize() {
        return this.size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getMeta() {
        return this.meta;
    }

    public void setMeta(int meta) {
        this.meta = meta;
    }

    public String getHashCode() {
        return this.hashCode;
    }

    public void setHashCode(String hashCode) {
        this.hashCode = hashCode;
    }


}
