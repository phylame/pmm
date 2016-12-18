package pmm.pbm.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.val;
import pmm.pbm.service.cms.CMSService;
import pmm.pbm.service.cms.CMSService.ItemWorker;

@Controller
@RequestMapping("/cms")
public class CMSController extends BaseController {
    private static final String[] itemNames = {"book", "author", "genre", "tag", "file", "log"};

    @Autowired
    private CMSService cmsService;

    @ModelAttribute("names")
    public String[] getItemNames() {
        return itemNames;
    }

    @RequestMapping({"/index", ""})
    public String index() {
        return "cms/index";
    }

    @RequestMapping("/about")
    public String about() {
        return "cms/about";
    }

    @RequestMapping({"/{item}", "/{item}/"})
    public String getItems(@PathVariable("item") String item, ModelMap mm) {
        val worker = cmsService.getWorker(item);
        if (worker == null) {
            return "/error/404";
        }
        val results = worker.getPaged(request);
        if (results == null) {
            return "/error/404";
        }
        mm.put("dto", results.getFirst());
        mm.put("items", results.getSecond());
        mm.put("queryUnits", worker.getQueryUnits());
        fillModel(mm, worker);
        return "cms/items";
    }

    @RequestMapping("/{item}/{id}")
    public String getItem(@PathVariable("item") String item, @PathVariable("id") String id, ModelMap mm) {
        val worker = cmsService.getWorker(item);
        if (worker == null) {
            return "/error/404";
        }
        val vo = worker.getById(id);
        if (vo == null) {
            return "/error/404";
        }
        mm.put("item", vo);
        fillModel(mm, worker);
        return "cms/item";
    }

    private void fillModel(ModelMap mm, ItemWorker worker) {
        mm.put("tag", worker.getTag());
        mm.put("itemUnits", worker.getItemUnits());
    }

}
