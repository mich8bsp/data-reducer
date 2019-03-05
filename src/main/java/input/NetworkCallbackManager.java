package input;

import akka.actor.ActorRef;
import models.Track;
import processing.NewTrackUpdateMsg;

import java.util.Set;
import java.util.stream.Collectors;

public class NetworkCallbackManager {

    private Set<Topic> topics;
    private String environment;
    private ActorRef processingActor;

    public NetworkCallbackManager(String environment, Set<String> topicNames, ActorRef processingActor){
        this.environment = environment;
        topics = topicNames.stream().map(this::createTopic).collect(Collectors.toSet());
        this.processingActor = processingActor;
    }

    public Topic<Track> createTopic(String topicName){
        return new Topic<Track>(topicName, new ITopicCallback<Track>() {
            @Override
            public void onDataArrival(Track entity) {
                processingActor.tell(new NewTrackUpdateMsg(entity, environment, topicName),
                        ActorRef.noSender());
            }

            @Override
            public void onDataRemoval(Track entity) {

            }

            @Override
            public void onConnectionLost(Track entity) {

            }
        });
    }
}
