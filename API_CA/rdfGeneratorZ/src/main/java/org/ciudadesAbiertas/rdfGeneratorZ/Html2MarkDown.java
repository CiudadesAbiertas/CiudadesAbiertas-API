package org.ciudadesAbiertas.rdfGeneratorZ;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * @author analistaweb
 */
public class Html2MarkDown {
	private static final Logger logger = LoggerFactory.getLogger(Html2MarkDown.class);
	private ArrayList<String> prefijoLista = new ArrayList<String>();
	
	private boolean enEncabezado = false;

	public String transformar(String xml) {
		xml = "<!DOCTYPE html [<!ENTITY aacute \"á\"><!ENTITY eacute \"é\"><!ENTITY iacute \"í\"><!ENTITY oacute \"ó\"><!ENTITY uacute \"ú\"><!ENTITY ntilde \"ñ\">]>"
				+ "<html>" + xml + "</html>";
		try {
			DocumentBuilder db = DocumentBuilderFactory.newInstance()
					.newDocumentBuilder();
			Document doc = db.parse(new ByteArrayInputStream(xml
					.getBytes("UTF-8")));
			StringBuilder retorno = new StringBuilder();
			if (doc.hasChildNodes()) {
				printNote(doc.getChildNodes(), retorno);
			}
			return retorno.toString();
		} catch (Exception e) {
			logger.error(e.getMessage());
			return "";
		}
	}

	private void printNote(NodeList nodeList, StringBuilder retorno) {

		for (int count = 0; count < nodeList.getLength(); count++) {
			Node tempNode = nodeList.item(count);
			if (tempNode.getNodeType() == Node.ELEMENT_NODE) {
				addInicio(tempNode, retorno);
				if (tempNode.hasChildNodes()) {
					printNote(tempNode.getChildNodes(), retorno);
				}
				retorno.append(addFin(tempNode));
			} else if (tempNode.getNodeType() == Node.TEXT_NODE) {
				retorno.append(tempNode.getTextContent());
			}
		}

	}

	private void addInicio(Node tempNode, StringBuilder retorno) {
		if ("h1".equals(tempNode.getNodeName())) {
			retorno.append("#");
		} else if ("h2".equals(tempNode.getNodeName())) {
			retorno.append("##");
		} else if ("h3".equals(tempNode.getNodeName())) {
			retorno.append("###");
		} else if ("h4".equals(tempNode.getNodeName())) {
			retorno.append("####");
		} else if ("h5".equals(tempNode.getNodeName())) {
			retorno.append("#####");
		} else if ("h6".equals(tempNode.getNodeName())) {
			retorno.append("######");
		} else if ("blockquote".equals(tempNode.getNodeName())) {
			retorno.append(">");
		} else if ("strong".equals(tempNode.getNodeName())) {
			retorno.append("**");
		} else if ("a".equals(tempNode.getNodeName())) {
			retorno.append("[");	
		} else if ("em".equals(tempNode.getNodeName())) {
			retorno.append("*");
		} else if ("dt".equals(tempNode.getNodeName())) {
			retorno.append("* *");
		} else if ("code".equals(tempNode.getNodeName())) {
			retorno.append("`");
		} else if ("li".equals(tempNode.getNodeName())) {
			for (int i = 0; i < prefijoLista.size(); i++) {
				if (i == prefijoLista.size() - 1) {
					retorno.append(prefijoLista.get(i));
				} else {
					retorno.append("\t");
				}
			}
		} else if ("ul".equals(tempNode.getNodeName())) {
			retorno.append(System.getProperty("line.separator"));
			this.prefijoLista.add("* ");
		} else if ("dl".equals(tempNode.getNodeName())) {
			retorno.append(System.getProperty("line.separator"));
		} else if ("ol".equals(tempNode.getNodeName())) {
			retorno.append(System.getProperty("line.separator"));
			this.prefijoLista.add("1. ");
		} else if ("tr".equals(tempNode.getNodeName())) {
			retorno.append(System.getProperty("line.separator") + "| ");
		} else if ("th".equals(tempNode.getNodeName())) {
			this.enEncabezado = true;
		}
	}

	private String addFin(Node tempNode) {
		if ("h1".equals(tempNode.getNodeName())
				|| "h2".equals(tempNode.getNodeName())
				|| "h3".equals(tempNode.getNodeName())
				|| "h4".equals(tempNode.getNodeName())
				|| "h5".equals(tempNode.getNodeName())
				|| "h6".equals(tempNode.getNodeName())
				|| "div".equals(tempNode.getNodeName())
				|| "br".equals(tempNode.getNodeName())
				|| "p".equals(tempNode.getNodeName())
				|| "dd".equals(tempNode.getNodeName())
				|| "blockquote".equals(tempNode.getNodeName())
				|| "li".equals(tempNode.getNodeName())
				) {
			return System.getProperty("line.separator");
		} else if ("strong".equals(tempNode.getNodeName())) {
			return "**";
		} else if ("dt".equals(tempNode.getNodeName())) {
			return "*. ";
		} else if ("a".equals(tempNode.getNodeName())) {
			return "](" 
					+ tempNode.getAttributes().getNamedItem("href").getTextContent() 
					+ (tempNode.getAttributes().getNamedItem("title") == null
							? "" 
							: (" \"" + tempNode.getAttributes().getNamedItem("title") + "\"")
							)
							
					+ ")";
		} else if ("img".equals(tempNode.getNodeName())) {
			return "![" 
					+ tempNode.getAttributes().getNamedItem("alt").getTextContent()
					+ "](" + tempNode.getAttributes().getNamedItem("src").getTextContent() + ")";
		} else if ("em".equals(tempNode.getNodeName())) {
			return "*";
		} else if ("code".equals(tempNode.getNodeName())) {
			return "`";
		} else if ("ul".equals(tempNode.getNodeName())) {
			this.prefijoLista.remove(this.prefijoLista.size() - 1);
			return System.getProperty("line.separator");
		} else if ("ol".equals(tempNode.getNodeName())) {
			this.prefijoLista.remove(this.prefijoLista.size() - 1);
			return System.getProperty("line.separator");
		} else if ("caption".equals(tempNode.getNodeName())) {
			return System.getProperty("line.separator") + "==";
		} else if ("tr".equals(tempNode.getNodeName())) {
			if (this.enEncabezado) {
				this.enEncabezado = false;
				return System.getProperty("line.separator") + "|--|";
			} else {
				return "";	
			}
			
		} else if ("td".equals(tempNode.getNodeName()) || "th".equals(tempNode.getNodeName())) {
			return " | ";
		} else {
			return "";
		}
	}

}
