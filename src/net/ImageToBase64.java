package net;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.commons.codec.binary.Base64;


// original code here: http://www.myjeeva.com/2012/07/how-to-convert-image-to-string-and-string-to-image-in-java/
public class ImageToBase64 {

		public static String toBase64(String imgPath) {
			File file = new File(imgPath);
			String imageDataString = "";
			try {
				/*
				 * Reading a Image file from file system
				 */
				FileInputStream imageInFile = new FileInputStream(file);
				byte imageData[] = new byte[(int) file.length()];
				imageInFile.read(imageData);

				/*
				 * Converting Image byte array into Base64 String
				 */
				imageDataString = encodeImage(imageData);

				System.out.println("Image Successfully Manipulated!");
			} catch (FileNotFoundException e) {
				System.out.println("Image not found" + e);
			} catch (IOException ioe) {
				System.out.println("Exception while reading the Image " + ioe);
			}
			return imageDataString;
		}

		/**
		 * Encodes the byte array into base64 string
		 * 
		 * @param imageByteArray
		 *            - byte array
		 * @return String a {@link java.lang.String}
		 */
		public static String encodeImage(byte[] imageByteArray) {
//			return Base64.encodeBase64URLSafeString(imageByteArray);
			return Base64.encodeBase64String(imageByteArray);
		}

		/**
		 * Decodes the base64 string into byte array
		 * 
		 * @param imageDataString
		 *            - a {@link java.lang.String}
		 * @return byte array
		 */
		public static byte[] decodeImage(String imageDataString) {
			return Base64.decodeBase64(imageDataString);
		}



}
