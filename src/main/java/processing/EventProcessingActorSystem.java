package processing;

import akka.actor.ActorSystem;

public enum EventProcessingActorSystem {

    INSTANCE;

    private ActorSystem system;


    public ActorSystem getSystem(){
        return system;
    }
}
