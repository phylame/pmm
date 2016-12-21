package pmm.pbm.data.base.support;

import java.util.Properties;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.springframework.util.StringUtils;

import lombok.val;

@Intercepts({
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class,
                RowBounds.class, ResultHandler.class}),
        @Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class})
})
public class SQLPerformanceInterceptor extends MyBatisInterceptor {
    public static final String WARN_TIME_KEY = "warnTime";

    private long warnTime = 2 * 1000L;

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        val mappedStatement = (MappedStatement) invocation.getArgs()[0];
        Object parameterObject = null;
        if (invocation.getArgs().length > 1) {
            parameterObject = invocation.getArgs()[1];
        }

        String statementId = mappedStatement.getId();
        String sql = mappedStatement.getBoundSql(parameterObject).getSql();

        long begin = System.currentTimeMillis();
        Object result = invocation.proceed();
        long costing = System.currentTimeMillis() - begin;

        if (costing > warnTime) {
            logger.error(
                    "\nSQL Performance Warning: {}ms\nID:  {}\nSQL: \nOptimize the SQL to keep costing < {}ms\n--------------------------------------",
                    costing, statementId, sql, warnTime);
        }
        return result;
    }

    @Override
    public void setProperties(Properties p) {
        String value = p.getProperty(WARN_TIME_KEY);
        if (!StringUtils.isEmpty(value)) {
            try {
                warnTime = Long.parseLong(value);
            } catch (NumberFormatException e) {
                logger.error("invalid value for {}", WARN_TIME_KEY);
            }
        }
    }

}
