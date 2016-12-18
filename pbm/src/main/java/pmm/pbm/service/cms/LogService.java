package pmm.pbm.service.cms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.NonNull;
import lombok.val;
import pmm.pbm.data.base.entity.Log;
import pmm.pbm.data.base.entity.LogExample;
import pmm.pbm.data.base.iface.LogMapper;
import pmm.pbm.data.base.support.Mapper;
import pmm.pbm.data.base.support.Paged;
import pmm.pbm.service.params.ListLogDTO;
import pmm.pbm.service.support.CrudService;

@Service("cmsLogService")
public class LogService implements CrudService<Log, LogExample, Integer> {
    @Autowired
    private LogMapper logMapper;

    @Override
    public Mapper<Log, LogExample, Integer> getMapper() {
        return logMapper;
    }

    public Paged<Log> getLogPaged(@NonNull ListLogDTO dto) {
        val example = new LogExample();
        return selectPaged(() -> selectByExample(example), dto);
    }

    @Transactional
    public Log getLogById(@NonNull String id) {
        return selectById(Integer.valueOf(id));
    }
}
