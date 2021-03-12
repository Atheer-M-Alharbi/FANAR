package com.example.fanarver3;


public class Observableboolean {

    private OnBooleanChangeListener listener;

    private boolean State;

    public void setOnIntegerChangeListener(OnBooleanChangeListener listener)
    {
        this.listener = listener;
    }

    public boolean get()
    {
        return State;
    }

    public void set(boolean State)
    {
        this.State = State;

        if(listener != null)
        {
            listener.onBooleanChanged(State);
        }
    }
}
