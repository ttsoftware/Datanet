package entities;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText;

import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.*;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class PropFind {

    private String propname;
    private String allprop;
    private Prop[] prop;

    public String getPropname() {
        return propname;
    }

    public void setPropname(String propname) {
        this.propname = propname;
    }

    public String getAllprop() {
        return allprop;
    }

    public void setAllprop(String allprop) {
        this.allprop = allprop;
    }

    public Prop[] getProp() {
        return prop;
    }

    public void setProp(Prop[] prop) {
        this.prop = prop;
    }
}
