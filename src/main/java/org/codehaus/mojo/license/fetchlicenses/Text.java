package org.codehaus.mojo.license.fetchlicenses;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class Text {
    private final String name;
    private final String text;

    public Text(String text) {
        this("LICENSE.txt", text);
    }

    public Text(String name, String text) {
        this.name = name;
        this.text = text;
    }

    public String text() {
        return text;
    }

    public String name() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Text)){
            return false;
        }
        Text that = (Text) obj;
        return new EqualsBuilder().append(this.name, that.name).append(this.text, that.text).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(this.name).append(this.text).toHashCode();
    }

    @Override
    public String toString() {
        return text;
    }
}