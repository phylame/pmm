package pmm.pbm.data.base.support;

import java.util.List;

import lombok.Data;

/**
 * Base abstraction for example.
 *
 * @author PW[<a href="mailto:phylame@163.com">phylame@163.com</a>]
 */
@Data
public class Example implements Sortable {
    /**
     * Page offset.
     */
    private int offset;

    /**
     * Page limit.
     */
    private int limit;

    /**
     * Group by clause.
     */
    protected String groupByClause;

    /**
     * Order by clause.
     */
    protected String orderByClause;

    /**
     * Distinct results.
     */
    protected boolean distinct;

    /**
     * Additional statement.
     */
    private String queryExpand;

    @Data
    public static class Criterion {

        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        public Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        public Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        public Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        public Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}
