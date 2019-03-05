package input;

public interface ITopicCallback<T> {

    void onDataArrival(T entity);
    void onDataRemoval(T entity);
    void onConnectionLost(T entity);
}
