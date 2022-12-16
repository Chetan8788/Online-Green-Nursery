package com.masai.config;

import org.springframework.context.annotation.Configuration;

@Configuration
//@EnableWebMvc
public class SwaggerConfig {

//	@Bean
//	public Docket docket() {
//		return new Docket(DocumentationType.SWAGGER_2).globalRequestParameters(customRequestHeader()).select()
//				.apis(RequestHandlerSelectors.any()).paths(PathSelectors.any()).build();
//	}

//	private List<RequestParameter> customRequestHeader() {
//		RequestParameter requestParameter = new RequestParameterBuilder().in(ParameterType.HEADER).name("token")
//				.required(true).query(param -> param.model(model -> model.scalarModel(ScalarType.STRING))).build();
//		return List.of(requestParameter);
//	}

}
