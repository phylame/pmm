package pmm.pbm.service.support;

import pmm.pbm.data.base.support.Entity;

public interface CommonService {
    default boolean isExisted(Entity entity) {
        return entity != null && !entity.getDeleted();
    }
}
