package edu.hw6.task5;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;

class HackerNewsTest {

    @Test
    public void hackerNewsTopStories() throws IOException, InterruptedException, URISyntaxException {
        long[] ids = HackerNews.hackerNewsTopStories();
        Assertions.assertNotNull(ids);
        Assertions.assertTrue(ids.length>0);
    }

    @Test
    public void newTitleById() throws IOException, InterruptedException, URISyntaxException {
        long[] ids = HackerNews.hackerNewsTopStories();
        Assertions.assertNotNull(ids);
        Assertions.assertTrue(ids.length>0);
        Assertions.assertNotNull(HackerNews.newsTitleById(ids[0]));
    }
}
