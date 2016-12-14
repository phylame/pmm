package pmm.pbm.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;

import lombok.val;

public abstract class BaseController {
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    protected HttpServletRequest request;

    @Autowired
    protected HttpServletResponse response;

    @ModelAttribute("url")
    public CharSequence getRequestURL() {
        val url = request.getRequestURL();
        if (StringUtils.isNoneEmpty(request.getQueryString())) {
            val query = request.getQueryString().replaceAll("(&*offset=[\\d]*)|(&*limit=[\\d]*)", "");
            if (StringUtils.isNoneEmpty(query)) {
                url.append('?').append(query);
            }
        }
        return url;
    }
}
