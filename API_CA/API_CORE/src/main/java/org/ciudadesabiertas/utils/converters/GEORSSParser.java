package org.ciudadesabiertas.utils.converters;

import java.util.Date;

import org.ciudadesabiertas.utils.Constants;

import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.feed.synd.SyndFeedImpl;

public class GEORSSParser  {
	
	//Static
	public static final String FEEDTYPE = "atom_1.0";
	public static final String AUTHOR_FEED_NAME = "Ciudades Abiertas";
	public static final String LINK_FEED = "https://ciudades-abiertas.es/";
	public static final String ODATA_FEED = "http://schemas.microsoft.com/ado/2007/08/dataservices";
	public static final String ODATA_METADATA_FEED = "http://schemas.microsoft.com/ado/2007/08/dataservices/metadata";
	public static final String XMLNS_CIUDADESABIERTAS = "ciudadesAbiertas";
	public static final String ENCODING_UTF8 = Constants.ENCODING_UTF8;
	public static final String DESCRIPTION = "Descripción del elemento " ;
	public static final String TITLE_GENERICO = "Contenido genenerado en formato GeoRSS desde API Ciudades Abiertas";
	
	private SyndFeed feed;
	
	
	public GEORSSParser () {
		feed = new SyndFeedImpl();
        feed.setFeedType(FEEDTYPE);
        feed.setEncoding(ENCODING_UTF8);
        feed.setLink(LINK_FEED);
        feed.setAuthor(AUTHOR_FEED_NAME);
        
      }
	

	public GEORSSParser(String contentMD5, Date responseDate, String self) {
		
		feed = new SyndFeedImpl();
		feed.setEncoding(ENCODING_UTF8);
        feed.setFeedType(FEEDTYPE);
        feed.setTitle(TITLE_GENERICO);
        feed.setAuthor(AUTHOR_FEED_NAME);
        
        feed.setLink(self);
        feed.setUri(contentMD5);
	}

	public SyndFeed getFeed() {
		return feed;
	}

	public void setFeed(SyndFeed feed) {
		this.feed = feed;
	}

		

}
