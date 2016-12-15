package pmm.pbm.service.results;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pmm.pbm.data.base.entity.Author;

@Data
@EqualsAndHashCode(callSuper = true)
public class AuthorVO extends Author {
    /**
     * Number of books for this tag.
     */
    private Integer books;

    public String getWho() {
        return getName();
    }
}
