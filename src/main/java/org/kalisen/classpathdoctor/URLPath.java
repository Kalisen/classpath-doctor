package org.kalisen.classpathdoctor;

import java.io.IOException;
import java.net.URL;

public class URLPath implements PathEntry {

    private URL url = null;
    private Version version = new DefaultVersion();
    
    public URLPath(URL url) {
        setUrl(url);
    }

    public Version getVersion() {
        return this.version;
    }

    public String getPath() {
        return this.url.getPath();
    }

    public boolean exists() {
        boolean result = false;
        try {
            result =  this.url.getContent() != null;
        } catch (IOException e) {
            // result stays false;
        }
        return result;
    }

    public URL getUrl() {
        return this.url;
    }

    public void setUrl(URL url) {
        if (url == null) {
            throw new IllegalArgumentException("null is not a valid argument");
        }
        this.url = url;
    }
    
}
