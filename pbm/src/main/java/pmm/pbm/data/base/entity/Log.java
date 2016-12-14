package pmm.pbm.data.base.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import pmm.pbm.data.base.support.Entity;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Log extends Entity {
    /**
     * Id of operator
     *
     * @mbg.generated
     */
    private String operatorId;

    /**
     * Id of source
     *
     * @mbg.generated
     */
    private Boolean sourceId;

    /**
     * Time of modification
     *
     * @mbg.generated
     */
    private Integer time;

    /**
     * Type of modification
     *
     * @mbg.generated
     */
    private String type;

    /**
     * Id of record modified
     *
     * @mbg.generated
     */
    private String targetId;

    /**
     * Description of modification
     *
     * @mbg.generated
     */
    private String content;
}