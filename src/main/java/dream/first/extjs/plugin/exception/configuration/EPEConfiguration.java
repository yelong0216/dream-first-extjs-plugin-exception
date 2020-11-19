package dream.first.extjs.plugin.exception.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.yelong.support.servlet.resource.response.support.ResourceResponseSupporter;
import org.yelong.support.spring.mvc.exception.ViewResponseExceptionResolver;

import dream.first.extjs.plugin.exception.resolver.EPEViewResponseExceptionResolver;

@Configuration
public class EPEConfiguration {

	@Bean
	@Order(Ordered.LOWEST_PRECEDENCE - 1)
	public ViewResponseExceptionResolver viewResponseExceptionResolver(
			ResourceResponseSupporter resourceResponseSupporter) {
		return new EPEViewResponseExceptionResolver(resourceResponseSupporter);
	}

}
