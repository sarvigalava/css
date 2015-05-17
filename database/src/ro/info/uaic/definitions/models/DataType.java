package ro.info.uaic.definitions.models;

import java.util.Date;

/**
 * Created by lotus on 26.04.2015.
 */
public enum DataType
{
    STRING, INTEGER, FLOAT, DATE, RAW;

    public static DataType parse(String name)
    {
        for (DataType type : values())
        {
            if (name.trim().equalsIgnoreCase(type.toString()))
            {
                return type;
            }
        }

        throw new IllegalArgumentException(name);
    }

    public static DataType getType(Object object)
    {
        if (object instanceof String)
            return STRING;

        if (object instanceof Long)
            return INTEGER;

        if (object instanceof Double)
            return FLOAT;

        if (object instanceof Date)
            return DATE;

        if (object instanceof byte[])
            return RAW;

        throw new IllegalArgumentException(object.getClass().getName());
    }
}
