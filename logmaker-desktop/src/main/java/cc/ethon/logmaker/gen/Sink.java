package cc.ethon.logmaker.gen;

import java.io.OutputStream;

public interface Sink {

	public OutputStream getOutputStream();

	public void applyContent();

}
