package pmm.pbm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.NonNull;
import lombok.val;
import pmm.pbm.data.base.entity.CMSEndpoint;
import pmm.pbm.data.base.entity.CMSEndpointExample;
import pmm.pbm.data.base.iface.CMSEndpointMapper;
import pmm.pbm.data.base.support.Mapper;
import pmm.pbm.service.support.CrudService;

@Service
public class CMSService implements CrudService<CMSEndpoint, CMSEndpointExample, Integer> {
    @Autowired
    private CMSEndpointMapper mapper;

    @Override
    public Mapper<CMSEndpoint, CMSEndpointExample, Integer> getMapper() {
        return mapper;
    }

    public CMSEndpoint getByTag(@NonNull String tag) {
        val example = new CMSEndpointExample();
        example.getCriteria().andTagEqualTo(tag).andDeletedEqualTo(false);
        return selectOneByExample(example);
    }
}
