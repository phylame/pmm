package pmm.pbm.data.mbg;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.internal.DefaultCommentGenerator;

public class MyCommentGenerator extends DefaultCommentGenerator {
    @Override
    public void addFieldComment(Field field, IntrospectedTable table, IntrospectedColumn column) {
        field.addJavaDocLine("/**");
        field.addJavaDocLine(" * " + column.getRemarks());
        addJavadocTag(field, false);
        field.addJavaDocLine(" */");
    }
}
