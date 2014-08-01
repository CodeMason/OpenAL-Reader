package warlord.ingame;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Properties;

import warlord.opengl.Game;

public class ObjectLoading {
	
	public static void unloadObject(File target, Object object) throws IOException{
		if(!target.exists()){
			target.createNewFile();
		}
		FileOutputStream fout = new FileOutputStream(target);
		ObjectOutputStream oos = new ObjectOutputStream(fout);
		oos.writeObject(object);
		oos.close();
	}
	public static void unloadProperties(File target, Properties properties) throws IOException{
		if(!target.exists()){
			target.createNewFile();
		}
		FileOutputStream fout = new FileOutputStream(target);
		ObjectOutputStream oos = new ObjectOutputStream(fout);
		oos.writeObject(properties);
		oos.close();
	}
	public static void unloadObject(String filename, Object object) throws IOException{
		File target = Game.getFile(filename);
		if(!target.exists()){
			target.createNewFile();
		}
		FileOutputStream fout = new FileOutputStream(target);
		ObjectOutputStream oos = new ObjectOutputStream(fout);
		oos.writeObject(object);
		oos.close();
	}
	
	public static Object loadObject(String filename) throws IOException, ClassNotFoundException{
		File target = Game.getFile(filename);
		if(!target.exists()){
			target.createNewFile();
		}
		FileInputStream fin = new FileInputStream(target);
		ObjectInputStream ois = new ObjectInputStream(fin);
		Object object = ois.readObject();
		ois.close();
		return object;
	}
	public static Object loadObject(File target) throws IOException, ClassNotFoundException{
		if(!target.exists()){
			target.createNewFile();
		}
		FileInputStream fin = new FileInputStream(target);
		ObjectInputStream ois = new ObjectInputStream(fin);
		Object object = ois.readObject();
		ois.close();
		return object;
	}

}
