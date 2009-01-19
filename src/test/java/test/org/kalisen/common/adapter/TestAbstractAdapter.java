package test.org.kalisen.common.adapter;

import java.util.Observable;
import java.util.Observer;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.kalisen.common.adapter.AbstractAdapter;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Test
public class TestAbstractAdapter {

	private Mockery mockery = null;

	@BeforeMethod
	public void setUpMockery() {
		this.mockery = new Mockery();
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void addListenerShouldThrowAnIllegalArgumentExceptionWhenPassedANullParameter() {
		MyTestAdapter adapter = new MyTestAdapter();
		adapter.addListener(null);
	}

	public void testAddListener() {
		final MyTestAdapter adapter = new MyTestAdapter();
		final Observer listener = this.mockery.mock(Observer.class);
		this.mockery.checking(new Expectations() {
			{
				oneOf(listener).update(with(any(Observable.class)),
						with(aNull(Object.class)));
			}
		});
		adapter.addListener(listener);
		adapter.notifyListeners();
		this.mockery.assertIsSatisfied();
	}

	public void testRemoveListener() {
		final MyTestAdapter adapter = new MyTestAdapter();
		final Observer listener = this.mockery.mock(Observer.class);
		adapter.addListener(listener);
		adapter.removeListener(listener);
		adapter.notifyListeners();
		this.mockery.assertIsSatisfied();
	}

	private static class MyTestAdapter extends AbstractAdapter {
		// for test purpose only
		public void notifyListeners() {
			getNotifier().setChanged();
			getNotifier().notifyObservers();
		}
	}

}
