package com.carmix.rest.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.carmix.business.dto.Categoria;
import com.carmix.rest.api.SpotifyRestService;
import com.neovisionaries.i18n.CountryCode;
import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.credentials.ClientCredentials;
import com.wrapper.spotify.model_objects.specification.Category;
import com.wrapper.spotify.model_objects.specification.Paging;
import com.wrapper.spotify.requests.authorization.client_credentials.ClientCredentialsRequest;
import com.wrapper.spotify.requests.data.browse.GetListOfCategoriesRequest;

@Component
public class SpotifyRestServiceImpl implements SpotifyRestService {

	private static final String clientId = "1f43594e3d7749c9acd70936f9bbd43a";
	private static final String clientSecret = "457643f55ab142a0950a7b8a1fdee818";

	private static final SpotifyApi spotifyApi = new SpotifyApi.Builder().setClientId(clientId)
			.setClientSecret(clientSecret).build();

	private static final ClientCredentialsRequest clientCredentialsRequest = spotifyApi.clientCredentials().build();

	/**
	 * Esto deberia estar en la capa de logica
	 */
	@Override
	public ResponseEntity<List<Categoria>> getCategorias() {
		getToken();

		GetListOfCategoriesRequest getListOfCategoriesRequest = spotifyApi
				.getListOfCategories()
				.country(CountryCode.ES)
				.locale("es_ES").build();

		Paging<Category> categories = null;
		List<Categoria> categorias = new ArrayList<>();
		try {
			categories = getListOfCategoriesRequest.execute();
			for (Category cat : categories.getItems()) {
				Categoria c = new Categoria();
				c.setId(cat.getId());
				c.setName(cat.getName());
				
				categorias.add(c);
			}
		} catch (Exception e) {
		}

		return new ResponseEntity<List<Categoria>>(categorias,HttpStatus.OK);
	}

	private void getToken() {
		ClientCredentials clientCredentials;
		try {
			clientCredentials = clientCredentialsRequest.execute();

			// Set access token for further "spotifyApi" object usage
			spotifyApi.setAccessToken(clientCredentials.getAccessToken());

		} catch (SpotifyWebApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
