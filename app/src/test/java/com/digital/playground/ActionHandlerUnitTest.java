package com.digital.playground;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.verifyPrivate;

import android.os.Bundle;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.Observer;

import com.digital.playground.core.ViewState;
import com.digital.playground.ui.viewmodel.RandomJokesViewState;
import com.digital.playground.unittest.ActionHandler;
import com.digital.playground.unittest.Callback;
import com.digital.playground.unittest.CollaboratorForPartialMocking;
import com.digital.playground.unittest.CollaboratorWithStaticMethods;
import com.digital.playground.unittest.Command;
import com.digital.playground.unittest.Data;
import com.digital.playground.unittest.Response;
import com.digital.playground.unittest.Service;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.util.List;
//https://www.baeldung.com/intro-to-powermock
//https://github.com/himnay/powermock-examples
//https://github.com/eugenp/tutorials/tree/master/testing-modules/mockito/src/test/java/com/baeldung/mockito

@RunWith(PowerMockRunner.class)
@PrepareForTest({CollaboratorWithStaticMethods.class, Command.class})
public class ActionHandlerUnitTest {
    @Rule
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();
    @Mock
    Observer<ViewState> observer;
    @Mock
    Callback<Response> callback;
    @Mock
    private Service service;
    @Mock
    private Command command;
    @Captor
    private ArgumentCaptor<Response> responseArgumentCaptor;

    @Captor
    private ArgumentCaptor<Callback<Response>> callbackCaptor;
    @Captor
    private ArgumentCaptor<RandomJokesViewState> emailCaptor;

    private ActionHandler handler;


    @Before
    public void setUp() {
        handler = new ActionHandler(service);
        handler.mJokeState.observeForever(observer);
    }


    @Test
    public void givenServiceWithValidResponse_whenCallbackReceived_thenProcessed() {
        ActionHandler handler = new ActionHandler(service);
        handler.doAction();

        verify(service).doAction(anyString(), callbackCaptor.capture());
        Callback<Response> callback = callbackCaptor.getValue();
        Response response = new Response();
        callback.reply(response);

        String expectedMessage = "Successful data response";
        Data data = response.getData();
        assertEquals("Should receive a successful message: ", expectedMessage, data.getMessage());
    }

    @Test
    public void givenServiceWithInvalidResponse_whenCallbackReceived_thenNotProcessed() {

        doAnswer((Answer<Void>) invocation -> {
            Callback<Response> callback = invocation.getArgument(1);
            Response response = new Response();
            response.setIsValid(false);
            callback.reply(response);

            Data data = response.getData();
            assertNull("No data in invalid response: ", data);
            return null;
        }).when(service).doAction(anyString(), any(Callback.class));

        ActionHandler handler = new ActionHandler(service);
        handler.doAction();
    }

    @Test
    public void mockStaticMethodAndVerifyStaticMethod() {
        // init
        PowerMockito.mockStatic(CollaboratorWithStaticMethods.class);
        when(CollaboratorWithStaticMethods.firstMethod(Mockito.anyString())).thenReturn("Hello Baeldung!");
        when(CollaboratorWithStaticMethods.secondMethod()).thenReturn("Nothing special");

        String firstWelcome = CollaboratorWithStaticMethods.firstMethod("Whoever");
        String secondWelcome = CollaboratorWithStaticMethods.firstMethod("Whatever");
        assertEquals("Hello Baeldung!", firstWelcome);
        assertEquals("Hello Baeldung!", secondWelcome);

        //To check how many time static method executed
        PowerMockito.verifyStatic(CollaboratorWithStaticMethods.class, times(2));
        CollaboratorWithStaticMethods.firstMethod(Mockito.anyString());

        //To check static method never executed
        PowerMockito.verifyStatic(CollaboratorWithStaticMethods.class, Mockito.never());
        CollaboratorWithStaticMethods.secondMethod();

    }

