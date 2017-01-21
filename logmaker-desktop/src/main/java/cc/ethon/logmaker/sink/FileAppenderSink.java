package cc.ethon.logmaker.sink;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class FileAppenderSink implements Sink {

	private final FileOutputStream outputStream;

	public FileAppenderSink(File file, boolean append) throws FileNotFoundException {
		outputStream = new FileOutputStream(file, append);
	}

	@Override
	public OutputStream getOutputStream() {
		return outputStream;
	}

	@Override
	public void applyContent() throws IOException {
		outputStream.flush();
	}

	@Override
	public void close() throws Exception {
		outputStream.close();
	}

}
