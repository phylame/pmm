package pmm.pbm.web.controller;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.val;
import pmm.pbm.data.base.entity.Tag;
import pmm.pbm.service.TagService;
import pmm.pbm.service.params.AddTagDTO;
import pmm.pbm.service.params.ListTagDTO;

@Controller
@RequestMapping("/cms/tags")
public class TagController extends BaseController {
    @Autowired
    private TagService tagService;

    @RequestMapping({"", "/"})
    public String getTags(@ModelAttribute("dto") ListTagDTO dto, ModelMap mm) {
        mm.put("tags", tagService.getTags(dto));
        return "tags";
    }

    @RequestMapping("/{id}")
    public String getTag(@PathVariable("id") String id, ModelMap mm) {
        val tag = tagService.getTagById(id);
        if (tag != null) {
            mm.put("tag", tag);
            return "tag";
        } else {
            return "tags";
        }
    }

    @RequestMapping("/{id}/edit")
    public String setState(@PathVariable("id") String id, Tag tag, ModelMap mm) {
        val po = tagService.getTagById(id);
        if (po != null) {
            BeanUtils.copyProperties(tag, po);
            tagService.updateByIdSelective(po);
        }
        return "tags";
    }

    @RequestMapping("/add")
    public String addTag(@ModelAttribute("tag") @Valid AddTagDTO dto, ModelMap mm) {
        return "tag";
    }
}
