package pmm.pbm.data.base.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import pmm.pbm.data.base.support.Entity;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Storage extends Entity {
    /**
     * URL of file
     *
     * @mbg.generated
     */
    private String url;

    /**
     * MD5 of content
     *
     * @mbg.generated
     */
    private String md5;

    /**
     * Name of file
     *
     * @mbg.generated
     */
    private String name;
}