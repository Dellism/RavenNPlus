package ravenNPlus.client.utils.vec3dtemp;

/**
 * A 3-dimensional, single-precision, floating-point vector.
 *
 */
public class Vec3f {
    /**
     * The x coordinate.
     */
    public float x;

    /**
     * The y coordinate.
     */
    public float y;

    /**
     * The z coordinate.
     */
    public float z;

    public Vec3f() { }

    public Vec3f(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vec3f(Vec3f v) {
        this.x = v.x;
        this.y = v.y;
        this.z = v.z;
    }

    public void set(Vec3f v) {
        this.x = v.x;
        this.y = v.y;
        this.z = v.z;
    }

    public void set(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public final void mul(float s) {
        this.x *= s;
        this.y *= s;
        this.z *= s;
    }

    /**
     * Sets the value of this vector to the difference
     * of vectors t1 and t2 (this = t1 - t2).
     * @param t1 the first vector
     * @param t2 the second vector
     */
    public void sub(Vec3f t1, Vec3f t2) {
        this.x = t1.x - t2.x;
        this.y = t1.y - t2.y;
        this.z = t1.z - t2.z;
    }

    /**
     * Sets the value of this vector to the difference of
     * itself and vector t1 (this = this - t1) .
     * @param t1 the other vector
     */
    public void sub(Vec3f t1) {
        this.x -= t1.x;
        this.y -= t1.y;
        this.z -= t1.z;
    }

    /**
     * Sets the value of this vector to the sum
     * of vectors t1 and t2 (this = t1 + t2).
     * @param t1 the first vector
     * @param t2 the second vector
     */
    public void add(Vec3f t1, Vec3f t2) {
        this.x = t1.x + t2.x;
        this.y = t1.y + t2.y;
        this.z = t1.z + t2.z;
    }

    /**
     * Sets the value of this vector to the sum of
     * itself and vector t1 (this = this + t1) .
     * @param t1 the other vector
     */
    public void add(Vec3f t1) {
        this.x += t1.x;
        this.y += t1.y;
        this.z += t1.z;
    }

    /**
     * Returns the length of this vector.
     * @return the length of this vector
     */
    public float length() {
        return (float) Math.sqrt(this.x*this.x + this.y*this.y + this.z*this.z);
    }

    /**
     * Normalize this vector.
     */
    public void normalize() {
        float norm = 1.0f / length();
        this.x = this.x * norm;
        this.y = this.y * norm;
        this.z = this.z * norm;
    }

    /**
     * Sets this vector to be the vector cross product of vectors v1 and v2.
     * @param v1 the first vector
     * @param v2 the second vector
     */
    public void cross(Vec3f v1, Vec3f v2) {
        float tmpX;
        float tmpY;

        tmpX = v1.y * v2.z - v1.z * v2.y;
        tmpY = v2.x * v1.z - v2.z * v1.x;
        this.z = v1.x * v2.y - v1.y * v2.x;
        this.x = tmpX;
        this.y = tmpY;
    }

    /**
     * Computes the dot product of this vector and vector v1.
     * @param v1 the other vector
     * @return the dot product of this vector and v1
     */
    public float dot(Vec3f v1) {
        return this.x * v1.x + this.y * v1.y + this.z * v1.z;
    }

    /**
     * Returns the hashcode for this Vec3f.
     * @return      a hash code for this Vec3f.
     */
    @Override
    public int hashCode() {
        int bits = 7;
        bits = 31 * bits + Float.floatToIntBits(x);
        bits = 31 * bits + Float.floatToIntBits(y);
        bits = 31 * bits + Float.floatToIntBits(z);
        return bits;
    }

    /**
     * Determines whether or not two 3D points or vectors are equal.
     * Two instances of Vec3f are equal if the values of their
     * x, y and z member fields,
     * representing their position in the coordinate space, are the same.
     * @param obj an object to be compared with this Vec3f
     * @return true if the object to be compared is
     *         an instance of Vec3f and has
     *         the same values; false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof Vec3f) {
            Vec3f v = (Vec3f) obj;
            return (x == v.x) && (y == v.y) && (z == v.z);
        }
        return false;
    }

    /**
     * Returns a String that represents the value
     * of this Vec3f.
     * @return a string representation of this Vec3f.
     */
    @Override
    public String toString() {
        return "Vec3f[" + x + ", " + y + ", " + z + "]";
    }
}
