package pmm.pbm.data.mbg;

import static org.mybatis.generator.internal.util.StringUtility.stringHasValue;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.CompilationUnit;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.InnerClass;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.JavaElement;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.Element;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;

public class MyGeneratorPlugin extends PluginAdapter {
    public static final String ROOT_ENTITY_KEY = "entityRoot";
    public static final String ROOT_EXAMPLE_KEY = "exampleRoot";
    public static final String ROOT_IFACE_KEY = "ifaceRoot";

    @Override
    public boolean validate(List<String> warnings) {
        return true;
    }

    @Override
    public boolean clientGenerated(Interface iface, TopLevelClass clazz, IntrospectedTable table) {
        String rootIface = context.getJavaClientGeneratorConfiguration().getProperty(ROOT_IFACE_KEY);
        if (stringHasValue(rootIface)) {
            addInterface(iface, String.format("%s<%s, %s, %s>",
                    rootIface,
                    table.getBaseRecordType(),
                    table.getExampleType(),
                    getPrimaryKeyType(table)));
            if (!iface.getMethods().stream().anyMatch(i -> i.getName().contains("BLOBs"))) {
                removeImportedTypes(iface, Arrays.asList("java.util.List", "org.apache.ibatis.annotations.Param"));
            }
            removeMethods(iface.getMethods(), rootIface);
        }
        return true;
    }

    @Override
    public boolean modelBaseRecordClassGenerated(TopLevelClass clazz, IntrospectedTable table) {
        String rootEntity = context.getJavaModelGeneratorConfiguration().getProperty(ROOT_ENTITY_KEY);
        if (stringHasValue(rootEntity)) {
            addLombokData(clazz);
            setSuperClass(clazz, rootEntity);
            removeFields(clazz.getFields(), rootEntity);
            clazz.getMethods().clear();
        }
        return super.modelBaseRecordClassGenerated(clazz, table);
    }

    @Override
    public boolean modelExampleClassGenerated(TopLevelClass clazz, IntrospectedTable table) {
        String rootExample = context.getJavaModelGeneratorConfiguration().getProperty(ROOT_EXAMPLE_KEY);
        if (stringHasValue(rootExample)) {
            addLombokData(clazz);
            setSuperClass(clazz, rootExample);
            List<Field> fields = clazz.getFields();
            removeFields(fields, rootExample);
            removeMethods(clazz.getMethods(), rootExample);
            List<InnerClass> classes = clazz.getInnerClasses();
            classes.removeIf(i -> i.getType().getShortName().equals("Criterion"));
        }

        CommentGenerator commentGenerator = context.getCommentGenerator();
        Method method = new Method("getCriteria");
        method.setVisibility(JavaVisibility.PUBLIC);
        method.setReturnType(FullyQualifiedJavaType.getCriteriaInstance());
        method.addBodyLine("if (oredCriteria.size() == 0) {createCriteria();}");
        method.addBodyLine("return oredCriteria.get(0);");
        commentGenerator.addGeneralMethodComment(method, table);
        clazz.addMethod(method);

        return true;
    }

    @Override
    public boolean clientSelectByExampleWithoutBLOBsMethodGenerated(Method method, Interface iface,
            IntrospectedTable table) {
        return false;
    }

    @Override
    public boolean clientUpdateByExampleSelectiveMethodGenerated(Method method, Interface iface,
            IntrospectedTable table) {
        return false;
    }

    @Override
    public boolean clientUpdateByExampleWithoutBLOBsMethodGenerated(Method method, Interface iface,
            IntrospectedTable table) {
        return false;
    }

    @Override
    public boolean sqlMapInsertSelectiveElementGenerated(XmlElement element, IntrospectedTable table) {
        return false;
    }

    @Override
    public boolean sqlMapUpdateByExampleWithoutBLOBsElementGenerated(XmlElement element, IntrospectedTable table) {
        return false;
    }

    @Override
    public boolean sqlMapUpdateByPrimaryKeyWithoutBLOBsElementGenerated(XmlElement element, IntrospectedTable table) {
        return false;
    }

