package com.example.fanarver3;


public class Observable {

    private OnChangeListener Booleanlistener;

    private boolean State;


    public void setOnChangeListener(OnChangeListener listener)
    {
        this.Booleanlistener = listener;

    }

    public boolean get()
    {
        return State;
    }

    public void set(boolean State)
    {
        this.State = State;

        if(Booleanlistener != null)
        {
            Booleanlistener.onBooleanChanged(State);
        }
    }
}
