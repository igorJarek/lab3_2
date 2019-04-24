package edu.iis.mto.staticmock;

import edu.iis.mto.staticmock.reader.FileNewsReader;
import edu.iis.mto.staticmock.reader.NewsReader;
import edu.iis.mto.staticmock.reader.WebServiceNewsReader;

public class NewsReaderFactory {

    public static NewsReader getReader(String readerType) {

        NewsReader reader = null;
        switch (readerType) {
            case "WS":
                reader = new WebServiceNewsReader();
                break;
            case "File":
                reader = new FileNewsReader();
                break;

            default:
                throw new IllegalArgumentException("unknown reater type: " + readerType);
        }
        return reader;
    }

}
