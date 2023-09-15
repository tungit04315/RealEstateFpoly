package com.poly.util;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;

@Service
public class FileManagerService {
	
	@Autowired
	ServletContext app;
	
	private Path getPath(String folder, String filename) {
		File dir = Paths.get(app.getRealPath("/files/"), folder).toFile();
		if(!dir.exists()) {
			dir.mkdirs();
		}
		return Paths.get(dir.getAbsolutePath(), filename);
	}
	
	public byte[] read(String folder,String name) {
		Path path = this.getPath(folder, name);
		try {
			
			return Files.readAllBytes(path);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public List<String> save(String folder, MultipartFile[] files) {
		List<String> filenames = new ArrayList<>();
		for(MultipartFile file : files) {
			String name = System.currentTimeMillis() + file.getOriginalFilename();
			String fileName = Integer.toHexString(name.hashCode()) + name.substring(name.lastIndexOf("."));
			Path path = this.getPath(folder, fileName);
			try {
				file.transferTo(path);
				filenames.add(fileName);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return filenames;
	}
	
	public List<String> list(String folder){
		List<String> filenames = new ArrayList<>();
		File dir = Paths.get(app.getRealPath("/files"), folder).toFile();
		if(dir.exists()) {
			File[] files = dir.listFiles();
			for(File f : files) {
				filenames.add(f.getName());
			}
		}
		return filenames;
	}

	public void delete(String folder, String filename) {
		Path path = this.getPath(folder, filename);
		path.toFile().delete();
	}
	
	
}
