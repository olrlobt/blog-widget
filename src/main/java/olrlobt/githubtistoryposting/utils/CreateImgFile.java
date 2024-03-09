package olrlobt.githubtistoryposting.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class CreateImgFile {

	public static File fromUrl(String imgUrl) throws IOException {
		File tempFile = File.createTempFile("temp", ".png");

		try (InputStream in = new URL(imgUrl).openStream();
			 FileOutputStream out = new FileOutputStream(tempFile)) {
			byte[] buffer = new byte[1024];
			int bytesRead;
			while ((bytesRead = in.read(buffer)) != -1) {
				out.write(buffer, 0, bytesRead);
			}
		}
		return tempFile;
	}
}