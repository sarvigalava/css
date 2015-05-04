package ro.info.uaic.launcher;

import org.springframework.stereotype.Component;

import java.util.HashMap;

/**
 * Created by lotus on 26.04.2015.
 */
@Component
public class Parameters
{
    private HashMap<Parameter, String> values = new HashMap<>();

    public void add(Parameter parameter, String value)
    {
        values.put(parameter, value);
    }

    public String get(Parameter parameter)
    {
        return values.get(parameter);
    }

    public String getStorageDirectory()
    {
        return get(Parameter.STORAGE_DIR);
    }
}
