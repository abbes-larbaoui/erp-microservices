package dz.kyrios.notificationservice.config.filter.specification;

import lombok.Data;

@Data
public class SearchSort {
    private String selector;
    private boolean desc;

    public SearchSort() {
    }
}
