import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javafx.scene.image.Image;

/**
 * Reads the worldmap.json file and has public methods to create Image and
 * 
 * @author Sammy
 *
 */
public class MapReader {
	/**
	 * @parm keys needed to access values - the JSON was auto-generated to use
	 *       String instead of Int keys
	 */
	private ArrayList<String> keys;

	public MapReader() {
		keys = new ArrayList<String>();

	}

	/**
	 * Fills out keys.
	 */
	public void Startup() {
		keys.add("0");
		keys.add("1");
		keys.add("2");
		keys.add("3");
		keys.add("4");
		keys.add("5");
		keys.add("6");
		keys.add("7");
		keys.add("8");
		keys.add("9");
	}

	/**
	 * 
	 * @param mapname the name of the HashMap we want to construct
	 * @return A HashMap of viewangles and forwardlocation strings extracted from
	 *         json
	 */
	public HashMap<Integer, String> ExtractMap(String mapname) {

		JSONObject spindle = ReadMap(mapname);
		// Gets the size of this HashMap to iterate over and to fetch values.
		int keysize = spindle.keySet().size();
		Integer Index = 0;
		HashMap<Integer, String> finalmap = new HashMap<>();

		while (Index < keysize) {
			finalmap.put(Index, (String) spindle.get(keys.get(Index)));
			// Adds each entry to the HashMap
			Index = Index + 1;
		}
		System.out.println(mapname + " initialized");
		return finalmap;
	}

	/**
	 * 
	 * @param mapname the name of the HashMap we want to construct
	 * @return A HashMap of viewangles and Images extracted from json
	 */
	public HashMap<Integer, Image> ExtractImageMap(String mapname) {

		JSONObject spindle = ReadMap(mapname);

		int keysize = spindle.keySet().size();
		Integer Index = 0;
		HashMap<Integer, Image> finalmap = new HashMap<>();

		while (Index < keysize) {
			String imname = (String) spindle.get(keys.get(Index));
			if (imname != null) {
				finalmap.put(Index, new Image(imname));
			} else {
				finalmap.put(Index, null);
			}
			Index = Index + 1;
		}
		System.out.println(mapname + " initialized");
		return finalmap;

	}

	/**
	 * 
	 * @param mapname the name of the HashMap we want to construct
	 * @return A JSONObject corresponding to the correct HashMap for parsing
	 */
	private JSONObject ReadMap(String mapname) {
		JSONParser parser = new JSONParser();

		// finds the worldmap file wherever it is
		ClassLoader loader1 = getClass().getClassLoader();

		// input stream
		InputStream i = loader1.getResourceAsStream("worldmap.json");
		// convert input stream to string (citation given in method)
		String StringStream = convert(i);

		Object maps = null;
		try {
			maps = parser.parse(StringStream);
			// Extracts the worldmap JSON file
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Code snippet below adapted from
		// https://www.tutorialspoint.com/json/json_java_example.htm
		JSONObject jsonObject = (JSONObject) maps;
		JSONObject spindle = (JSONObject) (jsonObject.get(mapname));
		return spindle;
	}

	/**
	 * Method convert adapted from
	 * http://roufid.com/5-ways-convert-inputstream-string-java/#javautilscanner
	 * 
	 * @return a string version of the input stream
	 */

	private String convert(InputStream inputStream) {
		Charset charset = StandardCharsets.UTF_8;
		try (Scanner scanner = new Scanner(inputStream, charset.name())) {
			return scanner.useDelimiter("\\A").next();
		}
	}

}
