package br.pucminas.stockmarket.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	  @Bean
	    public Docket api() {
			return new Docket(DocumentationType.SWAGGER_2).useDefaultResponseMessages(false).select()
					.apis(RequestHandlerSelectors.basePackage("br.pucminas.stockmarket.api.controllers"))
					.paths(PathSelectors.any()).build()
					.apiInfo(apiInfo());
		}

		private ApiInfo apiInfo() {
			return new ApiInfoBuilder().title("Mercado de Ações API")
					.description("Documentação da API de acesso aos recursos do Mercador de Ações.").version("1.0")
					.build();
		}
}
