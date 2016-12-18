package pmm.pbm.data.base.support;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import pmm.pbm.util.cms.HiddenField;

/**
 * Base abstraction for entity.
 *
 * @author PW[<a href="mailto:phylame@163.com">phylame@163.com</a>]
 */
@Data
@SuppressWarnings("serial")
public abstract class Entity implements Serializable {
    /**
     * ID
     */
    private Integer id;

    /**
     * Deleted state
     */
    @JsonIgnore
    @HiddenField
    private Boolean deleted;
}
