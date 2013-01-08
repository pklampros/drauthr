package base;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

import net.Uploader;

public class DataHandler {
	public String[] data = new String[4];
	public HashMap<String, String> types = new HashMap<String, String>();
	File [] filez = new File[2];
	
	// NEW PLAN
	
	/* 
	 * First load settings then check all files
	 */
	
	
	File st = new File("settings.csv");
	File dt = new File("dataTypes.csv");
	// private boolean fileIsNew = false;
	// Singleton with public final field
	private static final DataHandler INSTANCE = new DataHandler();

	public static DataHandler getInstance() {
		return INSTANCE;
	}
	public boolean setParameters() {
		
			fillTypes();
		
		return true;
	}
	// private String [] hashMapToStrings(HashMap<String, String> mapp) {
	// String[] result = new String[types.size()*2];
	// int i = 0;
	// for(Map.Entry<String, String> entry: types.entrySet()) {
	// result[i] = entry.getKey();
	// result[i+1] = entry.getValue();
	// i+=2;
	// }
	// return result
	// }

	// private HashMap<String, String> stringsToHashMap(String[] strings) {
	//
	// }
	private boolean checkSettings() {
		boolean stExists = st.exists();
		boolean dtExists = dt.exists();
		if (stExists && !dtExists)
			fillTypes();
		return !stExists || !dtExists;
	}

	public boolean checkFile(File f) {
		if (!f.exists()) {
			try {
				f.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return false;
		} else {

			return true;
		}
	}

	public void fillTypes() {
//		boolean exists = checkFile(dt);

//		if (exists) {
		if(!checkFile(dt)) {
			types = Uploader.getInstance().fillData().startAndLogin()
					.getNodeTypes();
			
//			String[] toStore = new String[types.size() * 2];
			
			writeFileFromMap(dt, types);
		} else {
			types = readFileToMap(dt);
		}
			//		}
//		return exists;
	}

	public void writeSettings() {
		boolean exists = checkFile(st);
		if (exists && st != null && data.length > 0)
			writeFileFromStrings(st, data);
	}

	public boolean readSettings() {
		boolean exists = st.exists();
		if (exists && st != null) {
			// file exists run normally
			data = readFileToStrings(st);
			fillTypes();
			return false;
		} else {
			// file does not exist: first run or settings deleted
			return true;
		}
	}

	private void writeFileFromMap(File f,HashMap<String, String> s) {
		try {
			FileWriter fstream = new FileWriter(f);
			BufferedWriter out = new BufferedWriter(fstream);
			for (Map.Entry<String, String> entry : types.entrySet()) {
				out.write(entry.getKey() + "," + entry.getValue());
				out.newLine();
			}
			// Close the output stream
			out.close();
		} catch (Exception e) {// Catch exception if any
			System.err.println("Error: " + e.getMessage());
		}
	}

	private void writeFileFromStrings(File f, String[] s) {
		try {
			FileWriter fstream = new FileWriter(f);
			BufferedWriter out = new BufferedWriter(fstream);
			for (int i = 0; i < s.length; i++) {
				out.write(s[i]);
				if (i < s.length - 1)
					out.newLine();
			}
			// Close the output stream
			out.close();
		} catch (Exception e) {// Catch exception if any
			System.err.println("Error: " + e.getMessage());
		}
	}

	private HashMap<String, String> readFileToMap(File f) {
		HashMap<String, String> fileData = new HashMap<String, String>();
		try {
			FileInputStream fstream = new FileInputStream(f);
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			// Read File Line By Line
			String line;
			while ((line = br.readLine()) != null) {
				if (line.length() > 1) {
					String [] lnz = line.split(",");
					fileData.put(lnz[0], lnz[1]);
				}
			}
			// Close the input stream
			in.close();

		} catch (Exception e) {// Catch exception if any
			System.err.println("Error: " + e.getMessage());
		}
		return fileData;
	}

	private String[] readFileToStrings(File f) {
		String[] fileData = new String[data.length];
		try {
			FileInputStream fstream = new FileInputStream(f);
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			// Read File Line By Line
			int i = 0;
			while ((fileData[i] = br.readLine()) != null
					&& i < fileData.length - 1) {
				i++;
			}
			// Close the input stream
			in.close();

		} catch (Exception e) {// Catch exception if any
			System.err.println("Error: " + e.getMessage());
		}
		return fileData;
	}
}
