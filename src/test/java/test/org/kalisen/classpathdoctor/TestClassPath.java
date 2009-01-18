package test.org.kalisen.classpathdoctor;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.kalisen.classpathdoctor.ClassPath;
import org.kalisen.classpathdoctor.DirectoryPath;
import org.kalisen.classpathdoctor.PathElement;
import org.kalisen.classpathdoctor.PathEntry;
import org.kalisen.classpathdoctor.PathSeparator;
import org.kalisen.test.TestEqualHelper;
import org.testng.Assert;
import org.testng.annotations.Factory;
import org.testng.annotations.Test;

@Test
public class TestClassPath {

	private final static PathSeparator SEPARATOR = new PathSeparator(":");
	private final static PathEntry ENTRY = new DirectoryPath(".");
	private final static PathEntry ENTRY2 = new DirectoryPath("..");
	
	public void settingEntriesShouldAlsoSetElementsAndAddSeparatorBetweenEachEntryElement() {
		ClassPath cp = new ClassPath();
		ArrayList<PathEntry> newEntries = new ArrayList<PathEntry>();
		newEntries.add(ENTRY);
		newEntries.add(ENTRY2);
		newEntries.add(ENTRY);
		cp.setEntries(newEntries);
		List<PathEntry> entries = cp.getEntries();
		List<PathElement> elements = cp.getElements();
		Assert.assertNotNull(entries);
		Assert.assertNotNull(elements);
		Assert.assertEquals(entries.size(), 3);
		Assert.assertEquals(elements.size(), 5);
		Iterator<PathEntry> iterOnEntries = entries.iterator();
		Assert.assertEquals(iterOnEntries.next(), ENTRY);
		Assert.assertEquals(iterOnEntries.next(), ENTRY2);
		Assert.assertEquals(iterOnEntries.next(), ENTRY);
		Iterator<PathElement> iterOnElements = elements.iterator();
		Assert.assertEquals(iterOnElements.next(), ENTRY);
		Assert.assertEquals(iterOnElements.next(), SEPARATOR);
		Assert.assertEquals(iterOnElements.next(), ENTRY2);
		Assert.assertEquals(iterOnElements.next(), SEPARATOR);
		Assert.assertEquals(iterOnElements.next(), ENTRY);
	}
	
	public void settingElementsShouldAlsoSetEntries() {
		ClassPath cp = new ClassPath();
		ArrayList<PathElement> newElements = new ArrayList<PathElement>();
		newElements.add(ENTRY);
		newElements.add(SEPARATOR);
		newElements.add(ENTRY2);
		newElements.add(SEPARATOR);
		newElements.add(SEPARATOR);
		cp.setElements(newElements);
		List<PathEntry> entries = cp.getEntries();
		List<PathElement> elements = cp.getElements();
		Assert.assertNotNull(entries);
		Assert.assertNotNull(elements);
		Assert.assertEquals(entries.size(), 2);
		Assert.assertEquals(elements.size(), 5);
		Iterator<PathEntry> iterOnEntries = entries.iterator();
		Assert.assertEquals(iterOnEntries.next(), ENTRY);
		Assert.assertEquals(iterOnEntries.next(), ENTRY2);
		Iterator<PathElement> iterOnElements = elements.iterator();
		Assert.assertEquals(iterOnElements.next(), ENTRY);
		Assert.assertEquals(iterOnElements.next(), SEPARATOR);
		Assert.assertEquals(iterOnElements.next(), ENTRY2);
		Assert.assertEquals(iterOnElements.next(), SEPARATOR);
		Assert.assertEquals(iterOnElements.next(), SEPARATOR);
	}
	
	@Test(expectedExceptions=IllegalArgumentException.class)
	public void settingElementsShouldThrowIllegalArgumentExceptionIfSequenceOfElementsIsNotValid() {
		ClassPath cp = new ClassPath();
		ArrayList<PathElement> invalidElementsSeq = new ArrayList<PathElement>();
		invalidElementsSeq.add(ENTRY);
		invalidElementsSeq.add(ENTRY2);
		cp.setElements(invalidElementsSeq);
	}
	
	public void addingAnElementWhichIsAnEntryShouldAddAnEntryAsWell() {
		ClassPath cp = new ClassPath();
		cp.addElement(ENTRY);
		List<PathEntry> entries = cp.getEntries();
		List<PathElement> elements = cp.getElements();
		Assert.assertNotNull(entries);
		Assert.assertNotNull(elements);
		Assert.assertEquals(entries.size(), 1);
		Assert.assertEquals(elements.size(), 1);
		Assert.assertEquals(entries.iterator().next(), ENTRY);
		Assert.assertEquals(elements.iterator().next(), ENTRY);
	}
	
	public void addingAnEntryShouldAddAnElementAsWell() {
		ClassPath cp = new ClassPath();
		cp.addEntry(ENTRY);
		List<PathEntry> entries = cp.getEntries();
		List<PathElement> elements = cp.getElements();
		Assert.assertNotNull(entries);
		Assert.assertNotNull(elements);
		Assert.assertEquals(entries.size(), 1);
		Assert.assertEquals(elements.size(), 1);
		Assert.assertEquals(entries.iterator().next(), ENTRY);
		Assert.assertEquals(elements.iterator().next(), ENTRY);
	}

