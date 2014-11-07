import java.util.Date;
import java.nio.file.attribute.BasicFileAttributes;

public class AccessDateFilter
	implements FileAttributeFilter
{
	private final Date accessDate;

	public Date getAccessDate()
	{
		return accessDate;
	}

	public AccessDateFilter(Date accessDate)
	{
		this.accessDate = accessDate;
	}

	public boolean accept(BasicFileAttributes attr)
	{
		return new Date(attr.lastAccessTime().toMillis())
											 .before(getAccessDate());
	}
}
