package org.codehaus.mojo.license.fetchlicenses;

public class Text {
    public final String text;

    public Text(String text){
        this.text = text;
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Text)){
            return false;
        }
        Text that = (Text) obj;
        return text.equals(that.text);
    }

    @Override
    public int hashCode() {
        return text.hashCode();
    }
}