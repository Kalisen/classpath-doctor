package org.kalisen.classpathdoctor;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

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
            throw new IllegalArgumentException(ResourceBundle.getBundle("UsersMessages")
                                               .getString("null.is.not.a.valid.argument"));
        }
        this.url = url;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((url == null) ? 0 : url.hashCode());
		result = prime * result + ((version == null) ? 0 : version.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		URLPath other = (URLPath) obj;
		if (url == null) {
			if (other.url != null)
				return false;
		} else if (!url.equals(other.url))
			return false;
		if (version == null) {
			if (other.version != null)
				return false;
		} else if (!version.equals(other.version))
			return false;
		return true;
	}

    
}
