package pmm.pbm.service.params;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pmm.pbm.util.cms.QueryField;

@Data
@EqualsAndHashCode(callSuper = true)
public class ListFileDTO extends PagingDTO {
    @QueryField
    private String id;

    @QueryField
    private String md5;

    @QueryField
    private String name;
}
