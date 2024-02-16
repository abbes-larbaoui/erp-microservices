package dz.kyrios.adminservice.config.auditlog;

import org.springframework.stereotype.Service;

@Service
public class TraceService {

    public void saveTrace(EntityTrace trace) {
        //TODO: send audit data to audit-log-service
        trace.setModule("ADMIN");
        System.out.println(trace);
    }
}
