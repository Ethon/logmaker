package cc.ethon.logmaker.sink;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XmlAppenderSink implements Sink {

	private final ByteArrayOutputStream outputStream;
	private final File file;

	private Document parseDocument(InputStream stream) throws ParserConfigurationException, SAXException, IOException {
		final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		final DocumentBuilder builder = factory.newDocumentBuilder();
		return builder.parse(stream);
	}

	private Document appendDocument(Document doc, Document toAppend) {
		final NodeList nodesToAppend = toAppend.getFirstChild().getChildNodes();
		final Node appendTo = doc.getFirstChild();
		for (int i = 0; i < nodesToAppend.getLength(); ++i) {
			final Node node = nodesToAppend.item(i);
			final Node importedNode = doc.importNode(node, true);
			appendTo.appendChild(importedNode);
		}
		return doc;
	}

	private void writeDocument(Document doc) throws TransformerException {
		final TransformerFactory transformerFactory = TransformerFactory.newInstance();
		final Transformer transformer = transformerFactory.newTransformer();
		final DOMSource source = new DOMSource(doc);
		final StreamResult result = new StreamResult(file);
		// final StreamResult result = new StreamResult(System.out);
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
		transformer.transform(source, result);
	}

	public XmlAppenderSink(File file) {
		this.outputStream = new ByteArrayOutputStream();
		this.file = file;
	}

	@Override
	public void close() throws Exception {
	}

	@Override
	public OutputStream getOutputStream() {
		return outputStream;
	}

	@Override
	public void applyContent() throws Exception {
		final ByteArrayInputStream newStream = new ByteArrayInputStream(outputStream.toByteArray());
		final Document newDocument = parseDocument(newStream);
		try (final FileInputStream oldStream = new FileInputStream(file)) {
			final Document oldDocument = parseDocument(oldStream);
			writeDocument(appendDocument(oldDocument, newDocument));
		}
	}

}