	public void addingAnEntryShouldAddASeparatorBetweenItAndTheExistingOne() {
		ClassPath cp = new ClassPath();
		cp.addEntry(ENTRY);
		cp.addEntry(ENTRY);
		List<PathEntry> entries = cp.getEntries();
		List<PathElement> elements = cp.getElements();
		Assert.assertNotNull(entries);
		Assert.assertNotNull(elements);
		Assert.assertEquals(entries.size(), 2);
		Assert.assertEquals(elements.size(), 3);
		Iterator<PathEntry> iterOnEntries = entries.iterator();
		Assert.assertEquals(iterOnEntries.next(), ENTRY);
		Assert.assertEquals(iterOnEntries.next(), ENTRY);
		Iterator<PathElement> iterOnElements = elements.iterator();
		Assert.assertEquals(iterOnElements.next(), ENTRY);
		Assert.assertEquals(iterOnElements.next(), SEPARATOR);
		Assert.assertEquals(iterOnElements.next(), ENTRY);
	}
	
	public void testAddAnElement() {
		ClassPath cp = new ClassPath();
		cp.addElement(SEPARATOR);
		List<PathEntry> entries = cp.getEntries();
		List<PathElement> elements = cp.getElements();
		Assert.assertNotNull(entries);
		Assert.assertNotNull(elements);
		Assert.assertTrue(entries.isEmpty());
		Assert.assertEquals(elements.size(), 1);
		Assert.assertEquals(elements.iterator().next(), SEPARATOR);
	}

	public void testRemoveAnEntry() {
		ClassPath cp = new ClassPath();
		cp.addEntry(ENTRY);
		cp.removeEntry(ENTRY);
		List<PathEntry> entries = cp.getEntries();
		List<PathElement> elements = cp.getElements();
		Assert.assertNotNull(entries);
		Assert.assertNotNull(elements);
		Assert.assertTrue(entries.isEmpty());
		Assert.assertTrue(elements.isEmpty());
	}
	
	public void removingAnElementWhichIsAnEntryShouldRemoveAnEntryAsWell() {
		ClassPath cp = new ClassPath();
		cp.addElement(ENTRY);
		cp.removeElement(ENTRY);
		List<PathEntry> entries = cp.getEntries();
		List<PathElement> elements = cp.getElements();
		Assert.assertNotNull(entries);
		Assert.assertNotNull(elements);
		Assert.assertTrue(entries.isEmpty());
		Assert.assertTrue(elements.isEmpty());
	}
	
	public void removingAnEntryShouldRemoveTheNextSeparatorIfItExists() {
		ClassPath cp = new ClassPath();
		cp.addElement(ENTRY);
		cp.addElement(ENTRY);
		cp.removeElement(ENTRY);
		List<PathEntry> entries = cp.getEntries();
		List<PathElement> elements = cp.getElements();
		Assert.assertNotNull(entries);
		Assert.assertNotNull(elements);
		Assert.assertEquals(entries.size(), 1);
		Assert.assertEquals(elements.size(), 1);
		Assert.assertEquals(entries.iterator().next(), ENTRY);
		Assert.assertEquals(elements.iterator().next(), ENTRY);
	}
	
	@Test(expectedExceptions = IllegalArgumentException.class)
	public void addingANullEntryWillThrowAnIllegalArgumentException() {
		new ClassPath().addEntry(null);
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void addingANullElementWillThrowAnIllegalArgumentException() {
		new ClassPath().addElement(null);
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void removingANullElementWillThrowAnIllegalArgumentException() {
		new ClassPath().removeEntry(null);
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void settingNullEntriesWillThrowAnIllegalArgumentException() {
		new ClassPath().setEntries(null);
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void settingNullElementsWillThrowAnIllegalArgumentException() {
		new ClassPath().setElements(null);
	}

	public void equalsShouldReturnTrueWhenClassPathAreTheSame() {
		ClassPath path1 = new ClassPath();
		path1.addEntry(new DirectoryPath(new File("./")));
		ClassPath path2 = new ClassPath();
		path2.addEntry(new DirectoryPath(new File("./")));
		Assert.assertTrue(path1.equals(path2));
	}

	public void equalsShouldReturnFalseWhenClassPathHaveDifferentNumberOfEntries() {
		ClassPath path1 = new ClassPath();
		path1.addEntry(new DirectoryPath(new File("./")));
		Assert.assertFalse(path1.equals(new ClassPath()));
	}

	public void equalsShouldReturnFalseWhenClassPathEntriesAreDifferent() {
		ClassPath path1 = new ClassPath();
		path1.addEntry(new DirectoryPath(new File("./")));
		ClassPath path2 = new ClassPath();
		path2.addEntry(new DirectoryPath(new File("./dummy.jar")));
		Assert.assertFalse(path1.equals(path2));
	}

	@Factory
	public Object[] buildEqualHashCodeTest() {
		return new Object[] { new TestEqualHelper(buildTestClassPath(),
				buildTestClassPath(), buildTestClassPath()) };
	}

	public ClassPath buildTestClassPath() {
		ClassPath result = new ClassPath();
		result.addElement(new PathSeparator(":"));
		result.addElement(new PathSeparator(":"));
		result.addEntry(new DirectoryPath("./"));
		result.addEntry(new DirectoryPath("./"));
		return result;
	}
}
