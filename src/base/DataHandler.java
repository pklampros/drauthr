package base;

import java.io.*;

public class DataHandler {
	public String[] data = new String[4];
	//private boolean fileIsNew = false;

	private File checkSettings() {
		File f = new File("settings.csv");
		if (!f.exists()) {
			try {
				f.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//fileIsNew = true;
		} else {
			
			//fileIsNew = false;
		}
		return f;
		// System.out.println(f.getPath());
	}

	public void writeSettings() {
		File f = checkSettings();
		if (f != null && data.length > 0)
			writeFile(f, data);
	}

	public void readSettings() {
		File f = checkSettings();
		if (f != null)
			data = readFile(f);
	}

	private void writeFile(File f, String[] s) {
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

	private String[] readFile(File f) {
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
