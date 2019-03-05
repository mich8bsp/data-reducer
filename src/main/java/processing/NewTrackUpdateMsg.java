package processing;

import models.ETrackingStatus;
import models.Track;

public class NewTrackUpdateMsg {

    private Track message;
    private String environment;
    private String topicName;

    public NewTrackUpdateMsg(Track message, String environment, String topicName){
        this.message = message;
        this.environment = environment;
        this.topicName = topicName;
    }

    public Track getMessage(){
        return message;
    }

    public String getTopicName() {
        return topicName;
    }

    public String getEnvironment() {
        return environment;
    }

    public boolean isFinalUpdate() {
        return message.info!=null && (message.info.status == ETrackingStatus.DROPPED ||
                message.info.status == ETrackingStatus.IMPACTED);
    }
}
