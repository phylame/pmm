package pmm.pbm.service.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.val;
import pmm.pbm.data.base.entity.Endpoint;
import pmm.pbm.data.base.entity.EndpointExample;
import pmm.pbm.data.base.iface.EndpointMapper;
import pmm.pbm.data.base.support.Mapper;
import pmm.pbm.service.support.CrudService;

@Service
public class EndpointService implements CrudService<Endpoint, EndpointExample, Integer> {
    @Autowired
    private EndpointMapper endpointMapper;

    @Override
    public Mapper<Endpoint, EndpointExample, Integer> getMapper() {
        return endpointMapper;
    }

    public Endpoint getByName(String name) {
        val example = new EndpointExample();
        example.createCriteria().andNameEqualTo(name).andDeletedEqualTo(false);
        return selectOneByExample(example);
    }
}
