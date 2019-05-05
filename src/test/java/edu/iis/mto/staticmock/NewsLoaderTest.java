package edu.iis.mto.staticmock;

import edu.iis.mto.staticmock.reader.WebServiceNewsReader;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ConfigurationLoader.class, NewsReaderFactory.class})
public class NewsLoaderTest {

    private ConfigurationLoader configLoader;
    private NewsReaderFactory readerFactory;
    private WebServiceNewsReader serviceNewsReader;
    private Configuration config;

    @Before
    public void SetUp() {
        mockStatic(ConfigurationLoader.class);
        mockStatic(NewsReaderFactory.class);

        configLoader = mock(ConfigurationLoader.class);
        when(configLoader.getInstance()).thenReturn(configLoader);
        config = new Configuration("WS");
        when(configLoader.loadConfiguration()).thenReturn(config);

        readerFactory = mock(NewsReaderFactory.class);
        serviceNewsReader = mock(WebServiceNewsReader.class);
        when(readerFactory.getReader(config.getReaderType())).thenReturn(serviceNewsReader);
    }

    @Test
    public void stateTest() {
        IncomingNews incomingNews = new IncomingNews();
        incomingNews.add(new IncomingInfo("Premium A", SubsciptionType.A));
        incomingNews.add(new IncomingInfo("Premium B", SubsciptionType.B));
        incomingNews.add(new IncomingInfo("Premium C", SubsciptionType.C));
        incomingNews.add(new IncomingInfo("Normal", SubsciptionType.NONE));
        when(serviceNewsReader.read()).thenReturn(incomingNews);

        NewsLoader newsLoader = new NewsLoader();
        PublishableNews publishableNews = newsLoader.loadNews();

        assertThat(publishableNews.getPublicContentSize(), is(1));
        assertThat(publishableNews.getSubscribentContentSize(), is(3));
    }

    @Test
    public void behaviorTest() {
        IncomingNews incomingNews = new IncomingNews();
        when(serviceNewsReader.read()).thenReturn(incomingNews);

        NewsLoader newsLoader = new NewsLoader();
        PublishableNews publishableNews = newsLoader.loadNews();

        verify(configLoader, times(1)).loadConfiguration();
    }
}
