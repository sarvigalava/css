package ro.info.uaic.launcher;

/**
 * Created by lotus on 26.04.2015.
 */
public enum Parameter
{
    STORAGE_FILE("f"), PORT("p"), PASSWORD("pass");

    private String key;

    Parameter(String key)
    {
        this.key = key;
    }

    public static Parameter fromKey(String key)
    {
        for (Parameter p : values())
        {
            if (p.key.equalsIgnoreCase(key))
            {
                return p;
            }
        }

        return null;
    }


}
