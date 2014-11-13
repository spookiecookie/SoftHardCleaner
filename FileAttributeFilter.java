import java.nio.file.attribute.BasicFileAttributes;
public interface FileAttributeFilter
{
	boolean accept(BasicFileAttributes attributes);
}
