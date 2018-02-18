package com.carmix.security;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;

@Configuration
public class TokenFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		HttpServletRequest httpRequest = (HttpServletRequest) request;

		System.out.println(httpRequest.getServletPath());
		if (!(httpRequest.getServletPath().equals("/user/") && httpRequest.getMethod().equals(HttpMethod.POST.name())) && !httpRequest.getServletPath().equals("/user/logIn") && !httpRequest.getServletPath().equals("/spotify/categorias")) {

			String authorizationHeader = httpRequest.getHeader("Authorization");
			if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
				System.out.println("Token no existe");
				httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED);
			} else {
				String authToken = authorizationHeader.substring(7);
				// Aqui se haria una comprobacioon del token con algun servicio
				// externo (o interno)
				if (authToken != "") {
					System.out.println("Todo en orden");
				}
			}
		}
		else{
			System.out.println("No hace falta filtro");
		}
		
		chain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig config) throws ServletException {
	}

	@Override
	public void destroy() {

	}
}
