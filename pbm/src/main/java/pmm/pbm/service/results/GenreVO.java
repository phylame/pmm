package pmm.pbm.service.results;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pmm.pbm.data.base.entity.Genre;

@Data
@EqualsAndHashCode(callSuper = true)
public class GenreVO extends Genre {
    private static final long serialVersionUID = -1291526036828011403L;

    /**
     * Number of books for this tag.
     */
    private Integer books;

    public String getWhat() {
        return getName();
    }
}
