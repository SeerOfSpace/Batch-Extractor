package core;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class UnzipLogicJava extends UnzipLogic {
	
	public static void unzipFolderJava(File dir, boolean mossOnly) throws IOException {
		File parent = new File(dir.getAbsoluteFile().getParent() + File.separator + dir.getName() + " Unzipped");
		unzipFolderJava(dir, parent, mossOnly);
	}
	
	public static void unzipFolderJava(File dir, File parent, boolean mossOnly) throws IOException {
		parent.mkdirs();
		for(File file : dir.listFiles()) {
			if(file.isDirectory()) {
				unzipFolderJava(file, parent, mossOnly);
			} else if((mossOnly && file.getName().toLowerCase().endsWith("moss.zip")) || (!mossOnly && getExtension(file.getName()).equals("zip"))) {
				File zipParent = new File(parent.getAbsolutePath() + File.separator + removeExtension(file.getName()));
				unzipJava(file, zipParent);
			}
		}
	}
	
	public static void unzipJava(File source) throws IOException {
		String name = removeExtension(source.getName());
		File parent = new File(source.getParent() + File.separator + name);
		unzipJava(source, parent);
	}
	
	public static void unzipJava(File source, File parent) throws IOException {
		ZipFile zipFile;
		try {
			zipFile = new ZipFile(source);
		} catch(java.util.zip.ZipException e) {
			return;
		}
		parent.mkdirs();
		Enumeration<? extends ZipEntry> zipEnum = zipFile.entries();
		while(zipEnum.hasMoreElements()) {
			ZipEntry entry = zipEnum.nextElement();
			if(!entry.isDirectory() && getExtension(entry.getName()).equals("java")) {
				File dest = new File(parent, getName(entry.getName()));
				if(dest.exists()) {
					System.out.println("File already exists, skipping: " + dest.getAbsolutePath());
				}
				BufferedInputStream inputStream = new BufferedInputStream(zipFile.getInputStream(entry));
				BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(dest));
				byte[] buffer = new byte[1024];
				int len;
				while((len = inputStream.read(buffer)) != -1) {
					outputStream.write(buffer, 0, len);
				}
				inputStream.close();
				outputStream.close();
			}
		}
		zipFile.close();
	}
	
}
