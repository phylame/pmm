package pmm.pbm.data.base.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import pmm.pbm.data.base.support.Entity;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class BookTag extends Entity {
    /**
     * Id of book
     *
     * @mbg.generated
     */
    private Integer bookId;

    /**
     * Id of tag
     *
     * @mbg.generated
     */
    private Integer tagId;
}