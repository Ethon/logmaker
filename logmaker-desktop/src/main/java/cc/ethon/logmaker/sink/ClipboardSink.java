package cc.ethon.logmaker.sink;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

public class ClipboardSink implements Sink {

	private final ByteArrayOutputStream outputStream;

	public ClipboardSink() {
		outputStream = new ByteArrayOutputStream();
	}

	@Override
	public OutputStream getOutputStream() {
		return outputStream;
	}

	@Override
	public void applyContent() {
		final Clipboard c = Toolkit.getDefaultToolkit().getSystemClipboard();
		final StringSelection data = new StringSelection(outputStream.toString());
		c.setContents(data, data);
	}

	@Override
	public void close() {
	}

}
