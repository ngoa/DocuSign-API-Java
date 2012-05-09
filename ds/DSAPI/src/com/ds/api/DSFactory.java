package com.ds.api;

import java.io.IOException;

public class DSFactory {

	public DSImpl createDSImpl() throws IOException {
		return new DSImpl();
	}

}
