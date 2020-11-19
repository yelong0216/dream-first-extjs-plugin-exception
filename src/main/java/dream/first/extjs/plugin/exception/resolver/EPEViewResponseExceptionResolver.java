package dream.first.extjs.plugin.exception.resolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;
import org.yelong.support.servlet.resource.response.ResourceResponseProperties;
import org.yelong.support.servlet.resource.response.support.ResourceResponseSupporter;
import org.yelong.support.spring.mvc.exception.ViewResponseExceptionResolver;

import dream.first.extjs.exception.Request404Exception;
import dream.first.extjs.login.LoginException;
import dream.first.extjs.plugin.exception.ExtJSPluginException;
import dream.first.extjs.support.msg.JsonMsg;
import dream.first.plugin.support.rights.AccessDenialException;

/**
 * 默认实现
 */
public class EPEViewResponseExceptionResolver implements ViewResponseExceptionResolver {

	private static final Logger LOGGER = LoggerFactory.getLogger(EPEViewResponseExceptionResolver.class);

	private ResourceResponseSupporter resourceResponseSupporter;

	public EPEViewResponseExceptionResolver(ResourceResponseSupporter resourceResponseSupporter) {
		this.resourceResponseSupporter = resourceResponseSupporter;
	}

	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response,
			HandlerMethod handlerMethod, Exception ex) {
		LOGGER.error("VIEW异常处理", ex);
		String resourceRelativePath = "";
		if (ex instanceof LoginException) {
			resourceRelativePath = "/html/loginSkip.html";
		} else if (ex instanceof AccessDenialException) {
			resourceRelativePath = "/html/accessDenialError.html";
		} else if (ex instanceof Request404Exception) {
			resourceRelativePath = "/html/404.html";
		} else {
			resourceRelativePath = "/html/error.html";
		}
		try {
			resourceResponseSupporter.responseHtml(new ResourceResponseProperties(request, response),
					ExtJSPluginException.RESOURCE_PRIVATES_PACKAGE,
					ExtJSPluginException.RESOURCE_PREFIX + resourceRelativePath);
			return new ModelAndView();
		} catch (Exception e) {
			LOGGER.error("响应异常页面异常", e);
			ModelAndView mav = new ModelAndView(new MappingJackson2JsonView());
			return mav.addAllObjects(new JsonMsg(false, e.getMessage()));
		}
	}

}
