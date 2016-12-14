package pmm.pbm.service.params;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ListTagDTO extends PagingDTO {
    private String id;

    private String name;

    private Boolean enable;
}
