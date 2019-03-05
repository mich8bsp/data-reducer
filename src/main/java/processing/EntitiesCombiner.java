package processing;

import models.Track;

public class EntitiesCombiner {

    public static Track combine(Track update, Track cached){
        if(cached==null){
            return update;
        }
        if(update.info==null && cached.info!=null){
            update.info = cached.info;
        }
        if(update.lp == null && cached.lp!=null){
            update.lp = cached.lp;
        }
        if(update.ip == null && cached.ip!=null){
            update.ip = cached.ip;
        }
        if(update.sv == null && cached.sv!=null){
            update.sv = cached.sv;
        }
        return update;
    }
}
