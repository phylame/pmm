package pmm.pbm.service.results;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pmm.pbm.data.base.entity.Author;

@Data
@EqualsAndHashCode(callSuper = true)
public class AuthorVO extends Author {
    private static final long serialVersionUID = 6892296870021620732L;

    /**
     * Number of books for this tag.
     */
    private Integer books;

    public String getWhat() {
        return getName();
    }
}
