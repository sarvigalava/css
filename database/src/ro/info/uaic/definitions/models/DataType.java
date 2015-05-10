package ro.info.uaic.definitions.models;

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
}
