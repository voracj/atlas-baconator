package baconator.ext_data;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import baconator.model.BaconatorData;
import baconator.utils.BaconatorException;

public class ParallelExternalDataSourceTest {
	@Test
	public void testOkClient() {
		ExternalDataClient client = Mockito.mock(ExternalDataClient.class);
		Mockito.when(client.retrieve()).thenReturn("[\"test\"]");
		ParallelExternalDataSource ds = new ParallelExternalDataSource(client);
		BaconatorData data = ds.retrieveData(2);
		Assert.assertEquals(2, data.getItems().size());
	}

	@Test(expected = BaconatorException.class)
	public void testFailingClient() {
		ExternalDataClient client = Mockito.mock(ExternalDataClient.class);
		Mockito.when(client.retrieve()).thenThrow(new RuntimeException("client failed1")).thenThrow(new RuntimeException("client failed2")).thenReturn("[\"test\"]");
		ParallelExternalDataSource ds = new ParallelExternalDataSource(client);
		BaconatorData data = ds.retrieveData(1);
		Assert.assertEquals(1, data.getItems().size());
	}
}
