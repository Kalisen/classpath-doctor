
package org.kalisen.classpathdoctor;

public class DefaultVersion implements Version {

    private int major = 0;
    private int minor = 0;
    private int patch = 0;

    public DefaultVersion() {
        super();
    }

    public DefaultVersion(int major, int minor, int patch) {
        super();
        setMajor(major);
        setMinor(minor);
        setPatch(patch);
    }

    @Override
    public String toString() {
        return new StringBuilder().append(getMajor())
                .append(".")
                .append(getMinor())
                .append(".")
                .append(getPatch())
                .toString();
    }

    public int getMajor() {
        return this.major;
    }

    public void setMajor(int major) {
        this.major = major;
    }

    public int getMinor() {
        return this.minor;
    }

    public void setMinor(int minor) {
        this.minor = minor;
    }

    public int getPatch() {
        return this.patch;
    }

    public void setPatch(int patch) {
        this.patch = patch;
    }

}
