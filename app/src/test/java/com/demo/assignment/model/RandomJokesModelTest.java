package com.demo.assignment.model;

import com.demo.assignment.repository.model.RandomJokesModel;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

public class RandomJokesModelTest {


    private final String type = "Testing Joke Type";
    private final String jokes = "This is Dummy Jokes data";
    private final int id = 1001;
    private final List<String> categoryList = new ArrayList<>();


    @Mock
    RandomJokesModel jokesModel;

    @Mock
    RandomJokesModel.Value value;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        Mockito.when(jokesModel.getType()).thenReturn(type);
        Mockito.when(value.getCategories()).thenReturn(categoryList);
        Mockito.when(value.getId()).thenReturn(id);
        Mockito.when(value.getJoke()).thenReturn(jokes);
    }

    @Test
    public void testJokesType() {
        Mockito.when(jokesModel.getType()).thenReturn(type);
        Assert.assertEquals("Testing Joke Type", jokesModel.getType());
    }

    @Test
    public void testRandomJokes() {
        Mockito.when(value.getJoke()).thenReturn(jokes);
        Assert.assertEquals("This is Dummy Jokes data", value.getJoke());
    }

    @Test
    public void testJokesId() {
        Mockito.when(value.getId()).thenReturn(id);
        Assert.assertEquals(1001, value.getId());
    }

    @Test
    public void testJokesCategory() {
        Mockito.when(value.getCategories()).thenReturn(categoryList);
        Assert.assertEquals(new ArrayList<>(), value.getCategories());

    }


    @Test
    public void testJokesError() {
        Mockito.when(value.getJoke()).thenReturn(jokes);
        Assert.assertNotEquals("Mismatch data", value.getJoke());
    }


    @After
    public void tearDown() throws Exception {
        jokesModel = null;
    }
}