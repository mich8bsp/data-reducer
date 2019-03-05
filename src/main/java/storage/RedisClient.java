package storage;

import org.redisson.Redisson;
import org.redisson.api.LocalCachedMapOptions;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

import java.util.HashMap;
import java.util.Map;

public enum RedisClient {
    INSTANCE;

    private RedisConfig config = new RedisConfig();
    private RedissonClient redissonClient = connectToRedis();
    private Map<RedisStoreKey, RMap<String, StorageMessage>> maps = new HashMap<>();

    private RedissonClient connectToRedis() {
        Config creationConfig = new Config();
        creationConfig
                .useSentinelServers()
                .addSentinelAddress(config.serverAddress + ":" + config.serverPort)
                .setMasterName(config.masterName)
                .setPassword(config.serverPassword);
        return Redisson.create(creationConfig);
    }

    public void updateTrack(StorageMessage newTrackUpdateMsg, RedisStoreKey redisStoreKey) {
        RMap<String, StorageMessage> storeMap = getOrCreateMap(redisStoreKey);
        storeMap.fastPutAsync(newTrackUpdateMsg.getKey(), newTrackUpdateMsg)
                .whenCompleteAsync((res, exc) -> {
                    String excMessage = "";
                    if(exc!=null){
                       excMessage =  " " + exc.getMessage();
                    }
                    System.out.println("Stored new update for key " + newTrackUpdateMsg.getKey() + " result: " + res + excMessage);
                });
    }

    public StorageMessage fetchLatestTrack(String trackKey, RedisStoreKey redisStoreKey) {
        RMap<String, StorageMessage> storeMap = getOrCreateMap(redisStoreKey);
        return storeMap.get(trackKey);
    }

    private RMap<String, StorageMessage> getOrCreateMap(RedisStoreKey key){
        if(!maps.containsKey(key)){
            maps.put(key, redissonClient.getLocalCachedMap(key.toString(),
                    LocalCachedMapOptions.defaults())); //FIXME: change defaults for local cache map (maybe add eviction but it requires redisson pro)
        }
        return maps.get(key);
    }
}
