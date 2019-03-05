package processing;


import akka.actor.AbstractActor;
import models.Track;
import storage.RedisClient;
import storage.RedisStoreKey;
import storage.StorageMessage;

import java.util.HashMap;
import java.util.Map;

public class EntityProcessingActor extends AbstractActor {

    private RedisClient redisClient = RedisClient.INSTANCE;

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(NewTrackUpdateMsg.class, this::processUpdateMessage)
//                .match(NewSystemUpdateMsg.class, this::processSystemMessage)
                .build();
    }

    private void processUpdateMessage(NewTrackUpdateMsg msg) {
        Track cachedTrack = null;
        StorageMessage dbCached = redisClient.fetchLatestTrack(msg.getMessage().getKey(), new RedisStoreKey(msg.getEnvironment(), msg.getTopicName()));
        if (dbCached != null) {
            cachedTrack = getTrackFromStorageMessage(dbCached);
        }

        Track combinedTrack = msg.getMessage().combine(cachedTrack);
        if (!combinedTrack.equals(cachedTrack)) {
            StorageMessage storageMessage = createStorageMessage(combinedTrack);
            redisClient.updateTrack(storageMessage, new RedisStoreKey(msg.getEnvironment(), msg.getTopicName()));
        }
        postProcessMessage(msg);
    }

    private void postProcessMessage(NewTrackUpdateMsg msg) {
        Integer trackId = msg.getMessage().trackId;
        if (msg.isFinalUpdate()) {
            // schedule remove from redis?
            //            tracksCache.remove(trackId);
        }
    }

    private StorageMessage createStorageMessage(Track track) {
        String jsonMessage = JsonSerializer.serialize(track);
        return new StorageMessage(jsonMessage, track.getKey());
    }

    private Track getTrackFromStorageMessage(StorageMessage message) {
        Track track = JsonSerializer.deserialize(message.getJsonMessage());
        return track;
    }
}
