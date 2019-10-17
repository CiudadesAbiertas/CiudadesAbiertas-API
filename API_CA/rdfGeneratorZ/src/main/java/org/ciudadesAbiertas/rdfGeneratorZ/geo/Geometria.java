package org.ciudadesAbiertas.rdfGeneratorZ.geo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.geoslab.coordinatetransformation.Transformer;

public class Geometria {
	private static final Logger logger = LoggerFactory.getLogger(Geometria.class);
	
	public static final String POINT = "Point";
	public static final String LINESTRING = "LineString";
	public static final String POLIGON = "Polygon";
	public static final String GEOMETRYCOLLECTION = "GeometryCollection";
	
	
	private static final String UTM30N_ED50 = "23030";
	private static final String UTM30N_ETRS89 = "25830";
	private static final String WGS84 = "4326";	
	
	private String type;
	private Object coordinates;
	
	public String asJson(String srsname) {
		return "";
	}
	
	public String asXML(String srsname) {
		return "";
	}
	
	public String asGEORSS(String srsname) {
		return "";
	}
	
	public String asCSV(String srsname) {
		return "";
	}
	
	public String asHtml(String srsname) {
		return "";
	}
	
	public String asJsonLD(String srsname) {
		return "";
	}
	
	public static String pasarEtrs89aAWgs84(Double x, Double y) {
		try {
	        double[] wsgGP = Geometria.pasarEtrs89aAWgs84Double(x, y);
	        double lax = wsgGP[1]; // x
	        double lay = wsgGP[0]; // y
	        return "" + lay + "," + lax;
		} catch (Exception e) {
			return "";
		}
	}
	public static String pasarAWgs84(Double x, Double y) {
		try {
	        double[] wsgGP = Geometria.pasarAWgs84Double(x, y);
	        double lax = wsgGP[1]; // x
	        double lay = wsgGP[0]; // y
	        return "" + lay + "," + lax;
		} catch (Exception e) {
			return "";
		}
	}
	
	public static String pasarAETRS89(Double x, Double y) {
		try {
	        double[] wsgGP = Geometria.pasarAETRS89Double(x, y);
	        double lax = wsgGP[0]; // x
	        double lay = wsgGP[1]; // y
	        return "" + lax + "," + lay;
		} catch (Exception e) {
			return "";
		}
	}
	
	public static double[] pasarAWgs84Double(Double x, Double y) {
		try {
			String source = UTM30N_ED50;
	        String target = WGS84;
	        return Transformer.transform(x,y, source,target);
		} catch (Exception e) {
			return null;
		}
	}
	public static double[] pasarEtrs89aAWgs84Double(Double x, Double y) {
		try {
			String source = UTM30N_ETRS89;
	        String target = WGS84;
	        return Transformer.transform(x,y, source,target);
		} catch (Exception e) {
			return null;
		}
	}
	public static double[] pasarAETRS89Double(Double x, Double y) {
		try {
			String source = UTM30N_ED50;
	        String target = UTM30N_ETRS89;
	        return Transformer.transform(x,y, source,target);
		} catch (Exception e) {
			return null;
		}
	}
	public static Punto pasarAWgs84ConPunto(Double x, Double y) {
		try {
			String source = UTM30N_ED50;
	        String target = WGS84;
	        double[] coords = Transformer.transform(x,y, source,target);
	        return new Punto(Punto.POINT, new Double[]{coords[1],coords[0]});
		} catch (Exception e) {
			return new Punto();
		}
	}
	
	
	public static Double[] pasarAUTM30(Double lon, Double lat) {
		try {
			String source = WGS84;
			String target = UTM30N_ED50;
			double[] coords = Transformer.transform(lon,lat, source,target);
			
			Double[] retorno = new Double[]{new Double(coords[0]), new Double(coords[1])};
	        return retorno;
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new IllegalArgumentException(e.getMessage());
		}
	}
	
	public static Double[] pasarETRS89AUTM30(Double lon, Double lat) {
		try {
			String source = UTM30N_ETRS89;
			String target = UTM30N_ED50;
			double[] coords = Transformer.transform(lon,lat, source,target);
			
			Double[] retorno = new Double[]{new Double(coords[0]), new Double(coords[1])};
	        return retorno;
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new IllegalArgumentException(e.getMessage());
		}
	}
	
	public static String invertirCoordenadas(String p) {
		String[] coors = p.split(",");
		return "" + coors[1] + "," + coors[0];
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Object getCoordinates() {
		return coordinates;
	}

	public void setCoordinates(Object coordinates) {
		this.coordinates = coordinates;
	}

	
	
	
}
