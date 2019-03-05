package storage;

import java.util.Objects;

public class RedisStoreKey {

    public String environment;
    public String topicName;

    public RedisStoreKey(String environment, String topicName) {
        this.environment = environment;
        this.topicName = topicName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RedisStoreKey that = (RedisStoreKey) o;
        return Objects.equals(environment, that.environment) &&
                Objects.equals(topicName, that.topicName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(environment, topicName);
    }

    @Override
    public String toString() {
        return environment + "-" + topicName;
    }
}
