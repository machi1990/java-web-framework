package com.upmc.stl.dar.server.resource.configuration;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Asset {
	private static Asset welcomeFile = null;
	private String content;
	private String absPath;
	private String type;
	
	private Asset() {
		super();
	}

	protected Asset(String absPath) throws IOException {
		super();
		setAbsPath(absPath);
	}
	
	public String getAbsPath() {
		return absPath;
	}
	
	protected void setAbsPath(String absPath) throws IOException {
		this.absPath = absPath;
		readFile();
	}
	
	private void readFile() throws IOException {
		Path path = Paths.get(absPath);
		type = Files.probeContentType(path);
		content = new String(Files.readAllBytes(path),Charset.defaultCharset());
	}
	
	public void makeWelcomeFile() {
		Asset.welcomeFile = clone();
	}
	
	public Asset clone() {
		Asset clone = new Asset();
		clone.type = type;
		clone.content = content;
		clone.absPath = absPath;
		
		return clone;
	}
	
	public String contentType() {
		return type;
	}
	
	@Override
	public String toString() {
		return content + "\r\n";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((absPath == null) ? 0 : absPath.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object object) {
		if (this == object)
			return true;
		if (object == null)
			return false;
		if (getClass() != object.getClass())
			return false;
		
		Asset asset = (Asset) object;
		if (absPath == null) {
			if (asset.absPath != null)
				return false;
		} else if (!absPath.equals(asset.absPath))
			return false;
		
		return true;
	}
	
	public static boolean hasWelcomeFile() {
		return welcomeFile != null;
	}
	
	public static void makeWelcomeFile(Asset asset) {
		welcomeFile = asset.clone();
	}
	
	public static Asset getWelcomeFile () {
		return welcomeFile;
	}
}
