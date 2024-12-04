/**
 * Copyright (c) OpenRS
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package net.openrs.cache;

import lombok.val;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * An {@link Archive} is a file within the cache that can have multiple member
 * files inside it.
 * 
 * @author Graham
 * @author `Discardedx2
 */
public class Archive {

	/**
	 * Decodes the specified {@link ByteBuffer} into an {@link Archive}.
	 * 
	 * @param buffer
	 *            The buffer.
	 * @param size
	 *            The size of the archive.
	 * @return The decoded {@link Archive}.
	 */
	public static Archive decode(ByteBuffer buffer, int size) {
		if (buffer == null || !buffer.hasRemaining()) {
			return null;
		}
		/* allocate a new archive object */
		Archive archive = new Archive(size);

		/* read the number of chunks at the end of the archive */
		buffer.position(buffer.limit() - 1);
		int chunks = buffer.get() & 0xFF;
		try {
			buffer.position(buffer.limit() - 1 - chunks * size * 4);
		} catch (IllegalArgumentException iae) {
			return null;
		}

		/* read the sizes of the child entries and individual chunks */
		int[][] chunkSizes = new int[chunks][size];
		int[] filesSize = new int[size];
		try {
			buffer.position(buffer.limit() - 1 - chunks * size * 4);
		} catch (IllegalArgumentException iae) {
			return null;
		}
		for (int chunk = 0; chunk < chunks; chunk++) {
			int chunkSize = 0;
			for (int id = 0; id < size; id++) {
				/* read the delta-encoded chunk length */
					int delta = buffer.getInt();
					chunkSize += delta;

					/* store the size of this chunk */
					chunkSizes[chunk][id] = chunkSize;

					/* and add it to the size of the whole file */
					filesSize[id] += chunkSize;

			}
		}

		/* allocate the buffers for the child entries */
		for (int id = 0; id < size; id++) {
			try {
				archive.entries[id] = ByteBuffer.allocate(filesSize[id]);
			} catch (IllegalArgumentException iae) {
				return null;
			}
		}

		/* read the data into the buffers */
		buffer.position(0);
		int chunk;
		for (chunk = 0; chunk < chunks; chunk++) {
			int id;
			for (id = 0; id < size; id++) {
				try {
					/* get the length of this chunk */
					val chunkSize = chunkSizes[chunk][id];

					/* copy this chunk into a temporary buffer */
					val temp = new byte[chunkSize];
					if (buffer.remaining() < temp.length) {
						return null;
					}

					buffer.get(temp);

					/* copy the temporary buffer into the file buffer */
					archive.entries[id].put(temp);
				} catch (NegativeArraySizeException exception){
				}
			}
		}

		/* flip all of the buffers */
		for (int id = 0; id < size; id++) {
			archive.entries[id].flip();
		}

		/* return the archive */
		return archive;
	}

	/**
	 * The array of entries in this archive.
	 */
	private final ByteBuffer[] entries;

	/**
	 * Creates a new archive.
	 * 
	 * @param size
	 *            The number of entries in the archive.
	 */
	public Archive(int size) {
		this.entries = new ByteBuffer[size];
	}

	/**
	 * Encodes this {@link Archive} into a {@link ByteBuffer}.
	 * <p />
	 * Please note that this is a fairly simple implementation that does not
	 * attempt to use more than one chunk.
	 * 
	 * @return An encoded {@link ByteBuffer}.
	 * @throws IOException
	 *             if an I/O error occurs.
	 */
	public ByteBuffer encode() throws IOException { // TODO: an implementation
													// that can use more than
													// one chunk
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		DataOutputStream os = new DataOutputStream(bout);
		try {
			/* add the data for each entry */
			for (int id = 0; id < entries.length; id++) {
				/* copy to temp buffer */
				byte[] temp = new byte[entries[id].limit()];
				entries[id].position(0);
				try {
					entries[id].get(temp);
				} finally {
					entries[id].position(0);
				}

				/* copy to output stream */
				os.write(temp);
			}

			/* write the chunk lengths */
			int prev = 0;
			for (int id = 0; id < entries.length; id++) {
				/*
				 * since each file is stored in the only chunk, just write the
				 * delta-encoded file size
				 */
				int chunkSize = entries[id].limit();
				os.writeInt(chunkSize - prev);
				prev = chunkSize;
			}

			/*
			 * we only used one chunk due to a limitation of the implementation
			 */
			bout.write(1);

			/* wrap the bytes from the stream in a buffer */
			byte[] bytes = bout.toByteArray();
			return ByteBuffer.wrap(bytes);
		} finally {
			os.close();
		}
	}

	/**
	 * Gets the entry with the specified id.
	 * 
	 * @param id
	 *            The id.
	 * @return The entry.
	 */
	public ByteBuffer getEntry(int id) {
		return entries[id];
	}

	/**
	 * Inserts/replaces the entry with the specified id.
	 * 
	 * @param id
	 *            The id.
	 * @param buffer
	 *            The entry.
	 */
	public void putEntry(int id, ByteBuffer buffer) {
		entries[id] = buffer;
	}

	/**
	 * Gets the size of this archive.
	 * 
	 * @return The size of this archive.
	 */
	public int size() {
		return entries.length;
	}

}
