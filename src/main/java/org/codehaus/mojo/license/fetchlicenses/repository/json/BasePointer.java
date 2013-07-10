package org.codehaus.mojo.license.fetchlicenses.repository.json;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class BasePointer implements Pointer {

    private final String name;
    private final String path;

    public BasePointer(String name, String path) {
        this.name = name;
        this.path = path;
    }

    public String name() {
        return name;
    }

    public String path() {
        return path;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(name).append(path()).hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if( ! (obj instanceof BasePointer)){
            return false;
        }
        BasePointer that = (BasePointer) obj;
        return new EqualsBuilder().append(this.name, that.name).append(this.path, that.path).isEquals();
    }

    @Override
    public String toString() {
        return name+": "+path;
    }
}
