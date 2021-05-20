package com.demo.assignment.repository.model;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.demo.assignment.BR;

public class LoginModel extends BaseObservable {
    private String firstName;
    private String lastName;
    private boolean isSubmitEnable;

    @Bindable
    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
        notifyPropertyChanged(BR.firstName);
    }

    @Bindable
    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
        notifyPropertyChanged(BR.lastName);
    }

    @Bindable
    public boolean isSubmitEnable() {
        return isSubmitEnable;
    }

    public void setSubmitEnable(boolean submitEnable) {
        isSubmitEnable = submitEnable;
        notifyPropertyChanged(BR.submitEnable);
    }
}
