package com.GG.springboot.ecommerce.service;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;



@Service
public class UploadFileServiceImpl implements IUploadFileService {

	private final Logger log = LoggerFactory.getLogger(getClass());

	private final static String UPLOADS_FOLDER = "uploads";

	@Override
	public Resource load(String filename) throws MalformedURLException {
		Path pathFoto = getPath(filename);
		log.info("pathFoto: " + pathFoto);

		Resource recurso = new UrlResource(pathFoto.toUri());

		if (!recurso.exists() || !recurso.isReadable()) {
			throw new RuntimeException("Error: no se puede cargar la imagen: " + pathFoto.toString());
		}
		return recurso;
	}

	@Override
	public String copy(MultipartFile file) throws IOException {
		String uniqueFilename = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
		Path rootPath = getPath(uniqueFilename);

		log.info("rootPath: " + rootPath);

		Files.copy(file.getInputStream(), rootPath);

		return uniqueFilename;
	}

	@Override
	public boolean delete(String filename) {
		Path rootPath = getPath(filename);
		File archivo = rootPath.toFile();

		if (archivo.exists() && archivo.canRead()) {
			if (archivo.delete()) {
				return true;
			}
		}
		return false;
	}

	public Path getPath(String filename) {
		return Paths.get(UPLOADS_FOLDER).resolve(filename).toAbsolutePath();
	}
	//Elimino la recursividad en el borrado y creación de carpetas de imagenes para garantizar la persistencia
	// En este método elimino de forma recursiva el directorio uploads
	/*@Override
	public void deleteAll() {
		FileSystemUtils.deleteRecursively(Paths.get(UPLOADS_FOLDER).toFile());

	}
	// Aca creo el directorio de forma recursiva cada vez que inicializo el proyecto y utilizo Paths.get..... para indicarle que directorio se debe
	//crear
	@Override
	public void init() throws IOException {
		// TODO Auto-generated method stub
		Files.createDirectory(Paths.get(UPLOADS_FOLDER));
	}*/

	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void init() throws IOException {
		// TODO Auto-generated method stub
		
	}
}