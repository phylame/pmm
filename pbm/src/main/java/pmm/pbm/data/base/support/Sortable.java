package pmm.pbm.data.base.support;

import org.apache.commons.lang3.StringUtils;

public interface Sortable {

    String getOrderByClause();

    void setOrderByClause(String clause);

    default void addSort(String sortBy) {
        String sortby = getOrderByClause();
        if (StringUtils.isEmpty(sortby)) {
            sortby = sortBy;
        } else {
            sortby += ", " + sortBy;
        }
        setOrderByClause(sortby);
    }

    default void addSort(String field, String order) {
        if (!"asc".equalsIgnoreCase(order) && !"desc".equalsIgnoreCase(order)) {
            throw new IllegalArgumentException("order must be 'asc' or 'desc': " + String.valueOf(order));
        }
        addSort(field + " " + order);
    }
}
