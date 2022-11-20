package com.todo.finance;

import com.todo.finance.services.cbrclient.QuotesRequestService;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringReader;

@SpringBootApplication
@AllArgsConstructor
public class FinanceApplication implements CommandLineRunner {
	private final QuotesRequestService requestsService;

	public static void main(String[] args) {
		SpringApplication.run(FinanceApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		String xmlData = requestsService.getCurrentQuotes();

		try {
			DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			InputSource is = new InputSource(new StringReader(xmlData));
			Document document = documentBuilder.parse(is);

			Node root = document.getDocumentElement();
			NodeList books = root.getChildNodes();
			for (int i = 0; i < books.getLength(); i++) {
				Node book = books.item(i);
				if (book.getNodeType() != Node.TEXT_NODE) {
					NodeList bookProps = book.getChildNodes();
					//System.out.println(book.getAttributes().getNamedItem("ID").getNodeValue());
					for(int j = 0; j < bookProps.getLength(); j++) {
						Node bookProp = bookProps.item(j);
						if (bookProp.getNodeType() != Node.TEXT_NODE) {
							System.out.println(bookProp.getNodeName() + ":" + bookProp.getChildNodes().item(0).getTextContent());
						}
					}
					System.out.println("");
				}
			}

		} catch (ParserConfigurationException | IOException | SAXException ex) {
			ex.printStackTrace(System.out);
		}
	}
}
