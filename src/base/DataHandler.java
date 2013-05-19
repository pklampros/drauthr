package base;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.Uploader;

public class DataHandler implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8596839416140677796L;
	public String[] data = new String[4];
	public HashMap<String, String> types = new HashMap<String, String>();
	public HashMap<String, String[]> typeData = new HashMap<String, String[]>();

	File dh = new File("dh.ser");
	private static final DataHandler INSTANCE = new DataHandler();

	public static DataHandler getInstance() {
		return INSTANCE;
	}


	public void fillTypeData(String type) {
		typeData.put(type, Uploader.getInstance().fillData().startAndLogin()
				.getFieldsOfType(type));
	}

	public void fillTypes() {
		types = Uploader.getInstance().fillData().startAndLogin()
				.getNodeTypes();
	}

	public boolean checkTypeData(String type) {
		if (!typeData.containsKey(type)) {
			fillTypeData(type);
			serialize();
		}
		return true;
	}
	public boolean checkTypes() {
		if (types.size() < 1) {
			fillTypes();

			serialize();
		} else {
		}
		return true;
	}

	public boolean readSettings() {
		boolean exists = dh.exists();
		if (exists && dh != null) {
			deserialize();
			return false;
		} else {
			return true;
		}
	}
	public void serialize() {
		try {
			FileOutputStream fileOut = new FileOutputStream("dh.ser");
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(this);
			out.close();
			fileOut.close();
		} catch (IOException i) {
			i.printStackTrace();
		}
	}

	public void deserialize() {
		DataHandler d;
		try {
			FileInputStream fileIn = new FileInputStream("dh.ser");
			ObjectInputStream in = new ObjectInputStream(fileIn);
			d = (DataHandler) in.readObject();
			in.close();
			fileIn.close();
		} catch (IOException i) {
			i.printStackTrace();
			return;
		} catch (ClassNotFoundException c) {
			System.out.println("DataHandler class not found");
			c.printStackTrace();
			return;
		}
		this.data = d.data;
		this.types = d.types;
		this.typeData = d.typeData;
	}

}
