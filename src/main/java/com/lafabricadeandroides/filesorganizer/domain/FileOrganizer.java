package com.lafabricadeandroides.filesorganizer.domain;

import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class FileOrganizer {
	private static Path targetPath;
	private static int filesCounter=0;
	private static int foldersCounter=1;
	private static int filesCopied=0;
	public static void organize(String sourceFolder, String targetFolder, String folderPrefix, int nof) 
			throws IOException {
		filesCopied=0;
		Path sourcePath = Paths.get(sourceFolder);
		Stream<Path> files = Files.walk(sourcePath, 1, FileVisitOption.FOLLOW_LINKS);
		files.forEach((Path p) -> {
			if (p.toString().equals(sourceFolder)) return;
			try {
				moveFile(p, nof, targetFolder, folderPrefix);
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
		filesCounter=0;
		foldersCounter=1;
	}
	private static void moveFile(Path p, int nof, String targetFolder, String folderPrefix) throws IOException {
		filesCounter++;
		if (filesCounter>nof) {
			foldersCounter++;
			filesCounter=1;
		}
		if (filesCounter==1) {
			targetPath = createPath(targetFolder, folderPrefix);
		}
		Files.copy(p, Paths.get(targetPath.toString() + "/" + p.getFileName()));
		filesCopied++;
		//System.out.println("Folder:" + foldersCounter + ":File:" + filesCounter + ":" + p.getFileName());
	}
	private static Path createPath(String targetFolder, String folderPrefix) throws IOException {
		String folderNumber = String.format("%04d", foldersCounter);
		Path targetPath = Paths.get(targetFolder + "/" + folderPrefix + folderNumber);
		Files.createDirectories(targetPath);
		return targetPath;
	}
	public static int getFilesCopied() {
		return filesCopied;
	}
}