    @Override
    public boolean sqlMapSelectByExampleWithoutBLOBsElementGenerated(XmlElement element, IntrospectedTable table) {
        int last = element.getElements().size() - 1;
        Element orderBy = element.getElements().get(last);
        element.getElements().remove(last);
        addQueryExpand(element);
        addGroupBy(element);
        element.addElement(orderBy);
        addLimit(element);
        return true;
    }

    private FullyQualifiedJavaType getPrimaryKeyType(IntrospectedTable table) {
        return table.getPrimaryKeyColumns().get(0).getFullyQualifiedJavaType();
    }

    private void addQueryExpand(XmlElement element) {
        XmlElement ifElement = new XmlElement("if");
        ifElement.addAttribute(new Attribute("test", "queryExpand != null and queryExpand != ''"));
        ifElement.addElement(new TextElement("${queryExpand}"));
        element.addElement(ifElement);
    }

    private void addLimit(XmlElement element) {
        XmlElement ifElement = new XmlElement("if");
        ifElement.addAttribute(new Attribute("test", "limit gte 1 and offset gte 0"));
        ifElement.addElement(new TextElement("limit #{limit} offset #{offset}"));
        element.addElement(ifElement);
    }

    private void addGroupBy(XmlElement element) {
        XmlElement ifElement = new XmlElement("if");
        ifElement.addAttribute(new Attribute("test", "groupByClause != null and groupByClause != ''"));
        ifElement.addElement(new TextElement("group by ${groupByClause}"));
        element.addElement(ifElement);
    }

    private FullyQualifiedJavaType addInterface(Interface iface, String name) {
        FullyQualifiedJavaType type = new FullyQualifiedJavaType(getShortName(name));
        iface.addSuperInterface(type);
        addImportedType(iface, new FullyQualifiedJavaType(name));
        return type;
    }

    private FullyQualifiedJavaType setSuperClass(TopLevelClass clazz, String name) {
        FullyQualifiedJavaType type = new FullyQualifiedJavaType(name);
        clazz.setSuperClass(type);
        addImportedType(clazz, type);
        return type;
    }

    private void addImportedType(CompilationUnit unit, FullyQualifiedJavaType type) {
        if (!unit.getType().getPackageName().equals(type.getPackageName())) { // not in same package
            unit.addImportedType(type);
        }
    }

    private <T extends JavaElement & CompilationUnit> void addLombokData(T clazz) {
        clazz.addAnnotation("@Data");
        addImportedType(clazz, new FullyQualifiedJavaType("lombok.Data"));
        clazz.addAnnotation("@ToString(callSuper = true)");
        addImportedType(clazz, new FullyQualifiedJavaType("lombok.ToString"));
        clazz.addAnnotation("@EqualsAndHashCode(callSuper = true)");
        addImportedType(clazz, new FullyQualifiedJavaType("lombok.EqualsAndHashCode"));
    }

    private String getShortName(String name) {
        int index = name.indexOf('<');
        if (index < 0) {
            index = name.lastIndexOf('.');
        } else {
            index = name.lastIndexOf('.', index);
        }
        return index < 0 ? name : name.substring(index + 1);
    }

    @SuppressWarnings("unchecked")
    private void removeImportedTypes(Object o, Collection<String> names) {
        try {
            java.lang.reflect.Field field = o.getClass().getDeclaredField("importedTypes");
            field.setAccessible(true);
            Set<FullyQualifiedJavaType> types = (Set<FullyQualifiedJavaType>) field.get(o);
            types.removeIf(i -> names.contains(i.getFullyQualifiedNameWithoutTypeParameters()));
        } catch (Exception ignored) {
        }
    }

    private void removeFields(List<Field> fields, String path) {
        try {
            Collection<String> names = Arrays.stream(Class.forName(path).getDeclaredFields())
                    .map(i -> i.getName())
                    .collect(Collectors.toSet());
            fields.removeIf(i -> names.contains(i.getName()));
        } catch (Exception ignored) {
        }
    }

    private void removeMethods(List<Method> methods, String path) {
        try {
            Collection<String> names = Arrays.stream(Class.forName(path).getMethods())
                    .map(i -> i.getName())
                    .collect(Collectors.toSet());
            methods.removeIf(i -> names.contains(i.getName()));
        } catch (Exception ignored) {
        }
    }
}
