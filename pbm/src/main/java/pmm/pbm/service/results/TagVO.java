package pmm.pbm.service.results;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pmm.pbm.data.base.entity.Tag;

@Data
@EqualsAndHashCode(callSuper = true)
public class TagVO extends Tag {
    private static final long serialVersionUID = -4288500747654163394L;

    /**
     * Number of books for this tag.
     */
    private Integer books;

    public String getWhat() {
        return getName();
    }
}
