package dz.kyrios.adminservice.config.auditlog;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TraceService {

    private final KafkaTemplate<String, EntityTrace> kafkaTemplate;

    public void saveTrace(EntityTrace trace) {
        trace.setModuleName("ADMIN");
        System.out.println(trace);

        //TODO: send audit data to audit-log-service
        kafkaTemplate.send("auditLogTopic", trace);
    }
}
