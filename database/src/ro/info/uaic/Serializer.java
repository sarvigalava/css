package ro.info.uaic;

import org.springframework.stereotype.Service;

import java.io.*;
import java.util.Objects;

/**
 * Created by lotus on 26.04.2015.
 */
@Service
public class Serializer
{
    public byte[] serialize(Object obj) throws IOException
    {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ObjectOutputStream os = new ObjectOutputStream(out);
        os.writeObject(obj);
        return out.toByteArray();
    }

    public <T> T deserialize(byte[] data, Class<T> clazz) throws IOException, ClassNotFoundException
    {
        ByteArrayInputStream in = new ByteArrayInputStream(data);
        ObjectInputStream is = new ObjectInputStream(in);
        return (T) is.readObject();
    }
}
