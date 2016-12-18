package pmm.pbm.data.base.entity;

import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import pmm.pbm.data.base.support.Entity;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Storage extends Entity implements Serializable {
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

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table pmm_storage
     *
     * @mbg.generated
     */
    private static final long serialVersionUID = 1L;
}