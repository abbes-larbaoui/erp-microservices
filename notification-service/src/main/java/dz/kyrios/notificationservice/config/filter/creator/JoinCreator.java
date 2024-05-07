package dz.kyrios.notificationservice.config.filter.creator;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Root;

public class JoinCreator {
    static public Join createJoin(Root root, String field){
        String[] args =  field.split("\\.");
        String attribute = args[args.length-1];
        Join join =null;
        if(args.length > 1 ){
            join = root.join(args[0]);
            for (int i = 1 ; i < args.length-1 ; i++) {
                join = join.join(args[i]);
            }
            return join;
        }
        return null;
    }
}
