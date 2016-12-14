package pmm.pbm.data.mbg;

import org.mybatis.generator.api.IntrospectedColumn;

public class MyIntrospectedColumn extends IntrospectedColumn {

    @Override
    public boolean isBLOBColumn() {
        return false;
    }

}
