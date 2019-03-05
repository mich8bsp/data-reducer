package storage;

import java.util.Objects;

public class StorageMessage {
    private long timestamp = System.currentTimeMillis();
    private String jsonMessage;
    private String key;

    public StorageMessage(String jsonMessage, String key) {
        this.jsonMessage = jsonMessage;
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String getJsonMessage() {
        return jsonMessage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StorageMessage that = (StorageMessage) o;
        return timestamp == that.timestamp &&
                Objects.equals(jsonMessage, that.jsonMessage) &&
                Objects.equals(key, that.key);
    }

    @Override
    public int hashCode() {
        return Objects.hash(timestamp, jsonMessage, key);
    }

    @Override
    public String toString() {
        return "StorageMessage{" +
                "timestamp=" + timestamp +
                ", jsonMessage='" + jsonMessage + '\'' +
                ", key='" + key + '\'' +
                '}';
    }
}
