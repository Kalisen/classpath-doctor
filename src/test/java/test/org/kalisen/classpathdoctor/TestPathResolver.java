package test.org.kalisen.classpathdoctor;

import org.kalisen.classpathdoctor.EmptyPathEntry;
import org.kalisen.classpathdoctor.PathEntry;
import org.kalisen.classpathdoctor.PathResolver;
import org.testng.Assert;
import org.testng.annotations.Test;

@Test
public class TestPathResolver {

	public void emptyStringShouldResolveToEmptyPathEntry() {
		PathEntry entry = new PathResolver().resolve("");
		Assert.assertNotNull(entry);
		Assert.assertTrue(entry instanceof EmptyPathEntry, "Expected "
				+ EmptyPathEntry.class.getName() + " but was "
				+ entry.getClass().getName());
	}

}
