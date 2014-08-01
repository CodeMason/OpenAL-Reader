package warlord.sound;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.ByteBuffer;

import org.lwjgl.BufferUtils;

public class SerializableSoundWrapper implements Serializable {

	private static final long serialVersionUID = -2935753709687008752L;
	
	public byte[] rawPCMData;
	public int rate, channels;
	
	public SerializableSoundWrapper(){		
	}
	
	public ByteBuffer getBuffer(){
		return (ByteBuffer) BufferUtils.createByteBuffer(rawPCMData.length).put(rawPCMData).rewind();
	}
	
	public OggData toOggData(){
		OggData oggdata = new OggData();
		oggdata.data = getBuffer();
		oggdata.rate = rate;
		oggdata.channels = channels;
		return oggdata;
	}
	
	/*
	 * Store the raw PCM data
	 */
	
	public static void main(String[] args){
		args = new String[]{"startup2.ogg", "startup2.buffer"};
		if(args.length < 2){
			return;
		}
		SerializableSoundWrapper instance = new SerializableSoundWrapper();
		try {
			OggData oggdata = new OggDecoder().getData(SerializableSoundWrapper.class.getResourceAsStream(args[0]));
			instance.rawPCMData = bufferToArray(oggdata.data);
			instance.rate = oggdata.rate;
			instance.channels = oggdata.channels;
			ObjectLoading.unloadObject(new File(new File(System.getenv("APPDATA"), ".QuestOfTheWarlord"), args[1]), instance);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static byte[] bufferToArray(ByteBuffer buffer){
		byte[] array = new byte[buffer.capacity()];
		for(int index = 0; index < array.length; index++){
			array[index] = buffer.get(index);
		}
		return array;
	}

}
