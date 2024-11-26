package exceptions;

import LocalException.Type;

public class SomeClassThatUsesException {

	@Override
	public String method(){
		try {
			fos = new FileOutputStream(zipFile);
			fos.write(logsContent);
		} catch (Exception e) {
			throw new Exception(LocalException.Type.EnumElement);
		}
		return "hello world";
	}
	
}
