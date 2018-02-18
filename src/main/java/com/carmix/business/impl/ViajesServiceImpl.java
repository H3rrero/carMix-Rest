package com.carmix.business.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
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

	// GLOBAL
	public static final int TOP_PAGE = 842;
	public static final int RIGTH_PAGE = 595;
	public static final PDFont FONT = PDType1Font.COURIER_BOLD;
	public static final int GENERAL_FONT_SIZE = 10;
	
	private void getToken(SpotifyApi spotifyApi) {
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
		String url = "http://datos.gob.es/apidata/nti/territory/Province?_pageSize=50";
		ResponseProvinciasGobierno response = restTemplate.getForObject(url, ResponseProvinciasGobierno.class);
		for (ResponseItem item : response.getResult().getItems()) {
			provincias.add(item.getLabel());
		}
		return provincias;
	}

	@Override
	public ViajeDto actualizarViaje(ViajeDto dto) {
		Viaje v = vr.findOne(dto.getId());
		if (v == null) {
			return null;
		}
		v.setOrigen(dto.getOrigen());
		v.setDestino(dto.getDestino());
		v.setPlazas(dto.getPlazas());
		v.setDescripcion(dto.getDescripcion());
		v.setPrecio(dto.getPrecio());

		for (Usuario u : dto.getUsuarios()) {
			UserViajeId id = new UserViajeId(u, v);
			UserViaje uv = uvr.findOne(id);
			if (uv == null) {
				uv = new UserViaje();
				uv.setId(id);
				uv.setRole("INVITADO");
				v.setPlazas(v.getPlazas() - 1);
				uvr.save(uv);

				if (v.getPlazas() == 0) {
					Usuario uMusica = ur.findOne(u.getId());
					SpotifyApi spotifyApi = new SpotifyApi.Builder().setClientId(clientId).setClientSecret(clientSecret)
							.build();
					this.getToken(spotifyApi);
					GetCategorysPlaylistsRequest getCategoryRequest = spotifyApi
							.getCategorysPlaylists(uMusica.getGeneroMusical()).country(CountryCode.ES).limit(10)
							.offset(0).build();
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

	@Override
	@Transactional
	public Viaje eliminarViaje(Long id) {
		Viaje v = vr.findOne(id);

		if (v != null) {
			List<UserViaje> userViajes = uvr.getViaje(id);
			for (UserViaje userViaje : userViajes) {
				uvr.delete(userViaje);
			}
			vr.delete(id);
			return v;
		} else {
			return null;
		}

	}

	@Override
	public byte[] getViajePDF(Long id) {

		ViajeDto dto = getViaje(id);
		
		if(dto == null){
			return null;
		}
		
		PDPage singlePage = null;
		try {
			ByteArrayOutputStream output = new ByteArrayOutputStream();
			PDDocument document = new PDDocument();
			// document.addPage(singlePage);
			PDPageContentStream contentStream = null;

			singlePage = new PDPage(PDRectangle.A4);
			document.addPage(singlePage);
			contentStream = new PDPageContentStream(document, singlePage);
			
			
			printTitulo("CarMix - Viaje " + dto.getId(), 100, TOP_PAGE - 100, contentStream);
			
			printSubTitulo("Orige-Destino:", 100, TOP_PAGE-130, contentStream);
			printText(dto.getOrigen() + " - " + dto.getDestino(), 110, TOP_PAGE-150, contentStream);
			
			printSubTitulo("Precio:", 100, TOP_PAGE-170, contentStream);
			printText(dto.getPrecio() + "", 110, TOP_PAGE-190, contentStream);
			
			printSubTitulo("Plazas:", 100, TOP_PAGE-210, contentStream);
			printText(dto.getPlazas() + "", 110, TOP_PAGE-230, contentStream);
			
			printSubTitulo("Descripcion:", 100, TOP_PAGE-250, contentStream);
			printText(dto.getDescripcion() + "", 110, TOP_PAGE-270, contentStream);
			
			printSubTitulo("Usuarios:", 100, TOP_PAGE-290, contentStream);
			int y = TOP_PAGE-310;
			for (Usuario u : dto.getUsuarios()) {
				printText(u.getUser() + "(" + u.getGeneroMusical() + ")", 110, y, contentStream);
				y -=20;
			}
			
			if(dto.getLista() != null){
				printSubTitulo("Lista de Spotify:", 100, y, contentStream);
				y-=20;
				printText(dto.getLista() + "", 110, y, contentStream);
			}
			
			contentStream.close();
			document.save(output);

			return output.toByteArray();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Print a text in the contentStream
	 * 
	 * @param text
	 * @param x
	 *            position
	 * @param y
	 *            position
	 * @param fontSize
	 * @param contentStream
	 */
	private void printText(String text, int x, int y, PDPageContentStream contentStream) {
		try {
			contentStream.beginText();
			contentStream.setFont(FONT, GENERAL_FONT_SIZE);
			contentStream.newLineAtOffset(x, y);
			contentStream.showText(text);
			contentStream.endText();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Print a text in the contentStream
	 * 
	 * @param text
	 * @param x
	 *            position
	 * @param y
	 *            position
	 * @param fontSize
	 * @param contentStream
	 */
	private void printTitulo(String text, int x, int y, PDPageContentStream contentStream) {
		try {
			contentStream.beginText();
			contentStream.setFont(FONT, 20);
			contentStream.newLineAtOffset(x, y);
			contentStream.showText(text);
			contentStream.endText();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Print a text in the contentStream
	 * 
	 * @param text
	 * @param x
	 *            position
	 * @param y
	 *            position
	 * @param fontSize
	 * @param contentStream
	 */
	private void printSubTitulo(String text, int x, int y, PDPageContentStream contentStream) {
		try {
			contentStream.beginText();
			contentStream.setFont(FONT, 16);
			contentStream.newLineAtOffset(x, y);
			contentStream.showText(text);
			contentStream.endText();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Get the width of a text based on the general font size
	 * 
	 * @param text
	 * @return Width
	 * @throws IOException
	 */
	private int width(String text) throws IOException {
		return (int) ((FONT.getStringWidth(text) / 1000.0f) * GENERAL_FONT_SIZE);
	}
}
