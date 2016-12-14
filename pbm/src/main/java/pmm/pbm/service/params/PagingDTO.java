package pmm.pbm.service.params;

import lombok.Data;
import pmm.pbm.data.base.support.Paged;
import pmm.pbm.data.base.support.Sortable;

@Data
public class PagingDTO implements Sortable {
    private int offset = 1;

    private int limit = Paged.DEFAULT_LIMITS;

    private String orderByClause;
}
