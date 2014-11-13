public class FileDeleter
	implements ActionTaker
{
	public void action(Path file)
	{
		System.out.println("Deleting file " + file);
	} 
}
