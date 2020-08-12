package co.kr.reo;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;

public class TimestampAdapter extends TypeAdapter<Timestamp> {
    @Override
    public void write(JsonWriter out, Timestamp timestamp) throws IOException {
        out.value(timestamp.getTime() / 1000);
    }

    @Override
    public Timestamp read(JsonReader in) throws IOException {
        return new Timestamp(in.nextLong() * 1000);
    }
}