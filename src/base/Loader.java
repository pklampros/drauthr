package base;

import gui.*;

import java.awt.EventQueue;

import net.Uploader;

public class Loader {

	public DataHandler dh;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Loader ld = new Loader();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Loader() {
		DataHandler dh = DataHandler.getInstance();
		boolean firstRun = dh.readSettings();
		System.out.println(firstRun);
		if (firstRun) {
			Settings.newSettings(true);
		} else
			if(dh.checkTypes()) TypeSelect.promptSelection(dh.types);
		//
		System.out.println(Uploader.getInstance().fillData().startAndLogin().getNode(33));
		
	}
}
