package com.services;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.domain.JRoot;

public class JRootService {
	
	public List<JRoot> getSystemRoots() {

		List<JRoot> roots = new ArrayList<>();
		File[] paths;
		try {
			paths = File.listRoots();
			for (File path : paths) {
				JRoot root = new JRoot(path.toString());
				roots.add(root);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return roots;
	}
	
	

}
