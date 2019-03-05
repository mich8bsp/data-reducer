package input;

public class Topic<T> {

    private final String topicName;
    private ITopicCallback<T> callback;

    Topic(String topicName, ITopicCallback<T> callback){
        this.topicName = topicName;
        this.callback = callback;
    }

}
