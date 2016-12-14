package pmm.pbm.service.params;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class AddTagDTO extends OperateDTO {
    @Size(min = 2, max = 10)
    private String name;

    @Min(0)
    private Integer rank;

    @NotNull
    private String intro;
}
