package cc.ethon.logmaker.sink;

import java.io.OutputStream;

public interface Sink extends AutoCloseable {

	public OutputStream getOutputStream();

	public void applyContent() throws Exception;

}
