package com.carmix.business.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.carmix.business.api.ViajesService;
import com.carmix.business.dto.ResponseItem;
import com.carmix.business.dto.ResponseProvinciasGobierno;
import com.carmix.business.dto.ViajeDto;
import com.carmix.dao.api.UserRepository;
import com.carmix.dao.api.UserViajeRepository;
import com.carmix.dao.api.ViajesRepository;
import com.carmix.model.UserViaje;
import com.carmix.model.UserViajeId;
import com.carmix.model.Usuario;
import com.carmix.model.Viaje;
import com.neovisionaries.i18n.CountryCode;
import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.credentials.ClientCredentials;
import com.wrapper.spotify.model_objects.specification.Paging;
import com.wrapper.spotify.model_objects.specification.PlaylistSimplified;
import com.wrapper.spotify.requests.authorization.client_credentials.ClientCredentialsRequest;
import com.wrapper.spotify.requests.data.browse.GetCategorysPlaylistsRequest;

@Service
public class ViajesServiceImpl implements ViajesService {

	@Autowired
	private ViajesRepository vr;
	
	@Autowired
	private UserRepository ur;
	
	@Autowired
	private UserViajeRepository uvr;
	
	private static final String clientId = "1f43594e3d7749c9acd70936f9bbd43a";
	private static final String clientSecret = "457643f55ab142a0950a7b8a1fdee818";
	
	

	private void getToken(SpotifyApi spotifyApi){
		ClientCredentialsRequest clientCredentialsRequest = spotifyApi.clientCredentials().build();
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
	@Override
	public List<ViajeDto> getViajes() {
		List<Viaje> viajes = (List<Viaje>) vr.findAll();

		List<ViajeDto> dtos = new ArrayList<>();
		for (Viaje v : viajes) {
			Usuario u = vr.getCreadorViaje(v.getId());
			ViajeDto dto = new ViajeDto();
			dto.setId(v.getId());
			dto.setOrigen(v.getOrigen());
			dto.setDestino(v.getDestino());
			dto.setDescripcion(v.getDescripcion());
			dto.setPlazas(v.getPlazas());
			dto.setPrecio(v.getPrecio());
			dto.setLista(v.getLista());
			dto.setCreador(u.getId());
			dto.setUrl("/viajes/" + v.getId());
			dtos.add(dto);
		}
		return dtos;
	}

	@Override
	public ViajeDto getViaje(Long id) {
		Viaje v = vr.findOne(id);

		if (v != null) {
			ViajeDto dto = new ViajeDto();
			
			Usuario u = vr.getCreadorViaje(id);
			dto.setCreador(u.getId());
			
			dto.setUsuarios(vr.getUsuariosViaje(id));
			dto.setId(v.getId());
			dto.setOrigen(v.getOrigen());
			dto.setDestino(v.getDestino());
			dto.setDescripcion(v.getDescripcion());
			dto.setPlazas(v.getPlazas());
			dto.setPrecio(v.getPrecio());
			dto.setLista(v.getLista());
			dto.setUrl("/viajes/" + v.getId());
			
			return dto;
		}
		return null;

	}

	@Override
	@Transactional
	public ViajeDto crearViaje(ViajeDto dto) {
		Viaje v = new Viaje();
		v.setOrigen(dto.getOrigen());
		v.setDestino(dto.getDestino());
		v.setPlazas(dto.getPlazas());
		v.setDescripcion(dto.getDescripcion());
		v.setPrecio(dto.getPrecio());
		
		v = vr.save(v);
		
		UserViajeId id = new UserViajeId();
		Usuario u = new Usuario();
		u.setId(dto.getCreador());
		id.setUsuario(u);
		id.setViaje(v);
		
		UserViaje uv = new UserViaje();
		uv.setId(id);
		uv.setRole("CREADOR");
		uvr.save(uv);
		dto.setUrl("viajes/" + v.getId());
		dto.setId(v.getId());
		return dto;
	}

	@Override
	public List<String> getDestinos() {
		List<String> provincias = new ArrayList<>();
		RestTemplate restTemplate = new RestTemplate();
		String url
		  = "http://datos.gob.es/apidata/nti/territory/Province?_pageSize=50";
		ResponseProvinciasGobierno response = restTemplate.getForObject(url, ResponseProvinciasGobierno.class);
		for (ResponseItem item: response.getResult().getItems()) {
			provincias.add(item.getLabel());
		}
		return provincias;
	}

	@Override
	public ViajeDto actualizarViaje(ViajeDto dto) {
		Viaje v = vr.findOne(dto.getId());
		
		v.setOrigen(dto.getOrigen());
		v.setDestino(dto.getDestino());
		v.setPlazas(dto.getPlazas());
		v.setDescripcion(dto.getDescripcion());
		v.setPrecio(dto.getPrecio());
		
		for (Usuario u : dto.getUsuarios()) {
			UserViajeId id = new UserViajeId(u, v);
			UserViaje uv = uvr.findOne(id);
			if(uv == null){
				uv = new UserViaje();
				uv.setId(id);
				uv.setRole("INVITADO");
				v.setPlazas(v.getPlazas()-1);
				uvr.save(uv);
				
				if(v.getPlazas() == 0){
				Usuario uMusica = ur.findOne(u.getId());
				SpotifyApi spotifyApi = new SpotifyApi.Builder().setClientId(clientId)
						.setClientSecret(clientSecret).build();
					this.getToken(spotifyApi);
					GetCategorysPlaylistsRequest getCategoryRequest = spotifyApi.getCategorysPlaylists(uMusica.getGeneroMusical())
					          .country(CountryCode.ES)
					          .limit(10)
					          .offset(0)
					          .build();
					try {
						Paging<PlaylistSimplified> playlistSimplifiedPaging = getCategoryRequest.execute();
						v.setLista(playlistSimplifiedPaging.getItems()[0].getName());
						dto.setLista(v.getLista());
					} catch (SpotifyWebApiException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					getCategoryRequest = null;
				}
			}
		}
		
		v = vr.save(v);
		
		return dto;
	}

}
