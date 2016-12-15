package pmm.pbm.data.base.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import pmm.pbm.data.base.support.Entity;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class CMSEndpoint extends Entity {
    /**
     * Tag of the item
     *
     * @mbg.generated
     */
    private String tag;

    /**
     * Name of the item
     *
     * @mbg.generated
     */
    private String name;

    /**
     * Type of the query service
     *
     * @mbg.generated
     */
    private String service;
}