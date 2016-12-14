package pmm.pbm.data.base.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import pmm.pbm.data.base.support.Entity;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Author extends Entity {
    /**
     * Name of author
     *
     * @mbg.generated
     */
    private String name;

    /**
     * Brief introduction of author
     *
     * @mbg.generated
     */
    private String intro;
}