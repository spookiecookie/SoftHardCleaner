import java.nio.file.Path;

public class FilePrinter
	implements ActionTaker
{
	public void action(Path file)
	{
		System.out.println("File found " +file);
	}
}
