package memory;

import memory.SecondaryMemory.Pointer;

public class FileInMemory {
    private String name;
	private int size;
	private Pointer start;
	private int length;
	private static byte[] contentFile = new byte[0];

	public FileInMemory(String name, byte[] content) {
		this.name = name;
		contentFile = content;
		size = contentFile.length;
	}

	public static byte[] part(int index) {
		byte[] part = new byte[Block.getSize()];

		// Provjerite ispravnost indeksa
		if (index < 0 || index >= contentFile.length / Block.getSize()) {
			return part;
		}

		int startIndex = index * Block.getSize();
		int endIndex = startIndex + Block.getSize();

		for (int i = startIndex; i < endIndex; i++) {
			if (i < contentFile.length) {
				part[i - startIndex] = contentFile[i];
			} else {
				// Ako smo došli do kraja datoteke, popunite ostatke s praznim znakovima
				part[i - startIndex] = (byte) ' ';
			}
		}

		return part;
	}

	public Pointer getStart() {
		return start;
	}

	public void setStart(Pointer start) {
		this.start = start;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public String getName() {
		return name;
	}

	public byte[] getContentFile() {
		return contentFile;
	}

	public void setContentFile(byte[] contentFile) {
		FileInMemory.contentFile = contentFile;
	}
}
