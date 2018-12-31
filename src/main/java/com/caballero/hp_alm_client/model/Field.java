package com.caballero.hp_alm_client.model;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "Field")
@XmlAccessorType(XmlAccessType.FIELD)
public class Field
{
    @XmlElement(name = "Value", required = true)
    private List<String> values;

    @XmlAttribute(name = "Name", required = true)
    private String name;

    public Field()
    {
    }

    public Field(String name)
    {
        name(name);
    }

    public Field(String name, String value)
    {
        name(name);
        value(value);
    }

    /**
     * Gets the value of the values property.
     *
     * @return
     */
    public List<String> values()
    {
        if (values == null)
        {
            values = new ArrayList<String>();
        }

        return values;
    }

    /**
     * Gets the first value of the values property.
     *
     * @return
     */
    public String value()
    {
        return values().isEmpty() ? null : values().get(0);
    }

    /**
     *  Add a new item to values property.
     *
     * @param value
     */
    public void value(String value)
    {
        values().add(value);
    }

    /**
     * Gets the value of the name property.
     *
     * @return
     */
    public String name()
    {
        return name;
    }

    /**
     * Sets the value of the name property.
     *
     * @param value
     */
    public void name(String value)
    {
        name = value;
    }
}
