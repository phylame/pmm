package pmm.pbm.service.results;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pmm.pbm.data.base.entity.Tag;

@Data
@EqualsAndHashCode(callSuper = true)
public class TagVO extends Tag {
    /**
     * Number of books for this tag.
     */
    private Integer books;

    public String getWho() {
        return getName();
    }
}