package dz.kyrios.adminservice.config.auditlog;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class EntityChangeInterceptor extends EmptyInterceptor {

    @Override
    public void onDelete(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {
        String data = getJson(null, state, propertyNames);
        audit(entity, data, "DELETE");
    }

    @Override
    public boolean onFlushDirty(Object entity, Serializable id, Object[] currentState, Object[] previousState, String[] propertyNames, Type[] types) {
        String data = getJson(currentState, previousState, propertyNames);
        audit(entity, data, "UPDATE");
        return false;
    }

    @Override
    public boolean onSave(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {
        if (entity.getClass().isAnnotationPresent(IgnoreAudit.class)) return false;
        String data = getJson(state, null, propertyNames);
        audit(entity, data, "CREATE");
        return super.onSave(entity, id, state, propertyNames, types);
    }

    private void audit(Object entity, String data, String action) {

        TraceService traceService = (TraceService) ApplicationContextProvider.getApplicationContext().getBean("traceService");

        EntityTrace trace = new EntityTrace();
        trace.setEntityName(entity.getClass().getSimpleName());

        Long entityId = getEntityId(entity);
        trace.setEntityId(entityId);

        trace.setAction(action);
        trace.setData(data);

        trace.setTimestamp(LocalDateTime.now());

        traceService.saveTrace(trace);
    }

    private Long getEntityId(Object entity) {
        try {
            Field idField = entity.getClass().getDeclaredField("id");
            idField.setAccessible(true);
            return (Long) idField.get(entity);
        } catch (Exception e) {
            // Handle exceptions appropriately
            e.printStackTrace();
            return null;
        }
    }

    public String getJson(Object[] currentState, Object[] previousState, String[] propertyNames) {
        List<EntityTraceDetail> entityTraceDetails = new ArrayList<>();

        if (currentState == null) {
            for (int i = 0; i < propertyNames.length; i++) {
                if (previousState[i] instanceof LocalDateTime || previousState[i] instanceof LocalDate) {
                    Object previosDate = previousState[i] == null ? null : previousState[i].toString();
                    EntityTraceDetail obj = new EntityTraceDetail(propertyNames[i], previosDate, null);
                    entityTraceDetails.add(obj);
                    continue;
                }
                EntityTraceDetail obj = new EntityTraceDetail(propertyNames[i], previousState[i], null);
                entityTraceDetails.add(obj);
            }
        } else if (previousState == null) {
            for (int i = 0; i < propertyNames.length; i++) {
                if (currentState[i] instanceof LocalDateTime || currentState[i] instanceof LocalDate) {
                    Object currentDate = currentState[i] == null ? null : currentState[i].toString();
                    EntityTraceDetail obj = new EntityTraceDetail(propertyNames[i], null, currentDate);
                    entityTraceDetails.add(obj);
                    continue;
                }
                EntityTraceDetail obj = new EntityTraceDetail(propertyNames[i], null, currentState[i]);
                entityTraceDetails.add(obj);
            }
        } else {
            for (int i = 0; i < propertyNames.length; i++) {
                if (previousState[i] instanceof LocalDateTime || previousState[i] instanceof LocalDate) {
                    Object previosDate = previousState[i] == null ? null : previousState[i].toString();
                    Object currentDate = currentState[i] == null ? null : currentState[i].toString();
                    EntityTraceDetail obj = new EntityTraceDetail(propertyNames[i], previosDate, currentDate);
                    entityTraceDetails.add(obj);
                    continue;
                }
                EntityTraceDetail obj = new EntityTraceDetail(propertyNames[i], previousState[i], currentState[i]);
                entityTraceDetails.add(obj);
            }
        }

        try {
            ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
            String json = ow.writeValueAsString(entityTraceDetails);
            return json;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}



