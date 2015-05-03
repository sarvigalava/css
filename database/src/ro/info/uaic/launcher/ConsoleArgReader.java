package ro.info.uaic.launcher;

import org.springframework.stereotype.Service;

/**
 * Created by lotus on 26.04.2015.
 */
@Service
public class ConsoleArgReader
{
    public Parameters read(String[] args)
    {
        Parameters parameters = new Parameters();

        for (int i = 0; i < args.length / 2; ++i)
        {
            String key = args[i * 2];
            String val = args[i * 2 + 1];
            if (key.startsWith("-"))
            {
                Parameter parameter = Parameter.fromKey(key.substring(1));
                parameters.add(parameter, val);
            }
        }

        return parameters;
    }
}
