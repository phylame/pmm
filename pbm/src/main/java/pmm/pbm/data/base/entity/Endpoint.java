package pmm.pbm.data.base.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import pmm.pbm.data.base.support.Entity;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Endpoint extends Entity {
    /**
     * Endpoint name
     *
     * @mbg.generated
     */
    private String name;

    /**
     * Mapped name
     *
     * @mbg.generated
     */
    private String mapping;

    /**
     * Type of id
     *
     * @mbg.generated
     */
    private String idType;

    /**
     * Type of entity
     *
     * @mbg.generated
     */
    private String entityType;

    /**
     * Type of example
     *
     * @mbg.generated
     */
    private String exampleType;
}