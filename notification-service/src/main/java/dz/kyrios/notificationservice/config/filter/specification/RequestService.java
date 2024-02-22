package dz.kyrios.notificationservice.config.filter.specification;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

public class RequestService {
    public static Sort.Direction getSortDirection(SearchSort sortItem) {
        if (!sortItem.isDesc()) {
            return Sort.Direction.ASC;
        } else {
            return Sort.Direction.DESC;
        }
    }

    public static List<Sort.Order> getOrders(String sortArray) {
        List<Sort.Order> orders = new ArrayList<>();
        if (sortArray == null || sortArray.length() == 0) return orders;
        try {
            SearchSort[] sorts = new ObjectMapper().readValue(sortArray, SearchSort[].class);
            if (sorts != null) for (SearchSort sortItem : sorts) {
                orders.add(new Sort.Order(getSortDirection(sortItem), sortItem.getSelector()));
            }
        } catch (Exception e) {
            e.printStackTrace();
            orders = new ArrayList<>();
        }
        return orders;
    }
}
