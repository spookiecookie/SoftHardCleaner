import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.FileSystems;
import java.util.List;
import java.util.LinkedList;
import java.nio.file.SimpleFileVisitor;
import java.util.Date;
import java.nio.file.FileVisitResult;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.io.IOException;
import java.nio.file.AccessDeniedException;

/**
* The SimpleHardCleaner program implements an application that
* simply walks a given directory and deletes files that are accessed later than given date. It cleans (kinda) hard drive.
*
* @author  Marius Žilėnas
* @version 1.0
* @since   2014-11-07 
*/
public class SoftHardCleaner
{
	public static class ListFiles
		extends SimpleFileVisitor<Path>
	{
		private final Date latestAccessDate;

		List<Path> files = new LinkedList<>();

		public void setFiles(List<Path> files)
		{
			this.files = files;
		}

		public List<Path> getFiles()
		{
			return files;
		}

		public Date getLatestAccessDate()
		{
			return latestAccessDate;
		}

		public ListFiles(Date lad)
		{
			super();
			this.latestAccessDate = lad;
		}

		@Override
		public FileVisitResult visitFile(Path file, BasicFileAttributes attr)
		{
			if (new Date(attr.lastAccessTime().toMillis())
					.before(getLatestAccessDate()))
			{
				System.out.println("File found " +file);
				getFiles().add(file);
			}
			return FileVisitResult.CONTINUE;
		}
	}

	public static void usage()
	{
		System.out.println("This tools is for detection of files in a directory that have not been accessed after a given date.");
		System.out.println("Expected input:");
		System.out.println("<y-M-d H:m:s> <path>");
		System.out.println("For example \"2010-10-21 12:00:00\" directory");
	}

	public void fileFind(Date date, Path path)
		throws IOException
	{
		ListFiles lf = new ListFiles(date);
		Files.walkFileTree(path, lf);
	}

	/**
	 * Lists all files that where not accessed since some date.
	 */
	public static void main(String[] args)
	{
		if (2 > args.length)
		{
			usage();
			System.exit(-1);
		}
		String date  = args[0];
		Path   path  = FileSystems.getDefault().getPath(args[1]);
		SoftHardCleaner shc = new SoftHardCleaner();
		try
		{
			shc.fileFind(
					new SimpleDateFormat("y-M-d H:m:s").parse(date),
					path);
		}
		catch (ParseException e)
		{
			System.out.println("Could not parse the date");
			System.exit(-1);
		}
		catch (AccessDeniedException e)
		{
			// Ignore access denied exceptions
		}
		catch (IOException e)
		{
			System.out.println("I/O problem occured"+e);
			System.exit(-1);
		}
	}
}
