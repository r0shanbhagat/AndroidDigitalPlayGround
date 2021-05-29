package com.demo.assignment.ui.activity;

import androidx.annotation.NonNull;
import androidx.lifecycle.AbstractSavedStateViewModelFactory;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;
import androidx.savedstate.SavedStateRegistryOwner;

import com.demo.assignment.core.BaseViewModel;

import dagger.assisted.Assisted;
import dagger.assisted.AssistedFactory;
import dagger.assisted.AssistedInject;


public class ExerciseViewModel extends BaseViewModel {
    private String plantId;


    @AssistedInject
    public ExerciseViewModel(@Assisted String plantId) {
        this.plantId = plantId;
    }

    public String getArgs() {
        return plantId;
    }


    @AssistedFactory
    public interface ExerciseViewModelFactory {
        MyViewModelFactory create(SavedStateRegistryOwner owner, String planId);

    }

    /**
     * https://dagger.dev/dev-guide/assisted-injection.html
     * https://github.com/google/dagger/issues/2287
     * https://cvoronin.medium.com/viewmodel-savedstatehandle-and-dagger-2-31-assisted-injection-52191f7e9da2
     * https://www.gitmemory.com/issue/google/dagger/2287/767892540
     */
    public static class MyViewModelFactory extends AbstractSavedStateViewModelFactory {
        private String plantId;

        @AssistedInject
        public MyViewModelFactory(@Assisted SavedStateRegistryOwner owner, @Assisted String plantId) {
            super(owner, null);
            this.plantId = plantId;
        }

        @NonNull
        @Override
        protected <T extends ViewModel> T create(@NonNull String key, @NonNull Class<T> modelClass, @NonNull SavedStateHandle handle) {
            return (T) new ExerciseViewModel(plantId);
        }
    }


    /**
     * Question :
     * 1. What is the use of Assisted now if we started using the Viewmodel Factory? :
     * Assisted injection is a dependency injection (DI) pattern that is used to construct an object where some parameters may be
     * provided by the DI framework and others must be passed in at creation time (a.k.a “assisted”) by the user.
     *
     * 2. try to use viewmodel factory as Dagger approach without AssistedInject? YEs Possible
     * 3. TRY TO MAKE THE THINGS GENERIC FOR FACTORY IF REQUIRED :need to work for Hilt
     *4. With the Assisted or ViewModel Factory we're loosing the advantage of HiltViewModel
     */

}

