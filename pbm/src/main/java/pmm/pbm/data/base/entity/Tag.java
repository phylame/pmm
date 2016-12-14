package pmm.pbm.data.base.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import pmm.pbm.data.base.support.Entity;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Tag extends Entity {
    /**
     * Name of tag
     *
     * @mbg.generated
     */
    private String name;

    /**
     * Brief introduction of tag
     *
     * @mbg.generated
     */
    private String intro;

    /**
     * Rank of tag
     *
     * @mbg.generated
     */
    private Integer rank;

    /**
     * Enable state
     *
     * @mbg.generated
     */
    private Boolean enable;
}