    @Test(expected = RuntimeException.class)
    public void givenPartialMocking_whenUsingPowerMockito_thenCorrect() throws Exception {
        String returnValue;

        spy(CollaboratorForPartialMocking.class);
        when(CollaboratorForPartialMocking.staticMethod()).thenReturn("I am a static mock method.");
        returnValue = CollaboratorForPartialMocking.staticMethod();
        CollaboratorForPartialMocking.staticMethod();
        assertEquals("I am a static mock method.", returnValue);

        CollaboratorForPartialMocking collaborator = new CollaboratorForPartialMocking();
        CollaboratorForPartialMocking mock = spy(collaborator);

        when(mock.finalMethod()).thenReturn("I am a final mock method.");
        returnValue = mock.finalMethod();
        verify(mock).finalMethod();
        assertEquals("I am a final mock method.", returnValue);

        PowerMockito.when(mock, "privateMethod").thenReturn("I am a private mock method.");

        returnValue = mock.privateMethodCaller();
        verifyPrivate(mock).invoke("privateMethod");
        assertEquals("I am a private mock method. Welcome to the Java world.", returnValue);

        //Only Private method
        CollaboratorForPartialMocking classUnderTest = PowerMockito.spy(new CollaboratorForPartialMocking());
        PowerMockito.when(classUnderTest, "privateMethod").thenReturn("I am a private mock method.");
        assertEquals("I am a private mock method. Welcome to the Java world.",
                classUnderTest.privateMethodCaller());
    }

    @Test
    public void testDoServiceInternal() throws Exception {
        PowerMockito.mockStatic(Command.class);

        ArgumentCaptor<Callback<Response>> captor = ArgumentCaptor.forClass(Callback.class);
        PowerMockito.doNothing().when(Command.class, "execute", captor.capture());
        Command.IADISABILITY.execute(new Bundle(), new Callback<Response>() {
            @Override
            public void reply(Response response) {
                assertEquals("Hello Baeldung", response.getValue());
            }
        });

        Callback<Response> resourceName = captor.getValue();
        Response response = new Response();
        response.setValue("Hello Baeldung");
        resourceName.reply(response);
    }


    /**
     * voidStaticMethodWithCallback where "execute" is the static method with void return type
     *
     * @throws Exception
     */
    @Test
    public void voidStaticMethodWithCallback() throws Exception {
        PowerMockito.mockStatic(Command.class);

        //ArgumentCaptor<Callback<Response>> captor = ArgumentCaptor.forClass(Callback.class);//Way to create local captor
        PowerMockito.doNothing().when(Command.class, "execute", callbackCaptor.capture());
        handler.doService(null);

        Callback<Response> resourceName = callbackCaptor.getValue();
        Response response = new Response();
        response.setValue("Hello Baeldung");
        resourceName.reply(response);

        verify(observer, times(2)).onChanged(emailCaptor.capture());
        List<RandomJokesViewState> randomJokesViewStateList = emailCaptor.getAllValues();
        assertEquals(ViewState.LOADING, randomJokesViewStateList.get(0).getCurrentState());
        assertEquals(ViewState.SUCCESS, randomJokesViewStateList.get(1).getCurrentState());
    }


    /**
     * Accessing non static method from static instance variable.eg. Command Pattern
     *
     * @throws Exception
     */
    @Test()
    public void testDoService() throws Exception {
        PowerMockito.mockStatic(Command.class);
        Whitebox.setInternalState(Command.class, "IADISABILITY", command);

        //ArgumentCaptor<Callback<Response>> captor = ArgumentCaptor.forClass(Callback.class);//Way to create local captor
        PowerMockito.doNothing().when(Command.IADISABILITY).execute(any(), callbackCaptor.capture());
        handler.doService(null);

        Callback<Response> resourceName = callbackCaptor.getValue();
        Response response = new Response();
        response.setValue("Hello Baeldung");
        resourceName.reply(response);

        verify(observer, times(2)).onChanged(emailCaptor.capture());
        List<RandomJokesViewState> randomJokesViewStateList = emailCaptor.getAllValues();
        assertEquals(ViewState.LOADING, randomJokesViewStateList.get(0).getCurrentState());
        assertEquals(ViewState.SUCCESS, randomJokesViewStateList.get(1).getCurrentState());
    }


}