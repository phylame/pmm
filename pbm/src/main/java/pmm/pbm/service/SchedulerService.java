package pmm.pbm.service;

import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SchedulerService {
    @Autowired
    private Scheduler scheduler;
}
