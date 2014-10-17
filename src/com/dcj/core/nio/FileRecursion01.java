package com.dcj.core.nio;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.junit.Test;

public class FileRecursion01 {
	/**
	 * 1.7之前,使用递归的方式来遍历
	 */
	@Test
	public void test01() {
		List resultlist = new ArrayList();
		String type = ".pdf";
		String path = "E:\\电子书";
		findFile(path, type, resultlist);
		System.out.println(resultlist);
	}

	public void findFile(String path, String type, List list) {
		File f = new File(path);
		if (!f.exists() || !f.isDirectory())
			return;
		File[] files = f.listFiles();
		for (File file : files) {
			if (file.isDirectory()) {
				findFile(file.getAbsolutePath(), type, list);
			} else {
				if (file.getAbsolutePath().endsWith(type))
					list.add(file);
			}
		}
	}

	/*
	 * 1.7版本提供了walktree的方式
	 */
	@Test
	public void test02() {
		Path startingDir = Paths.get("E:\\电子书");
		FindFileVisitor findJavaVisitor = new FindFileVisitor(".pdf");
		try {
			Files.walkFileTree(startingDir, findJavaVisitor);
		} catch (IOException e) {
			e.printStackTrace();
		}
		for (String name : findJavaVisitor.getFileNameList()) {
			System.out.println(name);
		}
	}
}

class FindFileVisitor extends SimpleFileVisitor<Path> {
	private List<String> fileNameList = new ArrayList<String>();
	private String fileSuffix = null;
	public List<String> getFileNameList() {
		return fileNameList;
	}

	public FindFileVisitor(String fileSuffix) {
		super();
		this.fileSuffix = fileSuffix;
	}

	@Override
	public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)
			throws IOException {
		/*Objects.requireNonNull(file);
		Objects.requireNonNull(attrs);*/
		if (file.toString().endsWith(fileSuffix)){
			fileNameList.add(file.toString());
		}
		return FileVisitResult.CONTINUE;
	}

}
